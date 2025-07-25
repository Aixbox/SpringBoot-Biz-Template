package com.aixbox.system.domain.vo.request.user;

import com.aixbox.system.domain.entity.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;

import java.time.LocalDateTime;


/**
 * 用户 更新参数
 */
@Data
@AutoMapper(target = SysUser.class)
public class SysUserUpdateReq {

    /**
    * 用户ID
    */
    @NotNull
    private Long id;
    /**
    * 部门ID
    */
    private Long deptId;
    /**
    * 用户账号
    */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过{max}个字符")
    private String userName;
    /**
    * 用户昵称
    */
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过{max}个字符")
    private String nickName;
    /**
    * 用户类型（sys_user系统用户）
    */
    private String userType;
    /**
    * 岗位编号数组
    */
    private Long[] postIds;
    /**
    * 用户邮箱
    */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
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
