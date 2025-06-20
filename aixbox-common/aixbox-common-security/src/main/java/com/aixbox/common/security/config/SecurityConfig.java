package com.aixbox.common.security.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.RequestPathInvalidException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.httpauth.basic.SaHttpBasicUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.HttpStatus;
import com.aixbox.common.core.enums.UserType;
import com.aixbox.common.core.exception.SseException;
import com.aixbox.common.core.utils.ServletUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.security.config.properties.SecurityProperties;
import com.aixbox.common.security.handler.AllUrlHandler;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.common.web.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.AccessDeniedException;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 权限安全配置
 *
 * @author Lion Li
 */

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                AllUrlHandler allUrlHandler = SpringUtils.getBean(AllUrlHandler.class);
                // 登录验证 -- 排除多个路径
                SaRouter
                    // 获取所有的
                    .match(allUrlHandler.getUrls())
                    // 对未排除的路径进行检查
                    .check(() -> {
                        HttpServletRequest request = ServletUtils.getRequest();
                        // 检查是否登录 是否有token
                        try {
                            StpUtil.checkLogin();
                        } catch (NotLoginException e) {
                            if (request.getRequestURI().contains("sse")) {
                                throw new SseException(e.getMessage(), e.getCode());
                            } else {
                                throw e;
                            }
                        }

                        // 检查 header 与 param 里的 clientid 与 token 里的是否一致
                        String headerCid = request.getHeader(LoginHelper.CLIENT_KEY);
                        String paramCid = ServletUtils.getParameter(LoginHelper.CLIENT_KEY);
                        String clientId = StpUtil.getExtra(LoginHelper.CLIENT_KEY).toString();
                        if (!StringUtils.equalsAny(clientId, headerCid, paramCid)) {
                            // token 无效
                            throw NotLoginException.newInstance(StpUtil.getLoginType(),
                                "-100", "客户端ID与Token不匹配",
                                StpUtil.getTokenValue());
                        }

                        //检查用户类型，如果是app用户只能访问前缀为app-api的路径，amdin用户只能访问前缀为admin-api的路径
                        UserType userType = WebUtils.getLoginUserTypeByPath(request);
                        // 用户类型不匹配，无权限
                        // 注意：只有 /admin-api/* 和 /app-api/* 有 userType，才需要比对用户类型
                        // 类似 WebSocket 的 /ws/* 连接地址，是不需要比对用户类型的
                        if (userType != null
                                && ObjectUtil.notEqual(LoginHelper.getUserType(), userType)) {
                            throw NotLoginException.newInstance(StpUtil.getLoginType(),
                                    "-100", "错误的用户类型",
                                    StpUtil.getTokenValue());
                        }

                        // 有效率影响 用于临时测试
                        // if (log.isDebugEnabled()) {
                        //     log.info("剩余有效时间: {}", StpUtil.getTokenTimeout());
                        //     log.info("临时有效时间: {}", StpUtil.getTokenActivityTimeout());
                        // }

                    });
            })).addPathPatterns("/**")
            // 排除不需要拦截的路径
            .excludePathPatterns(securityProperties.getExcludes());
    }

    /**
     * 对 actuator 健康检查接口 做账号密码鉴权
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        String username = SpringUtils.getProperty("spring.boot.admin.client.username");
        String password = SpringUtils.getProperty("spring.boot.admin.client.password");
        return new SaServletFilter()
            .addInclude("/actuator", "/actuator/**")
            .setAuth(obj -> {
                SaHttpBasicUtil.check(username + ":" + password);
            })
            .setError(e -> SaResult.error(e.getMessage()).setCode(HttpStatus.UNAUTHORIZED));
    }

}
