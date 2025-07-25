package com.aixbox.demo.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * demo对象
 */
@TableName(value ="test_demo")
@Data
@EqualsAndHashCode(callSuper = true)
public class TestDemo extends BaseDO {

    /**
     * 主键
     */
    @TableId
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