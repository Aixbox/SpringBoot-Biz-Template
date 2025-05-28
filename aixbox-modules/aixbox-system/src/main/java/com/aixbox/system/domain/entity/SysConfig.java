package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数配置对象
 */
@TableName(value ="sys_config")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseDO {

    /**
    * 参数主键
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 参数名称
    */
    private String configName;

    /**
    * 参数键名
    */
    private String configKey;

    /**
    * 参数键值
    */
    private String configValue;

    /**
    * 系统内置（Y是 N否）
    */
    private String configType;

    /**
    * 备注
    */
    private String remark;


}