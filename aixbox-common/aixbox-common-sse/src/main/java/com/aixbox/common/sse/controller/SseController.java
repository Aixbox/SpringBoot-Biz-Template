package com.aixbox.common.sse.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.common.sse.core.SseEmitterManager;
import com.aixbox.common.sse.dto.SseMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * SSE 控制器
 */
@RestController
@ConditionalOnProperty(value = "sse.enabled", havingValue = "true")
@RequiredArgsConstructor
public class SseController implements DisposableBean {

    private final SseEmitterManager sseEmitterManager;

    /**
     * 建立 SSE 连接
     */
    @GetMapping(value = "${sse.path}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        String tokenValue = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        return sseEmitterManager.connect(userId, tokenValue);
    }

    /**
     * 关闭 SSE 连接
     */
    @SaIgnore
    @GetMapping(value = "${sse.path}/close")
    public CommonResult<Void> close() {
        String tokenValue = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        sseEmitterManager.disconnect(userId, tokenValue);
        return success();
    }

    /**
     * 向特定用户发送消息
     *
     * @param userId 目标用户的 ID
     * @param msg    要发送的消息内容
     */
    @GetMapping(value = "${sse.path}/send")
    public CommonResult<Void> send(Long userId, String msg) {
        SseMessageDto dto = new SseMessageDto();
        dto.setUserIds(List.of(userId));
        dto.setMessage(msg);
        sseEmitterManager.publishMessage(dto);
        return success();
    }

    /**
     * 向所有用户发送消息
     *
     * @param msg 要发送的消息内容
     */
    @GetMapping(value = "${sse.path}/sendAll")
    public CommonResult<Void> send(String msg) {
        sseEmitterManager.publishAll(msg);
        return success();
    }

    /**
     * 清理资源。此方法目前不执行任何操作，但避免因未实现而导致错误
     */
    @Override
    public void destroy() throws Exception {
        // 销毁时不需要做什么 此方法避免无用操作报错
    }



}
