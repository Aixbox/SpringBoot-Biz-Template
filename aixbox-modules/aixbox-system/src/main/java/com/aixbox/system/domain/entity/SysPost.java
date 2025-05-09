package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位对象
 */
@TableName(value ="sys_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPost extends BaseDO {

    /**
    * 岗位ID
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 部门id
    */
    private Long deptId;

    /**
    * 岗位编码
    */
    private String postCode;

    /**
    * 岗位类别编码
    */
    private String postCategory;

    /**
    * 岗位名称
    */
    private String postName;

    /**
    * 显示顺序
    */
    private Long postSort;

    /**
    * 状态（0正常 1停用）
    */
    private String status;

    /**
    * 备注
    */
    private String remark;


}