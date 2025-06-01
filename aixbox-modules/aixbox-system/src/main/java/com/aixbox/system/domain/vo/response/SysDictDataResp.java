package com.aixbox.system.domain.vo.response;

import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据 返回参数
 */
@Data
public class SysDictDataResp {

    /**
    * 字典编码dict_code
    */
    @ExcelProperty(value = "字典编码")
    private Long id;
    /**
    * 字典排序
    */
    @ExcelProperty(value = "字典排序")
    private Long dictSort;
    /**
    * 字典标签
    */
    @ExcelProperty(value = "字典标签")
    private String dictLabel;
    /**
    * 字典键值
    */
    @ExcelProperty(value = "字典键值")
    private String dictValue;
    /**
    * 字典类型
    */
    @ExcelProperty(value = "字典类型")
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
    @ExcelProperty(value = "是否默认", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String isDefault;
    /**
    * 创建时间
    */
    @ExcelProperty(value = "备注")
    private LocalDateTime createTime;
    /**
    * 备注
    */
    @ExcelProperty(value = "创建时间")
    private String remark;


}
