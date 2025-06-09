package com.aixbox.system.domain.vo.request;

import com.aixbox.demo.domain.entity.DemoTest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * OSS对象存储更新参数
 */
@Data
public class SysOssUpdateReq {

            /**
             * 文件名
             */
                @NotBlank(message = "文件名不能为空")
        private String fileName;

            /**
             * 原名
             */
                @NotBlank(message = "原名不能为空")
        private String originalName;

            /**
             * 文件后缀名
             */
                @NotBlank(message = "文件后缀名不能为空")
        private String fileSuffix;

            /**
             * URL地址
             */
                @NotBlank(message = "URL地址不能为空")
        private String url;

            /**
             * 扩展字段
             */
        private String ext1;

            /**
             * 服务商
             */
                @NotBlank(message = "服务商不能为空")
        private String service;


}
