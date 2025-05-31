package com.aixbox.system.domain.vo.response;

import com.aixbox.system.domain.entity.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜单 返回参数
 */
@Data
@AutoMapper(target = SysMenu.class)
public class SysMenuResp {

    /**
    * 菜单ID
    */
    private Long id;
    /**
    * 菜单名称
    */
    private String menuName;
    /**
    * 父菜单ID
    */
    private Long parentId;
    /**
    * 显示顺序
    */
    private Long orderNum;
    /**
    * 路由地址
    */
    private String path;
    /**
    * 组件路径
    */
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
    private String isCache;
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
    * 创建时间
    */
    private LocalDateTime createTime;
                            /**
    * 备注
    */
    private String remark;


}
