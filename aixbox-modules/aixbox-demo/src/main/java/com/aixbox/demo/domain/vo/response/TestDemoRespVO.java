package com.aixbox.demo.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * demo 返回参数
 */
@Data
public class TestDemoRespVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * key键
     */
    private String testKey;

    /**
     * 值
     */
    private String value;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
