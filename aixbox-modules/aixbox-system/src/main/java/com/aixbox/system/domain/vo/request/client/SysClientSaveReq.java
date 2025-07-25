package com.aixbox.system.domain.vo.request.client;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 客户端 新增参数
 */
@Data
public class SysClientSaveReq {

    /**
    * 客户端id
    */
    private String clientId;
    /**
    * 客户端key
    */
    @NotBlank(message = "客户端key不能为空")
    private String clientKey;
    /**
    * 客户端秘钥
    */
    @NotBlank(message = "客户端秘钥不能为空")
    private String clientSecret;
    /**
    * 授权类型
    */
    @NotNull(message = "授权类型不能为空")
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
