package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 参数配置 返回参数
 */
@Data
public class SysConfigResp {

    /**
    * 参数主键
    */
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
    * 创建时间
    */
    private LocalDateTime createTime;
                            /**
    * 备注
    */
    private String remark;


}
