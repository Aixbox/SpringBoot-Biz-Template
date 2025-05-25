package com.aixbox.system.domain.bo;


import lombok.Data;

/**
 *
 */
@Data
public class SysMenuBo {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

}
