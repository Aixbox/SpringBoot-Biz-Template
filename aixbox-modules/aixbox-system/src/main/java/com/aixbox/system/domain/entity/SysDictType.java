package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型对象
 */
@TableName(value ="sys_dict_type")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends BaseDO {

    /**
    * 字典主键
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 字典名称
    */
    private String dictName;

    /**
    * 字典类型
    */
    private String dictType;

    /**
    * 备注
    */
    private String remark;


}