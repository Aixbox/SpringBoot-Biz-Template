package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptResp;
import com.aixbox.system.service.SysDeptService;
import com.aixbox.system.service.SysPostService;
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
import static com.aixbox.system.constant.ErrorCodeConstants.ADD_DEPT_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_DEPT_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.DEPT_EXIST_CHILD;
import static com.aixbox.system.constant.ErrorCodeConstants.DEPT_EXIST_USER;
import static com.aixbox.system.constant.ErrorCodeConstants.DEPT_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.EXIST_CHILD_DEPT;
import static com.aixbox.system.constant.ErrorCodeConstants.EXIST_POST;
import static com.aixbox.system.constant.ErrorCodeConstants.EXIST_USER;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_DEPT_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_DEPT_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_DEPT_PARENT_ERROR;

/**
 * 部门 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;
    private final SysPostService sysPostService;

    /**
     * 新增部门
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @SaCheckPermission("system:dept:add")
    @PostMapping("/add")
    public CommonResult<Void> add(@Valid @RequestBody SysDeptSaveReqVO addReqVO) {
        SysDeptBo sysDept = BeanUtils.toBean(addReqVO, SysDeptBo.class);
        SysDept dept = BeanUtils.toBean(addReqVO, SysDept.class);
        if (!sysDeptService.checkDeptNameUnique(sysDept)) {
            return error(DEPT_NAME_EXIST, sysDept.getDeptName());
        }
        return toAjax(sysDeptService.insertDept(dept), ADD_DEPT_ERROR);
    }

    /**
     * 修改部门
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Void> edit(@Valid @RequestBody SysDeptUpdateReqVO updateReqVO) {
        Long deptId = updateReqVO.getId();
        SysDeptBo dept = BeanUtils.toBean(updateReqVO, SysDeptBo.class);
        sysDeptService.checkDeptDataScope(deptId);
        if (!sysDeptService.checkDeptNameUnique(dept)) {
            return error(UPDATE_DEPT_NAME_EXIST, dept.getDeptName());
        } else if (dept.getParentId().equals(deptId)) {
            return error(UPDATE_DEPT_PARENT_ERROR, dept.getDeptName());
        } else if (StrUtils.equals(SystemConstants.DISABLE, dept.getStatus())) {
            if (sysDeptService.selectNormalChildrenDeptById(deptId) > 0) {
                return error(DEPT_EXIST_CHILD);
            } else if (sysDeptService.checkDeptExistUser(deptId)) {
                return error(DEPT_EXIST_USER);
            }
        }
        return toAjax(sysDeptService.updateDept(dept), UPDATE_DEPT_ERROR);
    }

    /**
     * 删除部门
     * @param id 删除id
     * @return 是否成功
     */
    @SaCheckPermission("system:dept:remove")
    @DeleteMapping("/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        if (sysDeptService.hasChildByDeptId(id)) {
            return error(EXIST_CHILD_DEPT);
        }
        if (sysDeptService.checkDeptExistUser(id)) {
            return error(EXIST_USER);
        }
        if (sysPostService.countPostByDeptId(id) > 0) {
            return error(EXIST_POST);
        }
        sysDeptService.checkDeptDataScope(id);
        return toAjax(sysDeptService.deleteDeptById(id), DELETE_DEPT_ERROR);
    }

    /**
     * 获取部门详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysDeptResp> getSysDept(@PathVariable("id") Long id) {
        sysDeptService.checkDeptDataScope(id);
        SysDeptResp sysDept = sysDeptService.getSysDept(id);
        return success(sysDept);
    }

    /**
     * 分页查询部门
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/page")
    public CommonResult<PageResult<SysDeptResp>> getSysDeptPage(@Valid SysDeptPageReqVO pageReqVO) {
        PageResult<SysDept> pageResult = sysDeptService.getSysDeptPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysDeptResp.class));
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list/exclude/{deptId}")
    public CommonResult<List<SysDeptResp>> excludeChild(@PathVariable(value = "deptId",
            required = false) Long deptId) {
        List<SysDeptResp> depts = sysDeptService.selectDeptList(new SysDeptBo());
        depts.removeIf(d -> d.getId().equals(deptId)
                || StrUtils.splitList(d.getAncestors()).contains(Convert.toStr(deptId)));
        return success(depts);
    }



}
