package com.aixbox.system.service.strategy;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.utils.ValidatorUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.common.social.config.properties.SocialProperties;
import com.aixbox.common.social.utils.SocialUtils;
import com.aixbox.system.constant.ErrorCodeConstants;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.user.SocialLoginBody;
import com.aixbox.system.domain.vo.response.LoginVO;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysLoginService;
import com.aixbox.system.service.SysSocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 第三方授权策略
 */
@Slf4j
@Service("social" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SocialAuthStrategy implements IAuthStrategy {

    private final SocialProperties socialProperties;
    private final SysSocialService socialService;
    private final SysUserMapper userMapper;
    private final SysLoginService loginService;



    /**
     * 登录-第三方授权登录
     *
     * @param body     登录信息
     * @param client   客户端信息
     */
    @Override
    public LoginVO login(String body, SysClient client) {
        SocialLoginBody loginBody = JsonUtils.parseObject(body, SocialLoginBody.class);
        ValidatorUtils.validate(loginBody);
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(
                loginBody.getSource(), loginBody.getSocialCode(),
                loginBody.getSocialState(), socialProperties);
        if (!response.ok()) {
            throw exception(ErrorCodeConstants.AUTH_LOGIN_ERROR , response.getMsg());
        }
        AuthUser authUserData = response.getData();
        //todo 这里可以对不同平台做一些逻辑，比如设置给项目点个star

        List<SysSocial> list = socialService.selectByAuthId(authUserData.getSource() + authUserData.getUuid());
        if (CollUtil.isEmpty(list)) {
            throw exception(ErrorCodeConstants.AUTH_SOCIAL_ERROR);
        }
        SysSocial social = social = list.get(0);
        SysUser user = loadUser(social.getUserId());
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
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


    private SysUser loadUser(Long userId) {
        SysUser user = userMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", "");
            throw exception(ErrorCodeConstants.USERNAME_NOT_EXIST);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", "");
            throw exception(ErrorCodeConstants.USERNAME_DISABLED);
        }
        return user;
    }


}




















