package com.aixbox.system.domain.vo.request;


import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 对象存储配置新增参数
 */
@Data
public class SysOssConfigSaveReq {

    /**
     * 配置key
     */
    @NotBlank(message = "配置key不能为空")
    @Size(min = 2, max = 100, message = "configKey长度必须介于{min}和{max} 之间")
    private String configKey;

    /**
     * accessKey
     */
    @NotBlank(message = "accessKey不能为空")
    @Size(min = 2, max = 100, message = "accessKey长度必须介于{min}和{max} 之间")
    private String accessKey;

    /**
     * 秘钥
     */
    @NotBlank(message = "secretKey不能为空")
    @Size(min = 2, max = 100, message = "secretKey长度必须介于{min}和{max} 之间")
    private String secretKey;

    /**
     * 桶名称
     */
    @NotBlank(message = "桶名称不能为空")
    @Size(min = 2, max = 100, message = "bucketName长度必须介于{min}和{max}之间")
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点
     */
    @NotBlank(message = "访问站点不能为空")
    @Size(min = 2, max = 100, message = "endpoint长度必须介于{min}和{max}之间")
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
