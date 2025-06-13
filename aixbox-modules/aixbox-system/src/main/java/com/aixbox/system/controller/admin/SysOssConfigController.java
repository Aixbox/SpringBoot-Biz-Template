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
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aixbox.system.domain.entity.SysOssConfig;
import com.aixbox.system.domain.vo.request.SysOssConfigPageReq;
import com.aixbox.system.domain.vo.request.SysOssConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysOssConfigUpdateReq;
import com.aixbox.system.domain.vo.response.SysOssConfigResp;
import com.aixbox.system.service.SysOssConfigService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;

import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_SYS_OSS_CONFIG_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_SYS_OSS_CONFIG_ERROR;

/**
 * 对象存储配置 Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/oss/config")
public class SysOssConfigController {

    private final SysOssConfigService sysOssConfigService;

    /**
     * 新增对象存储配置
     * @param addReq 新增参数
     * @return 对象存储配置 id
     */
    @SaCheckPermission("system:oss-config:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysOssConfigSaveReq addReq) {
        Long sysOssConfigId = sysOssConfigService.addSysOssConfig(addReq);
        return success(sysOssConfigId);
    }

    /**
     * 修改对象存储配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @SaCheckPermission("system:oss-config:update")
    @PutMapping("/update")
    public CommonResult<Void> update(@Valid @RequestBody SysOssConfigUpdateReq updateReq) {
        Boolean result = sysOssConfigService.updateSysOssConfig(updateReq);
        return toAjax(result, UPDATE_SYS_OSS_CONFIG_ERROR);
    }

    /**
     * 删除对象存储配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    @SaCheckPermission("system:oss-config:remove")
    @DeleteMapping("/{ids}")
    public CommonResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysOssConfigService.deleteSysOssConfig(Arrays.asList(ids));
        return toAjax(result, DELETE_SYS_OSS_CONFIG_ERROR);
    }

    /**
     * 获取对象存储配置详细信息
     * @param id 对象存储配置id
     * @return SysOssConfigResp 对象
     */
    @SaCheckPermission("system:oss-config:query")
    @GetMapping("/{id}")
    public CommonResult<SysOssConfigResp> getSysOssConfig(@PathVariable("id") Long id) {
        SysOssConfig sysOssConfig = sysOssConfigService.getSysOssConfig(id);
        return success(BeanUtils.toBean(sysOssConfig, SysOssConfigResp.class));
    }

    /**
     * 分页查询对象存储配置
     * @param pageReq 分页参数
     * @return SysOssConfigResp分页对象
     */
    @SaCheckPermission("system:oss-config:list")
    @GetMapping("/page")
    public CommonResult<PageResult<SysOssConfigResp>> getSysOssConfigPage(@Valid SysOssConfigPageReq pageReq) {
        PageResult<SysOssConfig> pageResult = sysOssConfigService.getSysOssConfigPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysOssConfigResp.class));
    }

    /**
     * 导出对象存储配置列表
     */
    @SaCheckPermission("system:oss-config:export")
    @PostMapping("/export")
    public void export(@Valid SysOssConfigPageReq pageReq, HttpServletResponse response) {
        pageReq.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysOssConfig> list = sysOssConfigService.getSysOssConfigPage(pageReq).getList();
        List<SysOssConfigResp> respList = BeanUtils.toBean(list, SysOssConfigResp.class);
        ExcelUtil.exportExcel(respList, "对象存储配置", SysOssConfigResp.class, response);
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:ossConfig:edit")
    @PutMapping("/changeStatus")
    public CommonResult<Void> changeStatus(@RequestBody SysOssConfigUpdateReq bo) {
        return toAjax(sysOssConfigService.updateOssConfigStatus(bo), UPDATE_SYS_OSS_CONFIG_ERROR);
    }



}
