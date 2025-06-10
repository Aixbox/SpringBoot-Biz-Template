package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.vo.response.SysOssUploadResp;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;
import com.aixbox.system.domain.vo.request.SysOssSaveReq;
import com.aixbox.system.domain.vo.request.SysOssUpdateReq;
import com.aixbox.system.domain.vo.response.SysOssResp;
import com.aixbox.system.service.SysOssService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;

import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_SYS_OSS_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_SYS_OSS_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPLOAD_FILE_EMPTY;

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
     * 下载OSS对象
     *
     * @param ossId OSS对象ID
     */
    @SaCheckPermission("system:oss:download")
    @GetMapping("/download/{ossId}")
    public void download(@PathVariable Long ossId, HttpServletResponse response) throws IOException {
        sysOssService.download(ossId, response);
    }

    /**
     * 上传OSS对象存储
     *
     * @param file 文件
     */
    @SaCheckPermission("system:oss:upload")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<SysOssUploadResp> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw exception(UPLOAD_FILE_EMPTY);
        }
        SysOssResp oss = sysOssService.upload(file);
        SysOssUploadResp uploadVo = new SysOssUploadResp();
        uploadVo.setUrl(oss.getUrl());
        uploadVo.setFileName(oss.getOriginalName());
        uploadVo.setOssId(oss.getId().toString());
        return success(uploadVo);
    }


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
