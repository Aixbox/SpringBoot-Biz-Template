package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
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
 * 部门 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 新增部门
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysDeptSaveReqVO addReqVO) {
        Long sysDeptId = sysDeptService.addSysDept(addReqVO);
        return success(sysDeptId);
    }

    /**
     * 修改部门
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysDeptUpdateReqVO updateReqVO) {
        Boolean result = sysDeptService.updateSysDept(updateReqVO);
        return success(result);
    }

    /**
     * 删除部门
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysDeptService.deleteSysDept(Arrays.asList(ids));
        return success(result);
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
