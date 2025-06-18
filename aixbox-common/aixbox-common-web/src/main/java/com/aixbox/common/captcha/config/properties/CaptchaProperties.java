package com.aixbox.common.captcha.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码 配置属性
 *
 * @author Lion Li
 */
@Data
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    private Boolean enabled;

    /**
     * 验证码类型
     */
    private String type;
}
