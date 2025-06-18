package com.aixbox.demo.domain.vo.response;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 测试返回参数
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
            private String inputType;

            /**
             * 性别
             */
            @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
            @ExcelDictFormat(dictType = "sys_user_sex")
            private Long sex;

            /**
             * 创建时间
             */
            @ExcelProperty(value = "创建时间")
            private LocalDate createTime;

            /**
             * int类型
             */
            @ExcelProperty(value = "int类型")
            private String integerType;

            /**
             * 文本域类型
             */
            @ExcelProperty(value = "文本域类型")
            private String textareaType;

            /**
             * 选择类型
             */
            @ExcelProperty(value = "选择类型")
            private String selectType;

                /**
                 * 选择类型Url
                 */
                private String selectTypeUrl;
            /**
             * 是否
             */
            @ExcelProperty(value = "是否", converter = ExcelDictConvert.class)
            @ExcelDictFormat(dictType = "sys_yes_no")
            private Boolean radioIsOrNot;

            /**
             * 复选框类型
             */
            @ExcelProperty(value = "复选框类型", converter = ExcelDictConvert.class)
            @ExcelDictFormat(dictType = "sys_device_type")
            private String checkboxType;



}
