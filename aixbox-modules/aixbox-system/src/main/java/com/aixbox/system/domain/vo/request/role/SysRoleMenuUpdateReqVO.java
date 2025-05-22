package com.aixbox.system.domain.vo.request.role;

import com.aixbox.system.domain.entity.SysRoleMenu;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 角色和菜单关联 更新参数
 */
@Data
@AutoMapper(target = SysRoleMenu.class)
public class SysRoleMenuUpdateReqVO {

    /**
    * 自增编号
    */
    @NotNull
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
