package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.vo.request.role.SysRoleMenuPageReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleMenuUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysRoleMenuRespVO;
import com.aixbox.system.service.SysRoleMenuService;
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

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * 角色和菜单关联 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/roleMenu")
public class SysRoleMenuController {

    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 新增角色和菜单关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysRoleMenuSaveReqVO addReqVO) {
        Long sysRoleMenuId = sysRoleMenuService.addSysRoleMenu(addReqVO);
        return success(sysRoleMenuId);
    }

    /**
     * 修改角色和菜单关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysRoleMenuUpdateReqVO updateReqVO) {
        Boolean result = sysRoleMenuService.updateSysRoleMenu(updateReqVO);
        return success(result);
    }

    /**
     * 删除角色和菜单关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysRoleMenuService.deleteSysRoleMenu(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取角色和菜单关联详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysRoleMenuRespVO> getSysRoleMenu(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysRoleMenu sysRoleMenu = sysRoleMenuService.getSysRoleMenu(id);
        return success(BeanUtils.toBean(sysRoleMenu, SysRoleMenuRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysRoleMenuRespVO>> getSysRoleMenuPage(@Valid SysRoleMenuPageReqVO pageReqVO) {
        PageResult<SysRoleMenu> pageResult = sysRoleMenuService.getSysRoleMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysRoleMenuRespVO.class));
    }



}
