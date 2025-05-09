package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 角色和菜单关联 新增参数
 */
@Data
public class SysRoleMenuSaveReqVO {

            /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 菜单ID
    */
    private Long menuId;
                                        
}
