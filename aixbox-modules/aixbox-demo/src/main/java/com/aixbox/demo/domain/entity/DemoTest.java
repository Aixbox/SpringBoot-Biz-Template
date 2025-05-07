package com.aixbox.demo.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 【请填写功能名称】对象
 */
@TableName(value ="demo_test")
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoTest extends BaseDO {

    /**
    * id
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 名字
    */
    private String name;


}