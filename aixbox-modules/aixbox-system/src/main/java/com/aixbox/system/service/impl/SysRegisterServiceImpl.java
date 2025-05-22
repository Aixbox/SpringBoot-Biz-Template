package com.aixbox.system.service.impl;


import cn.dev33.satoken.secure.BCrypt;
import com.aixbox.common.core.constant.Constants;
import com.aixbox.common.core.enums.UserType;
import com.aixbox.common.core.utils.ServletUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.user.RegisterBody;
import com.aixbox.system.event.LogininforEvent;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysRegisterService;
import com.aixbox.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_REGISTER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_EXIST;

/**
 * 注册校验方法
 */
@RequiredArgsConstructor
@Service
public class SysRegisterServiceImpl implements SysRegisterService {

    private final SysUserMapper userMapper;
    private final SysUserService userService;

    @Override
    public void register(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        // 校验用户类型是否存在
        String userType = UserType.getUserType(registerBody.getUserType()).getUserType();

        //todo 设置验证码



        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(BCrypt.hashpw(password));
        sysUser.setUserType(userType);

        boolean exist = userMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, sysUser.getUserName()));
        if (exist) {
            throw exception(USERNAME_EXIST, username);
        }

        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            throw exception(AUTH_REGISTER_ERROR);
        }
        recordLogininfor(username, Constants.REGISTER, "注册成功");
    }


    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }


}
