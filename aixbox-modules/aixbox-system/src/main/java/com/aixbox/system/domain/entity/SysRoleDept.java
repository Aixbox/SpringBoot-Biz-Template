package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和部门关联对象
 */
@TableName(value ="sys_role_dept")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDept extends BaseDO {

    /**
    * id
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 角色ID
    */
    private Long roleId;

    /**
    * 部门ID
    */
    private Long deptId;


}