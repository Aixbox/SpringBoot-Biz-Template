package com.aixbox.system.domain.bo;


import lombok.Data;

import java.time.LocalDateTime;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    private Long createBy;

    /**
     * 密码
     */
    private String password;

    /**
     * 岗位组
     */
    private Long[] postIds;


}
