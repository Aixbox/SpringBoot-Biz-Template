package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysUserRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysUserRoleRespVO;
import com.aixbox.system.service.SysUserRoleService;
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
 * 用户和角色关联 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userRole")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    /**
     * 新增用户和角色关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysUserRoleSaveReqVO addReqVO) {
        Long sysUserRoleId = sysUserRoleService.addSysUserRole(addReqVO);
        return success(sysUserRoleId);
    }

    /**
     * 修改用户和角色关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysUserRoleUpdateReqVO updateReqVO) {
        Boolean result = sysUserRoleService.updateSysUserRole(updateReqVO);
        return success(result);
    }

    /**
     * 删除用户和角色关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysUserRoleService.deleteSysUserRole(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取用户和角色关联详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysUserRoleRespVO> getSysUserRole(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysUserRole sysUserRole = sysUserRoleService.getSysUserRole(id);
        return success(BeanUtils.toBean(sysUserRole, SysUserRoleRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysUserRoleRespVO>> getSysUserRolePage(@Valid SysUserRolePageReqVO pageReqVO) {
        PageResult<SysUserRole> pageResult = sysUserRoleService.getSysUserRolePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysUserRoleRespVO.class));
    }



}
