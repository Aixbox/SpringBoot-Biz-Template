package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端对象
 */
@TableName(value ="sys_client")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysClient extends BaseDO {

    /**
    * id
    */
    @TableId(value = "id")
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