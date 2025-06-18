package com.aixbox.system.domain.vo.response;

import lombok.Data;

/**
 * 验证码信息
 *
 * @author Michelle.Chung
 */
@Data
public class CaptchaResp {

    /**
     * 是否开启验证码
     */
    private Boolean captchaEnabled = true;

    /**
     * 验证码类型
     */
    private String type;

    private String uuid;

    /**
     * 验证码图片
     */
    private String img;

}
