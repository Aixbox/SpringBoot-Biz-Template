package com.aixbox.system.domain.bo;


import lombok.Data;

/**
 *
 */
@Data
public class SysUserBo {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userIds;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 排除不查询的用户(工作流用)
     */
    private String excludeUserIds;

}
