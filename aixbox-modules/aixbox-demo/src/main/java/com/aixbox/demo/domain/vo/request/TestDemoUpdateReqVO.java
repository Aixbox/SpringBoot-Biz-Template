package com.aixbox.demo.domain.vo.request;


import com.aixbox.demo.domain.entity.TestDemo;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * demo 更新参数
 */
@Data
@AutoMapper(target = TestDemo.class)
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
