package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysMenuUpdateReqVO;
import com.aixbox.system.domain.vo.response.RouterVO;
import com.aixbox.system.domain.vo.response.SysMenuRespVO;
import com.aixbox.system.service.SysMenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

import static com.aixbox.common.core.pojo.CommonResult.success;

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
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysMenuSaveReqVO addReqVO) {
        Long sysMenuId = sysMenuService.addSysMenu(addReqVO);
        return success(sysMenuId);
    }

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysMenuUpdateReqVO updateReqVO) {
        Boolean result = sysMenuService.updateSysMenu(updateReqVO);
        return success(result);
    }

    /**
     * 删除菜单
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysMenuService.deleteSysMenu(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取菜单详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysMenuRespVO> getSysMenu(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysMenu sysMenu = sysMenuService.getSysMenu(id);
        return success(BeanUtils.toBean(sysMenu, SysMenuRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysMenuRespVO>> getSysMenuPage(@Valid SysMenuPageReqVO pageReqVO) {
        PageResult<SysMenu> pageResult = sysMenuService.getSysMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysMenuRespVO.class));
    }



}
