package com.aixbox.demo.domain.vo.response;

import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import java.util.Date;

/**
 * demo返回参数
 */
@Data
@ExcelIgnoreUnannotated
public class DemoTestResp {

            /**
             * id
             */
            @ExcelProperty(value = "id")
            private Long id;

            /**
             * 名字
             */
            @ExcelProperty(value = "名字")
            private String name;

            /**
             * 创建者
             */
            @ExcelProperty(value = "创建者")
            private String creator;

            /**
             * 创建时间
             */
            @ExcelProperty(value = "创建时间")
            private Date createTime;

            /**
             * 性别
             */
            @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
            @ExcelDictFormat(dictType = "sys_user_sex")
            private Long sex;

            /**
             * 是否
             */
            @ExcelProperty(value = "是否", converter = ExcelDictConvert.class)
            @ExcelDictFormat(dictType = "sys_yes_no")
            private Boolean isOrNot;



}
