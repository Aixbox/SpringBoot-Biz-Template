package com.aixbox.system.domain.vo.request.menu;


import lombok.Data;

/**
 * 获取菜单列表请求
 */
@Data
public class SysMenuListReq {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

}
