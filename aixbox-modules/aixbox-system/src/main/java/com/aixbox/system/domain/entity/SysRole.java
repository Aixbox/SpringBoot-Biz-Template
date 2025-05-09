package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色对象
 */
@TableName(value ="sys_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseDO {

    /**
    * 角色ID
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 角色权限字符串
    */
    private String roleKey;

    /**
    * 显示顺序
    */
    private Long roleSort;

    /**
    * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限 6：部门及以下或本人数据权限）
    */
    private String dataScope;

    /**
    * 菜单树选择项是否关联显示
    */
    private Long menuCheckStrictly;

    /**
    * 部门树选择项是否关联显示
    */
    private Long deptCheckStrictly;

    /**
    * 角色状态（0正常 1停用）
    */
    private String status;

    /**
    * 备注
    */
    private String remark;


}