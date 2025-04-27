package com.aixbox.common.core.utils.spring;


import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * Spring 工具类
 */
public class SpringUtils extends SpringUtil {

    /**
     * 是否为生产环境
     *
     * @return 是否生产环境
     */
    public static boolean isProd() {
        String activeProfile = getActiveProfile();
        return Objects.equals("prod", activeProfile);
    }

    public static boolean isVirtual() {
        return Threading.VIRTUAL.isActive(getBean(Environment.class));
    }

}
