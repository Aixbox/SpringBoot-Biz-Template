package com.aixbox.system.domain.vo.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 */
@Data
public class SysRoleChangeStatusReq {


    /**
     * 角色ID
     */
    private Long roleId;


    /**
     * 角色权限字符串
     */
    @NotBlank(message = "角色权限字符串不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过{max}个字符")
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;




}
