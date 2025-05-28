package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.bo.SysPostBo;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.post.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostSaveReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysPostResp;
import com.aixbox.system.service.SysPostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.system.constant.ErrorCodeConstants.POST_CODE_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.POST_NAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_POST_CODE_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_POST_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_POST_NAME_EXIST;

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
    @SaCheckPermission("system:post:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysPostSaveReqVO addReqVO) {
        SysPostBo postBo = BeanUtils.toBean(addReqVO, SysPostBo.class);
        if (!sysPostService.checkPostNameUnique(postBo)) {
            return error(POST_NAME_EXIST, addReqVO.getPostName());
        } else if (!sysPostService.checkPostCodeUnique(postBo)) {
            return error(POST_CODE_EXIST, addReqVO.getPostCode());
        }
        Long sysPostId = sysPostService.addSysPost(addReqVO);
        return success(sysPostId);
    }

    /**
     * 修改岗位
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @SaCheckPermission("system:post:edit")
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysPostUpdateReqVO updateReqVO) {
        SysPostBo postBo = BeanUtils.toBean(updateReqVO, SysPostBo.class);
        if (!sysPostService.checkPostNameUnique(postBo)) {
            return error(UPDATE_POST_NAME_EXIST, postBo.getPostName());
        } else if (!sysPostService.checkPostCodeUnique(postBo)) {
            return error(UPDATE_POST_CODE_EXIST, postBo.getPostName());
        } else if (SystemConstants.DISABLE.equals(postBo.getStatus())
                && sysPostService.countUserPostById(postBo.getId()) > 0) {
            return error(UPDATE_POST_ERROR);
        }
        Boolean result = sysPostService.updateSysPost(updateReqVO);
        return success(result);
    }

    /**
     * 删除岗位
     * @param ids 删除id数组
     * @return 是否成功
     */
    @SaCheckPermission("system:post:remove")
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@PathVariable Long[] ids) {
        Boolean result = sysPostService.deleteSysPost(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取岗位详细信息
     * @param id 数据id
     * @return demo对象
     */
    @SaCheckPermission("system:post:query")
    @GetMapping("/{id}")
    public CommonResult<SysPostResp> getSysPost(@PathVariable("id") Long id) {
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

    /**
     * 导出岗位列表
     */
    @SaCheckPermission("system:post:export")
    @PostMapping("/export")
    public void export(SysPostBo post, HttpServletResponse response) {
        List<SysPostResp> list = sysPostService.selectPostList(post);
        ExcelUtil.exportExcel(list, "岗位数据", SysPostResp.class, response);
    }

    /**
     * 获取岗位选择框列表
     *
     * @param postIds 岗位ID串
     * @param deptId  部门id
     */
    @SaCheckPermission("system:post:query")
    @GetMapping("/optionselect")
    public CommonResult<List<SysPostResp>> optionselect(@RequestParam(required = false) Long[] postIds,
                                          @RequestParam(required = false) Long deptId) {
        List<SysPostResp> list = new ArrayList<>();
        if (ObjectUtil.isNotNull(deptId)) {
            SysPostBo post = new SysPostBo();
            post.setDeptId(deptId);
            list = sysPostService.selectPostList(post);
        } else if (postIds != null) {
            list = sysPostService.selectPostByIds(List.of(postIds));
        }
        return success(list);
    }





}
