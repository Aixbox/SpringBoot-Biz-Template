package com.aixbox.demo.domain.vo.response;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

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



}
