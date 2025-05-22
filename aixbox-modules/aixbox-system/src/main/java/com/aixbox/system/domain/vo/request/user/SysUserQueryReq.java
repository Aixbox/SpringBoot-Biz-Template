package com.aixbox.system.domain.vo.request.user;


import lombok.Data;

/**
 *
 */
@Data
public class SysUserQueryReq {


    /**
     * 数据权限 当前角色ID
     */
    private Long roleId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 手机号码
     */
    private String phonenumber;

}
