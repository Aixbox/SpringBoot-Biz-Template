package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysConfig;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 参数配置 更新参数
 */
@Data
@AutoMapper(target = SysConfig.class)
public class SysConfigUpdateReq {

    /**
    * 参数主键
    */
    @NotNull
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
