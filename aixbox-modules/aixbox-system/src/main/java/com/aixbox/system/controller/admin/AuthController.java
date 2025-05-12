package com.aixbox.system.controller.admin;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.utils.ValidatorUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.common.social.config.properties.SocialLoginConfigProperties;
import com.aixbox.common.social.config.properties.SocialProperties;
import com.aixbox.common.social.utils.SocialUtils;
import com.aixbox.system.constant.ErrorCodeConstants;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.LoginBody;
import com.aixbox.system.domain.vo.request.SocialLoginBody;
import com.aixbox.system.domain.vo.response.LoginVO;
import com.aixbox.system.domain.vo.request.RegisterBody;
import com.aixbox.system.service.SysClientService;
import com.aixbox.system.service.SysLoginService;
import com.aixbox.system.service.SysRegisterService;
import com.aixbox.system.service.strategy.IAuthStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_GRANT_TYPE_BLOCKED;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_GRANT_TYPE_ERROR;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_PLATFORM_ERROR;

/**
 * 登录认证 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    private final SysClientService clientService;
    private final SysRegisterService registerService;
    private final SocialProperties socialProperties;
    private final SysLoginService loginService;


    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody String body) {
        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        ValidatorUtils.validate(loginBody);

        // 授权类型和客户端id
        String clientId = loginBody.getClientId();
        String grantType = loginBody.getGrantType();
        SysClient client = clientService.queryByClientId(clientId);

        // 查询不到 client 或 client 内不包含 grantType
        if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
            log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
            return CommonResult.error(AUTH_GRANT_TYPE_ERROR);
        } else if (!SystemConstants.NORMAL.equals(client.getStatus())) {
            return CommonResult.error(AUTH_GRANT_TYPE_BLOCKED);
        }

        // 登录
        LoginVO loginVo = IAuthStrategy.login(body, client, grantType);

        Long userId = LoginHelper.getUserId();

        //todo 执行推送任务
        //scheduledExecutorService.schedule(() -> {
        //    SseMessageDto dto = new SseMessageDto();
        //    dto.setMessage("欢迎登录RuoYi-Vue-Plus后台管理系统");
        //    dto.setUserIds(List.of(userId));
        //    SseMessageUtils.publishMessage(dto);
        //}, 5, TimeUnit.SECONDS);

        return success(loginVo);

    }


    /**
     * 获取跳转URL
     *
     * @param source 登录来源
     * @return 结果
     */
    @GetMapping("/binding/{source}")
    public CommonResult<String> authBinding(@PathVariable("source") String source,
                                 @RequestParam String tenantId, @RequestParam String domain) {
        SocialLoginConfigProperties obj = socialProperties.getType().get(source);
        if (ObjectUtil.isNull(obj)) {
            return CommonResult.error(AUTH_PLATFORM_ERROR,source + "平台账号暂不支持");
        }
        AuthRequest authRequest = SocialUtils.getAuthRequest(source, socialProperties);
        Map<String, String> map = new HashMap<>();
        map.put("domain", domain);
        map.put("state", AuthStateUtils.createState());
        String authorizeUrl = authRequest.authorize(Base64.encode(JsonUtils.toJsonString(map), StandardCharsets.UTF_8));
        return success(authorizeUrl);
    }


    /**
     * 前端回调绑定授权(需要token)
     *
     * @param loginBody 请求体
     * @return 结果
     */
    @PostMapping("/social/callback")
    public CommonResult<Void> socialCallback(@RequestBody SocialLoginBody loginBody) {
        // 校验token
        StpUtil.checkLogin();
        // 获取第三方登录信息
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(
                loginBody.getSource(), loginBody.getSocialCode(),
                loginBody.getSocialState(), socialProperties);
        AuthUser authUserData = response.getData();
        // 判断授权响应是否成功
        if (!response.ok()) {
            return CommonResult.error(ErrorCodeConstants.AUTH_RESPONSE_ERROR, response.getMsg());
        }
        loginService.socialRegister(authUserData);
        return success();
    }




    /**
     * 用户注册
     */
    @PostMapping("/register")
    public CommonResult<String> register(@Validated @RequestBody RegisterBody user) {
        //添加参数配置
        //if (!configService.selectRegisterEnabled(user.getTenantId())) {
        //    return R.fail("当前系统没有开启注册功能！");
        //}
        registerService.register(user);
        return success("注册成功");
    }

}
