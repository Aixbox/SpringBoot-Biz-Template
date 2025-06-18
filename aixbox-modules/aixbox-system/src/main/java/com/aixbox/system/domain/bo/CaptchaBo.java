package com.aixbox.system.domain.bo;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class CaptchaBo {

    private String id;

    private ImageCaptchaTrack data;

}
