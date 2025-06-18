package com.aixbox.common.captcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码类型
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum CaptchaType {

    /**
     * 数字
     */
    INPUT("INPUT"),

    /**
     * 字符
     */
    ACT("ACT");

    private final String type;
}
