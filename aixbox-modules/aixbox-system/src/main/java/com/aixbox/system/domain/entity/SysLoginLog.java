package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问日志对象
 */
@TableName(value ="sys_login_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginLog extends BaseDO {

    /**
    * 访问ID
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 日志类型
    */
    private Long logType;

    /**
    * 链路追踪编号
    */
    private String traceId;

    /**
    * 用户编号
    */
    private Long userId;

    /**
    * 用户类型
    */
    private Long userType;

    /**
    * 用户账号
    */
    private String username;

    /**
    * 登陆结果
    */
    private Long result;

    /**
    * 用户 IP
    */
    private String userIp;

    /**
    * 浏览器 UA
    */
    private String userAgent;


}