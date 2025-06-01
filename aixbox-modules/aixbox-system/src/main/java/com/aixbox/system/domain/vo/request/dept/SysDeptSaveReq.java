package com.aixbox.system.domain.vo.request.dept;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 部门 新增参数
 */
@Data
public class SysDeptSaveReq {

    /**
    * 父部门id
    */
    private Long parentId;
    /**
    * 祖级列表
    */
    private String ancestors;
    /**
    * 部门名称
    */
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过{max}个字符")
    private String deptName;
    /**
    * 部门类别编码
    */
    @Size(min = 0, max = 100, message = "部门类别编码长度不能超过{max}个字符")
    private String deptCategory;
    /**
    * 显示顺序
    */
    @NotNull(message = "显示顺序不能为空")
    private Long orderNum;
    /**
    * 负责人
    */
    private Long leader;
    /**
    * 联系电话
    */
    @Size(min = 0, max = 11, message = "联系电话长度不能超过{max}个字符")
    private String phone;
    /**
    * 邮箱
    */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    private String email;
    /**
    * 部门状态（0正常 1停用）
    */
    private String status;
                                        
}
