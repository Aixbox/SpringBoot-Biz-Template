package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 访问日志 新增参数
 */
@Data
public class SysLoginLogSaveReq {

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
