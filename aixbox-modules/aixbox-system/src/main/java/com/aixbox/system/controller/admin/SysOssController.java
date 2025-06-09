package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;
import com.aixbox.system.domain.vo.request.SysOssSaveReq;
import com.aixbox.system.domain.vo.request.SysOssUpdateReq;
import com.aixbox.system.domain.vo.response.SysOssResp;
import com.aixbox.system.service.SysOssService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;

//todo 这里的 DELETE_DEMO_ERROR 需要在util添加上下文数据，全大写的函数名
import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_SYS_OSS_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_SYS_OSS_ERROR;

/**
 * OSS对象存储 Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/oss")
public class SysOssController {

    private final SysOssService sysOssService;

    /**
     * 新增OSS对象存储
     * @param addReq 新增参数
     * @return OSS对象存储 id
     */
    @SaCheckPermission("system:oss:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysOssSaveReq addReq) {
        Long sysOssId = sysOssService.addSysOss(addReq);
        return success(sysOssId);
    }

    /**
     * 修改OSS对象存储
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @SaCheckPermission("system:oss:update")
    @PutMapping("/update")
    public CommonResult<Void> update(@Valid @RequestBody SysOssUpdateReq updateReq) {
        Boolean result = sysOssService.updateSysOss(updateReq);
        return toAjax(result, UPDATE_SYS_OSS_ERROR);
    }

    /**
     * 删除OSS对象存储
     * @param ids 删除id数组
     * @return 是否成功
     */
    @SaCheckPermission("system:oss:remove")
    @DeleteMapping("/{ids}")
    public CommonResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysOssService.deleteSysOss(Arrays.asList(ids));
        return toAjax(result, DELETE_SYS_OSS_ERROR);
    }

    /**
     * 获取OSS对象存储详细信息
     * @param id OSS对象存储id
     * @return SysOssResp 对象
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/{id}")
    public CommonResult<SysOssResp> getSysOss(@PathVariable("id") Long id) {
        SysOss sysOss = sysOssService.getSysOss(id);
        return success(BeanUtils.toBean(sysOss, SysOssResp.class));
    }

    /**
     * 分页查询OSS对象存储
     * @param pageReq 分页参数
     * @return SysOssResp分页对象
     */
    @SaCheckPermission("system:oss:list")
    @GetMapping("/page")
    public CommonResult<PageResult<SysOssResp>> getSysOssPage(@Valid SysOssPageReq pageReq) {
        PageResult<SysOss> pageResult = sysOssService.getSysOssPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysOssResp.class));
    }

    /**
     * 导出OSS对象存储列表
     */
    @SaCheckPermission("system:oss:export")
    @PostMapping("/export")
    public void export(@Valid SysOssPageReq pageReq, HttpServletResponse response) {
        pageReq.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysOss> list = sysOssService.getSysOssPage(pageReq).getList();
        List<SysOssResp> respList = BeanUtils.toBean(list, SysOssResp.class);
        ExcelUtil.exportExcel(respList, "OSS对象存储", SysOssResp.class, response);
    }



}
