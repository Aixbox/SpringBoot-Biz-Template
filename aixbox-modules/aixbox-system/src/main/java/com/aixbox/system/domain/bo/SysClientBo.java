package com.aixbox.system.domain.bo;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class SysClientBo {

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

    /**
     * 授权类型
     */
    @NotNull(message = "授权类型不能为空")
    private List<String> grantTypeList;

}
