package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据对象
 */
@TableName(value ="sys_dict_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseDO {

    /**
    * 字典编码dict_code
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 字典排序
    */
    private Long dictSort;

    /**
    * 字典标签
    */
    private String dictLabel;

    /**
    * 字典键值
    */
    private String dictValue;

    /**
    * 字典类型
    */
    private String dictType;

    /**
    * 样式属性（其他样式扩展）
    */
    private String cssClass;

    /**
    * 表格回显样式
    */
    private String listClass;

    /**
    * 是否默认（Y是 N否）
    */
    private String isDefault;

    /**
    * 备注
    */
    private String remark;


}