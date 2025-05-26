package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysRoleDept;
import com.aixbox.system.domain.vo.request.SysRoleDeptPageReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptSaveReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptUpdateReq;
import com.aixbox.system.domain.vo.response.SysRoleDeptResp;
import com.aixbox.system.service.SysRoleDeptService;
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
 * 角色和部门关联 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/roleDept")
public class SysRoleDeptController {

    private final SysRoleDeptService sysRoleDeptService;

    /**
     * 新增角色和部门关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysRoleDeptSaveReq addReq) {
        Long sysRoleDeptId = sysRoleDeptService.addSysRoleDept(addReq);
        return success(sysRoleDeptId);
    }

    /**
     * 修改角色和部门关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysRoleDeptUpdateReq updateReq) {
        Boolean result = sysRoleDeptService.updateSysRoleDept(updateReq);
        return success(result);
    }

    /**
     * 删除角色和部门关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysRoleDeptService.deleteSysRoleDept(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取角色和部门关联详细信息
     * @param id 数据id
     * @return 角色和部门关联对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysRoleDeptResp> getSysRoleDept(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysRoleDept sysRoleDept = sysRoleDeptService.getSysRoleDept(id);
        return success(BeanUtils.toBean(sysRoleDept, SysRoleDeptResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 角色和部门关联分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysRoleDeptResp>> getSysRoleDeptPage(@Valid SysRoleDeptPageReq pageReq) {
        PageResult<SysRoleDept> pageResult = sysRoleDeptService.getSysRoleDeptPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysRoleDeptResp.class));
    }



}
