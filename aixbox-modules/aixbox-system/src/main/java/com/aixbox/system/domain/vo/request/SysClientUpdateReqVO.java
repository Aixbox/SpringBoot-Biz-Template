package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysClient;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 客户端 更新参数
 */
@Data
@AutoMapper(target = SysClient.class)
public class SysClientUpdateReqVO {

    /**
    * id
    */
    @NotNull
    private Long id;
    /**
    * 客户端id
    */
    private String clientId;
    /**
    * 客户端key
    */
    private String clientKey;
    /**
    * 客户端秘钥
    */
    private String clientSecret;
    /**
    * 授权类型
    */
    private String grantType;
    /**
    * 设备类型
    */
    private String deviceType;
    /**
    * token活跃超时时间
    */
    private Long activeTimeout;
    /**
    * token固定超时
    */
    private Long timeout;
    /**
    * 状态（0正常 1停用）
    */
    private String status;
                                        
}
