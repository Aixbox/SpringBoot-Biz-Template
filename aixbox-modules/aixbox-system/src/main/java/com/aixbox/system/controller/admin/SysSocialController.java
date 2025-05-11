package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.vo.request.SysSocialPageReqVO;
import com.aixbox.system.domain.vo.request.SysSocialSaveReqVO;
import com.aixbox.system.domain.vo.request.SysSocialUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysSocialRespVO;
import com.aixbox.system.service.SysSocialService;
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
 * 社交用户 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/social")
public class SysSocialController {

    private final SysSocialService sysSocialService;

    /**
     * 新增社交用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysSocialSaveReqVO addReqVO) {
        Long sysSocialId = sysSocialService.addSysSocial(addReqVO);
        return success(sysSocialId);
    }

    /**
     * 修改社交用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysSocialUpdateReqVO updateReqVO) {
        Boolean result = sysSocialService.updateSysSocial(updateReqVO);
        return success(result);
    }

    /**
     * 删除社交用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysSocialService.deleteSysSocial(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取社交用户详细信息
     * @param id 数据id
     * @return 社交用户对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysSocialRespVO> getSysSocial(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysSocial sysSocial = sysSocialService.getSysSocial(id);
        return success(BeanUtils.toBean(sysSocial, SysSocialRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return 社交用户分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysSocialRespVO>> getSysSocialPage(@Valid SysSocialPageReqVO pageReqVO) {
        PageResult<SysSocial> pageResult = sysSocialService.getSysSocialPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysSocialRespVO.class));
    }



}
