package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysLoginLog;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 访问日志 更新参数
 */
@Data
@AutoMapper(target = SysLoginLog.class)
public class SysLoginLogUpdateReq {

    /**
    * 访问ID
    */
    @NotNull
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
