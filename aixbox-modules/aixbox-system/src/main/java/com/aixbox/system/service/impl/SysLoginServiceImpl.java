package com.aixbox.system.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.Constants;
import com.aixbox.common.core.domain.dto.PostDTO;
import com.aixbox.common.core.domain.dto.RoleDTO;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.utils.ServletUtils;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.redis.utils.RedisUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.constant.CacheConstants;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.response.SysDeptResp;
import com.aixbox.system.enums.LoginType;
import com.aixbox.system.event.LogininforEvent;
import com.aixbox.system.service.SysDeptService;
import com.aixbox.system.service.SysLoginService;
import com.aixbox.system.service.SysPermissionService;
import com.aixbox.system.service.SysPostService;
import com.aixbox.system.service.SysRoleService;
import com.aixbox.system.service.SysSocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_PASSWORD_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_PASSWORD_ERROR_RETRY_LIMIT_EXCEED;

/**
 * 登录校验方法
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginServiceImpl implements SysLoginService {

    //todo 设置配置参数
    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    private final SysPermissionService permissionService;
    private final SysDeptService deptService;
    private final SysRoleService roleService;
    private final SysPostService postService;
    private final SysSocialService socialService;


    /**
     * 登录校验
     */
    @Override
    public void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLogininfor(username, loginFail, StrUtils.format(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw exception(AUTH_PASSWORD_ERROR, loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLogininfor(username, loginFail, StrUtils.format(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw exception(AUTH_PASSWORD_ERROR, loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLogininfor(username, loginFail, StrUtils.format(loginType.getRetryLimitCount(), errorNumber));
                throw exception(AUTH_PASSWORD_ERROR_RETRY_LIMIT_EXCEED, loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);

    }

    /**
     * 构建登录用户
     */
    @Override
    public LoginUser buildLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        Long userId = user.getId();
        loginUser.setUserId(userId);
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setNickname(user.getNickName());
        loginUser.setUserType(user.getUserType());
        loginUser.setMenuPermission(permissionService.getMenuPermission(userId));
        loginUser.setRolePermission(permissionService.getRolePermission(userId));
        if (ObjectUtil.isNotNull(user.getDeptId())) {
            Opt<SysDeptResp> deptOpt = Opt.of(user.getDeptId()).map(deptService::selectDeptById);
            loginUser.setDeptName(deptOpt.map(SysDeptResp::getDeptName).orElse(StringUtils.EMPTY));
            loginUser.setDeptCategory(deptOpt.map(SysDeptResp::getDeptCategory).orElse(StringUtils.EMPTY));
        }
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        List<SysPost> posts = postService.selectPostsByUserId(userId);
        loginUser.setRoles(BeanUtil.copyToList(roles, RoleDTO.class));
        loginUser.setPosts(BeanUtil.copyToList(posts, PostDTO.class));
        return loginUser;
    }

    /**
     * 绑定第三方用户
     *
     * @param authUserData 授权响应实体
     */
    //@Lock4j todo 设置这个注解
    @Override
    public void socialRegister(AuthUser authUserData) {
        String authId = authUserData.getSource() + authUserData.getUuid();
        // 第三方用户信息
        SysSocial bo = BeanUtil.toBean(authUserData, SysSocial.class);
        BeanUtil.copyProperties(authUserData.getToken(), bo);
        Long userId = LoginHelper.getUserId();
        bo.setUserId(userId);
        bo.setAuthId(authId);
        bo.setOpenId(authUserData.getUuid());
        bo.setUserName(authUserData.getUsername());
        bo.setNickName(authUserData.getNickname());
        List<SysSocial> checkList = socialService.selectByAuthId(authId);
        if (CollUtil.isNotEmpty(checkList)) {
            throw new ServiceException("此三方账号已经被绑定!");
        }
        // 查询是否已经绑定用户
        SysSocial params = new SysSocial();
        params.setUserId(userId);
        params.setSource(bo.getSource());
        List<SysSocial> list = socialService.queryList(params);
        if (CollUtil.isEmpty(list)) {
            // 没有绑定用户, 新增用户信息
            socialService.insertByBo(bo);
        } else {
            // 更新用户信息
            bo.setId(list.get(0).getId());
            socialService.updateByBo(bo);
            // 如果要绑定的平台账号已经被绑定过了 是否抛异常自行决断
            // throw new ServiceException("此平台账号已经被绑定!");
        }
    }


    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    public void recordLogininfor(String username, String status, String message) {
        //todo 处理登录事件
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }


}
