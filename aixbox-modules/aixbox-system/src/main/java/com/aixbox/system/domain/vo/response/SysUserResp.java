package com.aixbox.system.domain.vo.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户 返回参数
 */
@Data
public class SysUserResp {

    /**
    * 用户ID
    */
    @ExcelProperty(value = "用户序号")
    private Long id;
    /**
    * 部门ID
    */
    private Long deptId;
    /**
    * 用户账号
    */
    @ExcelProperty(value = "登录名称")
    private String userName;
    /**
    * 用户昵称
    */
    @ExcelProperty(value = "用户名称")
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
    @ExcelProperty(value = "用户邮箱")
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
    * 账号状态（0正常 1停用）
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
    * 创建时间
    */
    private LocalDateTime createTime;
    /**
    * 备注
    */
    private String remark;

    /**
     * 角色对象
     */
    private List<SysRoleResp> roles;


}
