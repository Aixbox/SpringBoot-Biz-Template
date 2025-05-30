package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志对象
 */
@TableName(value ="sys_operate_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperateLog extends BaseDO {

    /**
    * 日志主键
    */
    @TableId(value = "id")
    private Long id;

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
    * 操作模块类型
    */
    private String type;

    /**
    * 操作名
    */
    private String subType;

    /**
    * 操作数据模块编号
    */
    private Long bizId;

    /**
    * 操作内容
    */
    private String action;

    /**
    * 操作结果
    */
    private Long success;

    /**
    * 拓展字段
    */
    private String extra;

    /**
    * 请求方法名
    */
    private String requestMethod;

    /**
    * 请求地址
    */
    private String requestUrl;

    /**
    * 用户 IP
    */
    private String userIp;

    /**
    * 浏览器 UA
    */
    private String userAgent;


}