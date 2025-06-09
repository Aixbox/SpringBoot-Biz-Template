package com.aixbox.system.domain.vo.response;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 对象存储配置返回参数
 */
@Data
@ExcelIgnoreUnannotated
public class SysOssConfigResp {

            /**
             * 主键
             */
            @ExcelProperty(value = "主键")
            private Long id;

            /**
             * 配置key
             */
            @ExcelProperty(value = "配置key")
            private String configKey;

            /**
             * accessKey
             */
            @ExcelProperty(value = "accessKey")
            private String accessKey;

            /**
             * 秘钥
             */
            @ExcelProperty(value = "秘钥")
            private String secretKey;

            /**
             * 桶名称
             */
            @ExcelProperty(value = "桶名称")
            private String bucketName;

            /**
             * 前缀
             */
            @ExcelProperty(value = "前缀")
            private String prefix;

            /**
             * 访问站点
             */
            @ExcelProperty(value = "访问站点")
            private String endpoint;

            /**
             * 自定义域名
             */
            @ExcelProperty(value = "自定义域名")
            private String domain;

            /**
             * 是否https（Y=是,N=否）
             */
            @ExcelProperty(value = "是否https", converter = ExcelDictConvert.class)
            @ExcelDictFormat(readConverterExp = "Y==是,N=否")
            private String isHttps;

            /**
             * 域
             */
            @ExcelProperty(value = "域")
            private String region;

            /**
             * 桶权限类型(0=private 1=public 2=custom)
             */
            @ExcelProperty(value = "桶权限类型(0=private 1=public 2=custom)")
            private String accessPolicy;

            /**
             * 是否默认（0=是,1=否）
             */
            @ExcelProperty(value = "是否默认", converter = ExcelDictConvert.class)
            @ExcelDictFormat(readConverterExp = "0==是,1=否")
            private String status;

            /**
             * 扩展字段
             */
            @ExcelProperty(value = "扩展字段")
            private String ext1;

            /**
             * 创建时间
             */
            @ExcelProperty(value = "创建时间")
            private LocalDate createTime;

            /**
             * 备注
             */
            @ExcelProperty(value = "备注")
            private String remark;



}
