package com.aixbox.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysMenuBo;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.menu.SysMenuListReq;
import com.aixbox.system.domain.vo.request.menu.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.menu.SysMenuSaveReq;
import com.aixbox.system.domain.vo.request.menu.SysMenuUpdateReq;
import com.aixbox.system.domain.vo.response.RouterVO;
import com.aixbox.system.domain.vo.response.SysMenuResp;

import java.util.List;
import java.util.Set;

/**
* 菜单 Service接口
*/
public interface SysMenuService {

    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysMenu(SysMenuSaveReq addReqVO);

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysMenu(SysMenuUpdateReq updateReqVO);

    /**
     * 删除菜单
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysMenu(List<Long> ids);

    /**
     * 获取菜单详细数据
     * @param id 数据id
     * @return 菜单对象
     */
    SysMenu getSysMenu(Long id);

    /**
     * 分页查询菜单
     * @param pageReqVO 分页查询参数
     * @return 菜单分页对象
     */
    PageResult<SysMenu> getSysMenuPage(SysMenuPageReqVO pageReqVO);

    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVO> buildMenus(List<SysMenu> menus);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   查询信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuResp> selectMenuList(SysMenuListReq menu, Long userId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenuResp selectMenuById(Long menuId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<Tree<Long>> buildMenuTreeSelect(List<SysMenuResp> menus);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuResp> selectMenuList(Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuBo 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenuBo menuBo);

    /**
     * 是否存在菜单子节点
     *
     * @param id 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(Long id);

    /**
     * 查询菜单是否存在角色
     *
     * @param id 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(Long id);
}
