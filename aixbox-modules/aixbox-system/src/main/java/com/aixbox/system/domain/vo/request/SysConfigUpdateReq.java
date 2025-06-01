package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysConfig;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过{max}个字符")
    private String configName;
    /**
    * 参数键名
    */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过{max}个字符")
    private String configKey;
    /**
    * 参数键值
    */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过{max}个字符")
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
