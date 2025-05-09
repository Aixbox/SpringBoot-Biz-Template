package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门对象
 */
@TableName(value ="sys_dept")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseDO {

    /**
    * 部门id
    */
    @TableId(value = "id")
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