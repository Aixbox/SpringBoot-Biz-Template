package com.aixbox.system.domain.vo.request.role;


import lombok.Data;

/**
 *
 */
@Data
public class SysRoleChangeStatusReq {


    /**
     * 角色ID
     */
    private Long id;


    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;




}
