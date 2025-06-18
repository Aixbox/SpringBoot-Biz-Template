package com.aixbox.system.controller.admin;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cn.dev33.satoken.annotation.SaIgnore;
import com.aixbox.common.captcha.config.properties.CaptchaProperties;
import com.aixbox.common.captcha.enums.CaptchaType;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.system.domain.bo.CaptchaBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * 验证码操作处理
 *
 * @author Lion Li
 */
@SaIgnore
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class CaptchaController {

    private final CaptchaProperties captchaProperties;
    private final ImageCaptchaApplication imageCaptchaApplication;

    //todo 设置 @RateLimiter(key = "#phonenumber", time = 60, count = 1)



    /**
     * 生成行为验证码
     */
    @PostMapping("/auth/captcha")
    public CaptchaResponse<ImageCaptchaVO> getCaptcha() {
        if (!captchaProperties.getEnabled()) {
            throw new ServiceException("验证码未开启");
        }
        String type = captchaProperties.getType();
        if ("RANDOM".equalsIgnoreCase(type)) {
            type = Arrays.asList(CaptchaTypeConstant.SLIDER, CaptchaTypeConstant.CONCAT, CaptchaTypeConstant.ROTATE, CaptchaTypeConstant.WORD_IMAGE_CLICK)
                         .get(ThreadLocalRandom.current().nextInt(4));
        }
        return imageCaptchaApplication.generateCaptcha(type);
    }

    /**
     * 校验行为验证码
     */
    @PostMapping("/auth/verify")
    public ApiResponse<?> verify(@RequestBody CaptchaBo data) {
        ApiResponse<?> response = imageCaptchaApplication.matching(data.getId(), data.getData());
        if (response.isSuccess()) {
            return ApiResponse.ofSuccess(Collections.singletonMap("id", data.getId()));
        }
        return response;
    }

}
