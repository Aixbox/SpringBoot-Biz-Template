package com.aixbox.system.domain.vo.response;

import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "参数主键")
    private Long id;
    /**
    * 参数名称
    */
    @ExcelProperty(value = "参数名称")
    private String configName;
    /**
    * 参数键名
    */
    @ExcelProperty(value = "参数键名")
    private String configKey;
    /**
    * 参数键值
    */
    @ExcelProperty(value = "参数键值")
    private String configValue;
    /**
    * 系统内置（Y是 N否）
    */
    @ExcelProperty(value = "系统内置", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private Boolean configType;
    /**
    * 备注
    */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
