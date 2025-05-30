package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 操作日志 新增参数
 */
@Data
public class SysOperateLogSaveReq {

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
