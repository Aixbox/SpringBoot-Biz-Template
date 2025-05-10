package com.aixbox.system.service;


import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.enums.LoginType;

import java.util.function.Supplier;

/**
 * 登录校验方法
 */
public interface SysLoginService {
    void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier);

    LoginUser buildLoginUser(SysUser user);
}
