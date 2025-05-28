package com.aixbox.system.domain.bo;


import lombok.Data;

/**
 *
 */
@Data
public class SysConfigBo {

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
     * 系统内置（Y是 N否）
     */
    private String configType;

}
