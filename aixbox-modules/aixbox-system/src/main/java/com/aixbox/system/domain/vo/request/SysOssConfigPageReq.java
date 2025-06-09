package com.aixbox.system.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 对象存储配置 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOssConfigPageReq extends PageParam {

            /**
             * 配置key
             */
            private String configKey;


            /**
             * accessKey
             */
            private String accessKey;


            /**
             * 秘钥
             */
            private String secretKey;


            /**
             * 桶名称
             */
            private String bucketName;


            /**
             * 前缀
             */
            private String prefix;


            /**
             * 访问站点
             */
            private String endpoint;


            /**
             * 自定义域名
             */
            private String domain;


            /**
             * 是否https（Y=是,N=否）
             */
            private String isHttps;


            /**
             * 域
             */
            private String region;


            /**
             * 桶权限类型(0=private 1=public 2=custom)
             */
            private String accessPolicy;


            /**
             * 是否默认（0=是,1=否）
             */
            private String status;


            /**
             * 扩展字段
             */
            private String ext1;


            /**
             * 创建时间
             */
            private LocalDate createTime;




}
