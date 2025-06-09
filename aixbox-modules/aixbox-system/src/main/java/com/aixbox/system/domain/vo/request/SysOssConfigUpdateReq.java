package com.aixbox.system.domain.vo.request;

import com.aixbox.demo.domain.entity.DemoTest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 对象存储配置更新参数
 */
@Data
public class SysOssConfigUpdateReq {

            /**
             * 配置key
             */
                @NotBlank(message = "配置key不能为空")
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
                @NotBlank(message = "桶权限类型(0=private 1=public 2=custom)不能为空")
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
             * 备注
             */
        private String remark;


}
