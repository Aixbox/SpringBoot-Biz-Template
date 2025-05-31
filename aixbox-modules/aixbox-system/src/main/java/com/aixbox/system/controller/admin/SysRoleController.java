package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.bo.SysRoleBo;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.role.SysRoleChangeStatusReq;
import com.aixbox.system.domain.vo.request.role.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleQueryReq;
import com.aixbox.system.domain.vo.request.role.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleUpdateDataScopeReq;
import com.aixbox.system.domain.vo.request.role.SysRoleUpdateReq;
import com.aixbox.system.domain.vo.request.user.SysUserQueryReq;
import com.aixbox.system.domain.vo.response.DeptTreeSelectResp;
import com.aixbox.system.domain.vo.response.SysRoleResp;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.service.SysDeptService;
import com.aixbox.system.service.SysRoleService;
import com.aixbox.system.service.SysUserService;
import jakarta.servlet.http.HttpServletResponse;
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

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;
import static com.aixbox.system.constant.ErrorCodeConstants.BULK_AUTH_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.BULK_REVOKE_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.CHANGE_STATUS_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.REVOKE_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.ROLE_KEY_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.ROLE_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_DATA_SCOPE_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_ROLE_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_ROLE_KEY_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_ROLE_NAME_EXIST;

/**
 * 角色 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;
    private final SysDeptService sysDeptService;


    /**
     * 查询已分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public CommonResult<PageResult<SysUserResp>> allocatedList( SysUserQueryReq user,
                                                               @Valid PageParam pageQuery) {
        PageResult<SysUserResp> pageResult = sysUserService.selectAllocatedList(user, pageQuery);
        return success(pageResult);
    }

    /**
     * 查询未分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public CommonResult<PageResult<SysUserResp>> unallocatedList(SysUserBo user, @Valid  PageParam pageQuery) {
        return success(sysUserService.selectUnallocatedList(user, pageQuery));
    }

    /**
     * 取消授权用户
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/authUser/cancel")
    public CommonResult<Void> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return toAjax(sysRoleService.deleteAuthUser(userRole), REVOKE_USER_ERROR);
    }


    /**
     * 批量取消授权用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/authUser/cancelAll")
    public CommonResult<Void> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return toAjax(sysRoleService.deleteAuthUsers(roleId, userIds), BULK_REVOKE_USER_ERROR);
    }


    /**
     * 批量选择用户授权
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/authUser/selectAll")
    public CommonResult<Void> selectAuthUserAll(Long roleId, Long[] userIds) {
        sysRoleService.checkRoleDataScope(roleId);
        return toAjax(sysRoleService.insertAuthUsers(roleId, userIds), BULK_AUTH_USER_ERROR);
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/changeStatus")
    public CommonResult<Void> changeStatus(@RequestBody SysRoleChangeStatusReq role) {
        sysRoleService.checkRoleAllowed(BeanUtils.toBean(role, SysRoleBo.class));
        sysRoleService.checkRoleDataScope(role.getId());
        return toAjax(sysRoleService.updateRoleStatus(role.getId(), role.getStatus()),
                CHANGE_STATUS_ERROR);
    }


    /**
     * 修改保存数据权限
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping("/dataScope")
    public CommonResult<Void> dataScope(@RequestBody SysRoleUpdateDataScopeReq role) {
        sysRoleService.checkRoleAllowed(BeanUtils.toBean(role, SysRoleBo.class));
        sysRoleService.checkRoleDataScope(role.getRoleId());
        return toAjax(sysRoleService.authDataScope(role), UPDATE_DATA_SCOPE_ERROR);
    }

    /**
     * 导出角色信息列表
     */
    @SaCheckPermission("system:role:export")
    @PostMapping("/export")
    public void export(SysRoleQueryReq role, HttpServletResponse response) {
        List<SysRole> sysRoles = sysRoleService.selectRoleList(role);
        List<SysRoleResp> list = BeanUtils.toBean(sysRoles, SysRoleResp.class);
        ExcelUtil.exportExcel(list, "角色数据", SysRoleResp.class, response);
    }



    /**
     * 获取对应角色部门树列表
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:role:list")
    @GetMapping(value = "/deptTree/{roleId}")
    public CommonResult<DeptTreeSelectResp> roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        DeptTreeSelectResp selectVo = new DeptTreeSelectResp();
        selectVo.setCheckedKeys(sysDeptService.selectDeptListByRoleId(roleId));
        selectVo.setDepts(sysDeptService.selectDeptTreeList(new SysDeptBo()));
        return success(selectVo);
    }










    /**
     * 新增角色
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @SaCheckPermission("system:role:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysRoleSaveReqVO addReqVO) {
        SysRoleBo sysRolebo = BeanUtils.toBean(addReqVO, SysRoleBo.class);
        sysRoleService.checkRoleAllowed(sysRolebo);
        if (!sysRoleService.checkRoleNameUnique(sysRolebo)) {
            return error(ROLE_NAME_EXIST, addReqVO.getRoleName());
        } else if (!sysRoleService.checkRoleKeyUnique(sysRolebo)) {
            return error(ROLE_KEY_EXIST, addReqVO.getRoleName());
        }
        Long sysRoleId = sysRoleService.addSysRole(addReqVO);
        return success(sysRoleId);
    }

    /**
     * 修改角色
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysRoleUpdateReq updateReqVO) {
        SysRoleBo roleBo = BeanUtils.toBean(updateReqVO, SysRoleBo.class);
        sysRoleService.checkRoleAllowed(roleBo);
        sysRoleService.checkRoleDataScope(roleBo.getId());
        if (!sysRoleService.checkRoleNameUnique(roleBo)) {
            return error(UPDATE_ROLE_NAME_EXIST, updateReqVO.getRoleName());
        } else if (!sysRoleService.checkRoleKeyUnique(roleBo)) {
            return error(UPDATE_ROLE_KEY_EXIST, updateReqVO.getRoleName());
        }
        if (sysRoleService.updateSysRole(updateReqVO)) {
            sysRoleService.cleanOnlineUserByRole(updateReqVO.getId());
            return success();
        }
        return error(UPDATE_ROLE_ERROR, updateReqVO.getRoleName());
    }

    /**
     * 删除角色
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@PathVariable Long[] ids) {
        Boolean result = sysRoleService.deleteSysRole(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取角色详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysRoleResp> getSysRole(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getSysRole(id);
        return success(BeanUtils.toBean(sysRole, SysRoleResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysRoleResp>> getSysRolePage(@Valid SysRolePageReqVO pageReqVO) {
        PageResult<SysRole> pageResult = sysRoleService.getSysRolePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysRoleResp.class));
    }



}
