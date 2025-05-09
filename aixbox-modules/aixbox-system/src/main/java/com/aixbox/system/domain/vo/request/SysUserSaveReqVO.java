package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户 新增参数
 */
@Data
public class SysUserSaveReqVO {

            /**
    * 部门ID
    */
    private Long deptId;
    /**
    * 用户账号
    */
    private String userName;
    /**
    * 用户昵称
    */
    private String nickName;
    /**
    * 用户类型（sys_user系统用户）
    */
    private String userType;
    /**
    * 岗位编号数组
    */
    private String postIds;
    /**
    * 用户邮箱
    */
    private String email;
    /**
    * 手机号码
    */
    private String phonenumber;
    /**
    * 用户性别（0男 1女 2未知）
    */
    private String sex;
    /**
    * 头像地址
    */
    private Long avatar;
    /**
    * 密码
    */
    private String password;
    /**
    * 帐号状态（0正常 1停用）
    */
    private String status;
    /**
    * 最后登录IP
    */
    private String loginIp;
    /**
    * 最后登录时间
    */
    private LocalDateTime loginDate;
                                            /**
    * 备注
    */
    private String remark;

}
