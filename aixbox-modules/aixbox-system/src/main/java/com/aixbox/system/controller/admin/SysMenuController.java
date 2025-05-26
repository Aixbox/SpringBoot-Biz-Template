package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.lang.tree.Tree;
import com.aixbox.common.core.constant.AdminConstants;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysMenuBo;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.menu.SysMenuListReq;
import com.aixbox.system.domain.vo.request.menu.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.menu.SysMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.menu.SysMenuUpdateReqVO;
import com.aixbox.system.domain.vo.response.MenuTreeSelectResp;
import com.aixbox.system.domain.vo.response.RouterVO;
import com.aixbox.system.domain.vo.response.SysMenuResp;
import com.aixbox.system.service.SysMenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;
import static com.aixbox.system.constant.ErrorCodeConstants.ADDRESS_NOT_HTTP;
import static com.aixbox.system.constant.ErrorCodeConstants.EXIST_CHILD_MENU;
import static com.aixbox.system.constant.ErrorCodeConstants.MENU_EXIST_ROLE;
import static com.aixbox.system.constant.ErrorCodeConstants.MENU_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_MENU_ADDRESS_NOT_HTTP;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_MENU_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_MENU_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_MENU_PARENT_ERROR;

/**
 * 菜单 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;



    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public CommonResult<List<RouterVO>> getRouters() {
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(LoginHelper.getUserId());
        return success(sysMenuService.buildMenus(menus));
    }


    /**
     * 获取菜单列表
     */
    @SaCheckRole(value = {
            AdminConstants.SUPER_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public CommonResult<List<SysMenuResp>> list(SysMenuListReq menu) {
        List<SysMenuResp> menus = sysMenuService.selectMenuList(menu, LoginHelper.getUserId());
        return success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     *
     * @param menuId 菜单ID
     */
    @SaCheckRole(value = {
            AdminConstants.SUPER_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public CommonResult<SysMenuResp> getInfo(@PathVariable Long menuId) {
        return success(sysMenuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/treeselect")
    public CommonResult<List<Tree<Long>>> treeselect(SysMenuListReq menu) {
        List<SysMenuResp> menus = sysMenuService.selectMenuList(menu, LoginHelper.getUserId());
        return success(sysMenuService.buildMenuTreeSelect(menus));
    }


    /**
     * 加载对应角色菜单列表树
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public CommonResult<MenuTreeSelectResp> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<SysMenuResp> menus = sysMenuService.selectMenuList(LoginHelper.getUserId());
        MenuTreeSelectResp selectVo = new MenuTreeSelectResp();
        selectVo.setCheckedKeys(sysMenuService.selectMenuListByRoleId(roleId));
        selectVo.setMenus(sysMenuService.buildMenuTreeSelect(menus));
        return success(selectVo);
    }







    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @SaCheckRole(AdminConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysMenuSaveReqVO addReqVO) {
        SysMenuBo menuBo = BeanUtils.toBean(addReqVO, SysMenuBo.class);
        if (!sysMenuService.checkMenuNameUnique(menuBo)) {
            return error(MENU_NAME_EXIST, addReqVO.getMenuName());
        } else if (SystemConstants.YES_FRAME.equals(addReqVO.getIsFrame()) && !StrUtils.ishttp(addReqVO.getPath())) {
            return error(ADDRESS_NOT_HTTP, addReqVO.getMenuName());
        }
        Long sysMenuId = sysMenuService.addSysMenu(addReqVO);
        return success(sysMenuId);
    }

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Void> edit(@Valid @RequestBody SysMenuUpdateReqVO updateReqVO) {
        SysMenuBo menuBo = BeanUtils.toBean(updateReqVO, SysMenuBo.class);
        if (!sysMenuService.checkMenuNameUnique(menuBo)) {
            return error(UPDATE_MENU_NAME_EXIST, updateReqVO.getMenuName());
        } else if (SystemConstants.YES_FRAME.equals(updateReqVO.getIsFrame()) && !StrUtils.ishttp(updateReqVO.getPath())) {
            return error(UPDATE_MENU_ADDRESS_NOT_HTTP, updateReqVO.getMenuName());
        } else if (updateReqVO.getId().equals(updateReqVO.getParentId())) {
            return error(UPDATE_MENU_PARENT_ERROR, updateReqVO.getMenuName());
        }
        Boolean result = sysMenuService.updateSysMenu(updateReqVO);
        return toAjax(result, UPDATE_MENU_ERROR);
    }

    /**
     * 删除菜单
     * @param id 删除id
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable Long id) {
        if (sysMenuService.hasChildByMenuId(id)) {
            return error(EXIST_CHILD_MENU);
        }
        if (sysMenuService.checkMenuExistRole(id)) {
            return error(MENU_EXIST_ROLE);
        }
        Boolean result = sysMenuService.deleteSysMenu(Arrays.asList(id));
        return success(result);
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysMenuResp>> getSysMenuPage(@Valid SysMenuPageReqVO pageReqVO) {
        PageResult<SysMenu> pageResult = sysMenuService.getSysMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysMenuResp.class));
    }



}
