package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访问日志 返回参数
 */
@Data
public class SysLoginLogResp {

    /**
    * 访问ID
    */
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
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
