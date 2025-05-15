package com.aixbox.system.domain.vo.response;


import lombok.Data;

import java.util.Set;

/**
 * 登录用户信息
 */
@Data
public class UserInfoResp {

    /**
     * 用户基本信息
     */
    private SysUserResp user;


    /**
     * 菜单权限
     */
    private Set<String> permissions;

    /**
     * 角色权限
     */
    private Set<String> roles;

}
