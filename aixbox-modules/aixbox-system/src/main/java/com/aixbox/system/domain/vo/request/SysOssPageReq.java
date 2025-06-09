package com.aixbox.system.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * OSS对象存储 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOssPageReq extends PageParam {

            /**
             * 文件名
             */
            private String fileName;


            /**
             * 原名
             */
            private String originalName;


            /**
             * 文件后缀名
             */
            private String fileSuffix;


            /**
             * URL地址
             */
            private String url;


            /**
             * 扩展字段
             */
            private String ext1;


            /**
             * 服务商
             */
            private String service;


            /**
             * 创建时间
             */
            private LocalDate createTime;




}
