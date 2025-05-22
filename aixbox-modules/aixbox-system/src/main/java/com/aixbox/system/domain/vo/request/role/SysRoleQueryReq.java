package com.aixbox.system.domain.vo.request.role;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Data
public class SysRoleQueryReq {


    /**
     * 角色ID
     */
    private Long roleId;


    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;




    public SysRoleQueryReq(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

}
