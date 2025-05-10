package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.utils.ValidatorUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.LoginBody;
import com.aixbox.system.domain.vo.response.LoginVO;
import com.aixbox.system.domain.vo.response.RegisterBody;
import com.aixbox.system.service.SysClientService;
import com.aixbox.system.service.SysRegisterService;
import com.aixbox.system.service.strategy.IAuthStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_GRANT_TYPE_BLOCKED;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_GRANT_TYPE_ERROR;
import static com.aixbox.common.core.pojo.CommonResult.success;

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
