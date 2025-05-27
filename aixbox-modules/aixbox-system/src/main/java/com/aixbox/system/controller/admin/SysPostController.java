package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.post.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostSaveReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysPostResp;
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

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * 岗位 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/post")
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 新增岗位
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysPostSaveReqVO addReqVO) {
        Long sysPostId = sysPostService.addSysPost(addReqVO);
        return success(sysPostId);
    }

    /**
     * 修改岗位
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysPostUpdateReqVO updateReqVO) {
        Boolean result = sysPostService.updateSysPost(updateReqVO);
        return success(result);
    }

    /**
     * 删除岗位
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysPostService.deleteSysPost(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取岗位详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysPostResp> getSysPost(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysPost sysPost = sysPostService.getSysPost(id);
        return success(BeanUtils.toBean(sysPost, SysPostResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysPostResp>> getSysPostPage(@Valid SysPostPageReqVO pageReqVO) {
        PageResult<SysPost> pageResult = sysPostService.getSysPostPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysPostResp.class));
    }



}
