package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 参数配置 新增参数
 */
@Data
public class SysConfigSaveReq {

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
