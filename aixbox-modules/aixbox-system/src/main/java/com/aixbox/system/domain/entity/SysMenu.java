package com.aixbox.system.domain.entity;

import com.aixbox.common.core.constant.Constants;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单对象
 */
@TableName(value ="sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseDO {

    /**
    * 菜单ID
    */
    @TableId(value = "id")
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
    private Long isFrame;

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

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 获取路由名称
     */
    public String getRouteName() {
        String routerName = StringUtils.capitalize(path);
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame()) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 是否为菜单内部跳转
     */
    public boolean isMenuFrame() {
        return getParentId() == 0L && SystemConstants.TYPE_MENU.equals(menuType) && isFrame.equals(SystemConstants.NO_FRAME);
    }

    /**
     * 获取路由地址
     */
    public String getRouterPath() {
        String routerPath = this.path;
        // 内链打开外网方式
        if (getParentId() != 0L && isInnerLink()) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0L == getParentId() && SystemConstants.TYPE_DIR.equals(getMenuType())
                && SystemConstants.NO_FRAME.equals(getIsFrame())) {
            routerPath = "/" + this.path;
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame()) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 是否为内链组件
     */
    public boolean isInnerLink() {
        return isFrame.equals(SystemConstants.NO_FRAME) && StrUtils.ishttp(path);
    }

    /**
     * 内链域名特殊字符替换
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, ".", ":"},
                new String[]{"", "", "", "/", "/"});
    }

    /**
     * 获取组件信息
     */
    public String getComponentInfo() {
        String component = SystemConstants.LAYOUT;
        if (StringUtils.isNotEmpty(this.component) && !isMenuFrame()) {
            component = this.component;
        } else if (StringUtils.isEmpty(this.component) && getParentId() != 0L && isInnerLink()) {
            component = SystemConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(this.component) && isParentView()) {
            component = SystemConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为parent_view组件
     */
    public boolean isParentView() {
        return getParentId() != 0L && SystemConstants.TYPE_DIR.equals(menuType);
    }


}