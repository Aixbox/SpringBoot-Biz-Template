package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联对象
 */
@TableName(value ="sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends BaseDO {

    /**
    * 自增编号
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 角色ID
    */
    private Long roleId;

    /**
    * 菜单ID
    */
    private Long menuId;


}