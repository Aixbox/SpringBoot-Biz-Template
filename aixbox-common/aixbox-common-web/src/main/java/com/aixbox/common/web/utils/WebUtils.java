package com.aixbox.common.web.utils;


import com.aixbox.common.core.enums.UserType;
import jakarta.servlet.http.HttpServletRequest;
import com.aixbox.common.web.config.properties.WebProperties;

/**
 * 专属于 web 包的工具类
 */
public class WebUtils {

    private static WebProperties properties;

    public WebUtils(WebProperties webProperties) {
        WebUtils.properties = webProperties;
    }

    /**
     * 获得当前用户的类型
     * 注意：该方法仅限于 web 相关的 framework 组件使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static UserType getLoginUserTypeByPath(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 基于 URL 前缀的约定
        if (request.getServletPath().startsWith(properties.getAdminApi().getPrefix())) {
            return UserType.SYS_USER;
        }
        if (request.getServletPath().startsWith(properties.getAppApi().getPrefix())) {
            return UserType.APP_USER;
        }
        return null;
    }

}
