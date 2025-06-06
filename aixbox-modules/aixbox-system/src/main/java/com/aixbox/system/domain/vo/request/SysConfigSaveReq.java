package com.aixbox.system.domain.vo.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 参数配置 新增参数
 */
@Data
public class SysConfigSaveReq {

    /**
    * 参数名称
    */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过{max}个字符")
    private String configName;
    /**
    * 参数键名
    */
    @NotBlank(message = "参数键名不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过{max}个字符")
    private String configKey;
    /**
    * 参数键值
    */
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过{max}个字符")
    private String configValue;
    /**
    * 系统内置（Y是 N否）
    */
    private Boolean configType;
    /**
    * 备注
    */
    private String remark;

}
