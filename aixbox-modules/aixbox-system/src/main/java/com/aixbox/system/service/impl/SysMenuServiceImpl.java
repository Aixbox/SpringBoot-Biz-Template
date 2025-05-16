package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysMenuUpdateReqVO;
import com.aixbox.system.domain.vo.response.MetaVO;
import com.aixbox.system.domain.vo.response.RouterVO;
import com.aixbox.system.mapper.SysMenuMapper;
import com.aixbox.system.service.SysMenuService;
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

    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysMenu(SysMenuSaveReqVO addReqVO) {
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
    public Boolean updateSysMenu(SysMenuUpdateReqVO updateReqVO) {
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




