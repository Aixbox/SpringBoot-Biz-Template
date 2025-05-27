package com.aixbox.system.domain.vo.response;


import lombok.Data;

import java.util.List;

/**
 * 用户信息
 */
@Data
public class SysUserInfoResp {

    /**
     * 用户信息
     */
    private SysUserResp user;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

    /**
     * 角色列表
     */
    private List<SysRoleResp> roles;

    /**
     * 岗位ID列表
     */
    private List<Long> postIds;

    /**
     * 岗位列表
     */
    private List<SysPostResp> posts;

}
