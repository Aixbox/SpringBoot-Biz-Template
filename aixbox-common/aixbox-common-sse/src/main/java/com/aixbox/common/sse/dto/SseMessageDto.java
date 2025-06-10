package com.aixbox.common.sse.dto;


import lombok.Data;

import java.util.List;

/**
 * 消息的dto
 */
@Data
public class SseMessageDto {

    /**
     * 需要推送到的session key 列表
     */
    private List<Long> userIds;

    /**
     * 需要发送的消息
     */
    private String message;

}
