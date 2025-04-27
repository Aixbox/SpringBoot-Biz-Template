package com.aixbox.demo.domain.vo.request;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 *
 */
@Data
public class TestDemoUpdateReqVO {

    /**
     * 主键
     */
    @NotNull
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

}
