package com.aixbox.system.domain.vo.request.dept;

import com.aixbox.system.domain.entity.SysDept;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 部门 更新参数
 */
@Data
@AutoMapper(target = SysDept.class)
public class SysDeptUpdateReq {

    /**
    * 部门id
    */
    @NotNull
    private Long id;
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
    private String phone;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 部门状态（0正常 1停用）
    */
    private String status;
                                        
}
