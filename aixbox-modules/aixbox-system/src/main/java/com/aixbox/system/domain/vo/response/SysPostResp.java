package com.aixbox.system.domain.vo.response;

import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位 返回参数
 */
@Data
public class SysPostResp {

    /**
    * 岗位ID
    */
    @ExcelProperty(value = "岗位序号")
    private Long id;
    /**
    * 部门id
    */
    @ExcelProperty(value = "部门id")
    private Long deptId;
    /**
    * 岗位编码
    */
    @ExcelProperty(value = "岗位编码")
    private String postCode;
    /**
    * 岗位类别编码
    */
    @ExcelProperty(value = "类别编码")
    private String postCategory;
    /**
    * 岗位名称
    */
    @ExcelProperty(value = "岗位名称")
    private String postName;
    /**
    * 显示顺序
    */
    @ExcelProperty(value = "岗位排序")
    private Long postSort;
    /**
    * 状态（0正常 1停用）
    */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;
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
