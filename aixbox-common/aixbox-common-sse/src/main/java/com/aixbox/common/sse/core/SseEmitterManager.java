package com.aixbox.common.sse.core;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理 Server-Sent Events (SSE) 连接
 */
@Slf4j
public class SseEmitterManager {

    /**
     * 订阅的频道
     */
    private final static String SSE_TOPIC = "global:sse";

    private final static Map<Long, Map<String, SseEmitter>> USER_TOKEN_EMITTERS = new ConcurrentHashMap<>();





}
