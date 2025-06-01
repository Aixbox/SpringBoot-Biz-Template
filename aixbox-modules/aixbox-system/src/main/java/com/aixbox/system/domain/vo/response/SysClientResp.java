package com.aixbox.system.domain.vo.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户端 返回参数
 */
@Data
public class SysClientResp {

    /**
    * id
    */
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
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
     * 授权类型
     */
    @ExcelProperty(value = "授权类型")
    private List<String> grantTypeList;
                        

}
