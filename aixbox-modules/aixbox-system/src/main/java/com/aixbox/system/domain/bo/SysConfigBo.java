package com.aixbox.system.domain.bo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

}
