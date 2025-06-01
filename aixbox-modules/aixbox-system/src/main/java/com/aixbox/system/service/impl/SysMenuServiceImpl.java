package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.TreeBuildUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysMenuBo;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.vo.request.menu.SysMenuListReq;
import com.aixbox.system.domain.vo.request.menu.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.menu.SysMenuSaveReq;
import com.aixbox.system.domain.vo.request.menu.SysMenuUpdateReq;
import com.aixbox.system.domain.vo.response.MetaVO;
import com.aixbox.system.domain.vo.response.RouterVO;
import com.aixbox.system.domain.vo.response.SysMenuResp;
import com.aixbox.system.mapper.SysMenuMapper;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysRoleMenuMapper;
import com.aixbox.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
* 菜单 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMapper  sysRoleMapper;
    private final SysRoleMenuMapper  sysRoleMenuMapper;

    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysMenu(SysMenuSaveReq addReqVO) {
        SysMenu sysMenu = BeanUtils.toBean(addReqVO, SysMenu.class);
        sysMenuMapper.insert(sysMenu);
        return sysMenu.getId();
    }

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysMenu(SysMenuUpdateReq updateReqVO) {
        SysMenu sysMenu = MapstructUtils.convert(updateReqVO, SysMenu.class);
        return sysMenuMapper.updateById(sysMenu) > 0;
    }

    /**
     * 删除菜单
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysMenu(List<Long> ids) {
        return sysMenuMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取菜单详细数据
     * @param id 数据id
     * @return 菜单对象
     */
    @Override
    public SysMenu getSysMenu(Long id) {
        return sysMenuMapper.selectById(id);
    }

    /**
     * 分页查询菜单
     * @param pageReqVO 分页查询参数
     * @return 菜单分页对象
     */
    @Override
    public PageResult<SysMenu> getSysMenuPage(SysMenuPageReqVO pageReqVO) {
        return sysMenuMapper.selectPage(pageReqVO);
    }


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StrUtils.isNotEmpty(perm)) {
                permsSet.addAll(StrUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus;
        if (LoginHelper.isSuperAdmin(userId)) {
            menus = sysMenuMapper.selectMenuTreeAll();
        } else {
            menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);

    }

    /**
     * 构建前端路由所需要的菜单
     * 路由name命名规则 path首字母转大写 + id
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVO> buildMenus(List<SysMenu> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            String name = menu.getRouteName() + menu.getId();
            RouterVO router = new RouterVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(name);
            router.setPath(menu.getRouterPath());
            router.setComponent(menu.getComponentInfo());
            router.setQuery(menu.getQueryParam());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), StrUtils.equals("1",
                    menu.getIsCache().toString()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && SystemConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (menu.isMenuFrame()) {
                String frameName = StrUtils.capitalize(menu.getPath()) + menu.getId();
                router.setMeta(null);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(frameName);
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(),
                        StrUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && menu.isInnerLink()) {
                router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                String routerPath = SysMenu.innerLinkReplaceEach(menu.getPath());
                String innerLinkName = StrUtils.capitalize(routerPath) + menu.getId();
                children.setPath(routerPath);
                children.setComponent(SystemConstants.INNER_LINK);
                children.setName(innerLinkName);
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;

    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenuResp> selectMenuList(SysMenuListReq menu, Long userId) {
        List<SysMenu> menuList;
        // 管理员显示所有菜单信息
        if (LoginHelper.isSuperAdmin(userId)) {
            menuList = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                    .like(StrUtils.isNotBlank(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName())
                    .eq(StrUtils.isNotBlank(menu.getVisible()), SysMenu::getVisible, menu.getVisible())
                    .eq(StrUtils.isNotBlank(menu.getStatus()), SysMenu::getStatus, menu.getStatus())
                    .orderByAsc(SysMenu::getParentId)
                    .orderByAsc(SysMenu::getOrderNum));
        } else {
            QueryWrapper<SysMenu> wrapper = Wrappers.query();
            wrapper.inSql("r.id", "select role_id from sys_user_role where user_id = " + userId)
                   .like(StrUtils.isNotBlank(menu.getMenuName()), "m.menu_name", menu.getMenuName())
                   .eq(StrUtils.isNotBlank(menu.getVisible()), "m.visible", menu.getVisible())
                   .eq(StrUtils.isNotBlank(menu.getStatus()), "m.status", menu.getStatus())
                   .orderByAsc("m.parent_id")
                   .orderByAsc("m.order_num");
            menuList = sysMenuMapper.selectMenuListByUserId(wrapper);
        }
        return MapstructUtils.convert(menuList, SysMenuResp.class);


    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuResp selectMenuById(Long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectById(menuId);
        return MapstructUtils.convert(sysMenu, SysMenuResp.class);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree<Long>> buildMenuTreeSelect(List<SysMenuResp> menus) {
        if (CollUtil.isEmpty(menus)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(menus, (menu, tree) -> {
            Tree<Long> menuTree = tree.setId(menu.getId())
                                      .setParentId(menu.getParentId())
                                      .setName(menu.getMenuName())
                                      .setWeight(menu.getOrderNum());
            menuTree.put("menuType", menu.getMenuType());
            menuTree.put("icon", menu.getIcon());
        });
    }

    @Override
    public List<SysMenuResp> selectMenuList(Long userId) {
        return selectMenuList(new SysMenuListReq(), userId);
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);
        return sysMenuMapper.selectMenuListByRoleId(roleId, role.getMenuCheckStrictly());
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuBo 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenuBo menuBo) {
        boolean exist = sysMenuMapper.exists(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getMenuName, menuBo.getMenuName())
                .eq(SysMenu::getParentId, menuBo.getParentId())
                .ne(ObjectUtil.isNotNull(menuBo.getId()), SysMenu::getId, menuBo.getId()));
        return !exist;
    }

    /**
     * 是否存在菜单子节点
     *
     * @param id 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long id) {
        return sysMenuMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
    }

    /**
     * 查询菜单使用数量
     *
     * @param id 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long id) {
        return sysRoleMenuMapper.exists(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, id));
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = StreamUtils.filter(list, n -> n.getParentId().equals(t.getId()));
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            // 判断是否有子节点
            if (list.stream().anyMatch(n -> n.getParentId().equals(tChild.getId()))) {
                recursionFn(list, tChild);
            }
        }
    }

}




