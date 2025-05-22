package com.aixbox.system.service.strategy;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.utils.ValidatorUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.response.LoginVO;
import com.aixbox.system.domain.vo.request.user.PasswordLoginBody;
import com.aixbox.system.enums.LoginType;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysLoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_DISABLED;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_NOT_EXIST;

/**
* 密码认证策略
*/
@Slf4j
@Service("password" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class PasswordAuthStrategy implements IAuthStrategy {

    private final SysUserMapper userMapper;
    private final SysLoginService loginService;


    @Override
    public LoginVO login(String body, SysClient client) {
        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
        ValidatorUtils.validate(loginBody);

        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String code = loginBody.getCode();
        String uuid = loginBody.getUuid();

        //todo 集成pro的行为验证码功能



        SysUser user = loadUserByUsername(username);
        loginService.checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = loginService.buildLoginUser(user);
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);


        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        return loginVo;

    }


    private SysUser loadUserByUsername(String username) {
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(sysUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw exception(USERNAME_NOT_EXIST, username);
        } else if (SystemConstants.DISABLE.equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw exception(USERNAME_DISABLED, username);
        }
        return sysUser;
    }


}
