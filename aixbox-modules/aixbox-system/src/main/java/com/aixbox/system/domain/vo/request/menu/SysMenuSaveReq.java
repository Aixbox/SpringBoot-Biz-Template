package com.aixbox.system.domain.vo.request.menu;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 菜单 新增参数
 */
@Data
public class SysMenuSaveReq {

    /**
    * 菜单名称
    */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过{max}个字符")
    private String menuName;
    /**
    * 父菜单ID
    */
    private Long parentId;
    /**
    * 显示顺序
    */
    @NotNull(message = "显示顺序不能为空")
    private Long orderNum;
    /**
    * 路由地址
    */
    @Size(min = 0, max = 200, message = "路由地址不能超过{max}个字符")
    private String path;
    /**
    * 组件路径
    */
    @Size(min = 0, max = 200, message = "组件路径不能超过{max}个字符")
    private String component;
    /**
    * 路由参数
    */
    private String queryParam;
    /**
    * 是否为外链（0是 1否）
    */
    private String isFrame;
    /**
    * 是否缓存（0缓存 1不缓存）
    */
    private Long isCache;
    /**
    * 菜单类型（M目录 C菜单 F按钮）
    */
    private String menuType;
    /**
    * 显示状态（0显示 1隐藏）
    */
    private String visible;
    /**
    * 菜单状态（0正常 1停用）
    */
    private String status;
    /**
    * 权限标识
    */
    private String perms;
    /**
    * 菜单图标
    */
    private String icon;
    /**
    * 备注
    */
    private String remark;

}
