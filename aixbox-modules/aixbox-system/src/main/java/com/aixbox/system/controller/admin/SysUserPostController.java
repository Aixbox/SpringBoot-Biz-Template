package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.vo.request.SysUserPostPageReq;
import com.aixbox.system.domain.vo.request.SysUserPostSaveReq;
import com.aixbox.system.domain.vo.request.SysUserPostUpdateReq;
import com.aixbox.system.domain.vo.response.SysUserPostResp;
import com.aixbox.system.service.SysUserPostService;
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
 * 用户岗位关联 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userPost")
public class SysUserPostController {

    private final SysUserPostService sysUserPostService;

    /**
     * 新增用户岗位关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysUserPostSaveReq addReq) {
        Long sysUserPostId = sysUserPostService.addSysUserPost(addReq);
        return success(sysUserPostId);
    }

    /**
     * 修改用户岗位关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysUserPostUpdateReq updateReq) {
        Boolean result = sysUserPostService.updateSysUserPost(updateReq);
        return success(result);
    }

    /**
     * 删除用户岗位关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysUserPostService.deleteSysUserPost(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取用户岗位关联详细信息
     * @param id 数据id
     * @return 用户岗位关联对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysUserPostResp> getSysUserPost(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysUserPost sysUserPost = sysUserPostService.getSysUserPost(id);
        return success(BeanUtils.toBean(sysUserPost, SysUserPostResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 用户岗位关联分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysUserPostResp>> getSysUserPostPage(@Valid SysUserPostPageReq pageReq) {
        PageResult<SysUserPost> pageResult = sysUserPostService.getSysUserPostPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysUserPostResp.class));
    }



}
