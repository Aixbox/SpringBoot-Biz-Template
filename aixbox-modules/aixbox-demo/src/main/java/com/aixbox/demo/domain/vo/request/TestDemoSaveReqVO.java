package com.aixbox.demo.domain.vo.request;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 *
 */
@Data
public class TestDemoSaveReqVO {

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * key键
     */
    @NotNull
    private String testKey;

    /**
     * 值
     */
    private String value;

}
