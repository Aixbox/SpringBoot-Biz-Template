package com.aixbox.system.service;


import com.aixbox.system.domain.vo.request.RegisterBody;

/**
 * 注册校验方法
 */
public interface SysRegisterService {

    /**
     * 注册
     */
    void register(RegisterBody registerBody);
}
