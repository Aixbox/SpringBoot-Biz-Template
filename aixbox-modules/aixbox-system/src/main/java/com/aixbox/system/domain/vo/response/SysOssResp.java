package com.aixbox.system.domain.vo.response;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * OSS对象存储返回参数
 */
@Data
@ExcelIgnoreUnannotated
public class SysOssResp {

            /**
             * 对象存储主键
             */
            @ExcelProperty(value = "对象存储主键")
            private Long id;

            /**
             * 文件名
             */
            @ExcelProperty(value = "文件名")
            private String fileName;

            /**
             * 原名
             */
            @ExcelProperty(value = "原名")
            private String originalName;

            /**
             * 文件后缀名
             */
            @ExcelProperty(value = "文件后缀名")
            private String fileSuffix;

            /**
             * URL地址
             */
            @ExcelProperty(value = "URL地址")
            private String url;

            /**
             * 扩展字段
             */
            @ExcelProperty(value = "扩展字段")
            private String ext1;

            /**
             * 服务商
             */
            @ExcelProperty(value = "服务商")
            private String service;

            /**
             * 创建时间
             */
            @ExcelProperty(value = "创建时间")
            private LocalDate createTime;



}
