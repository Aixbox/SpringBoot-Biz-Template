package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysDept;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 部门 更新参数
 */
@Data
@AutoMapper(target = SysDept.class)
public class SysDeptUpdateReqVO {

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
    private String deptName;
    /**
    * 部门类别编码
    */
    private String deptCategory;
    /**
    * 显示顺序
    */
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
