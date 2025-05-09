package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色和菜单关联 返回参数
 */
@Data
public class SysRoleMenuRespVO {

    /**
    * 自增编号
    */
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 菜单ID
    */
    private Long menuId;
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
