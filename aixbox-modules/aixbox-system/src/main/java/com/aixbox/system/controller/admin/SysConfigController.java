package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.bo.SysConfigBo;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import com.aixbox.system.domain.vo.request.SysConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysConfigUpdateReq;
import com.aixbox.system.domain.vo.response.SysConfigResp;
import com.aixbox.system.service.SysConfigService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.system.constant.ErrorCodeConstants.CONFIG_KEY_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_CONFIG_KEY_EXIST;

/**
 * 参数配置 Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/config")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    /**
     * 新增参数配置
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@RequestBody SysConfigSaveReq addReq) {
        SysConfigBo config = BeanUtils.toBean(addReq, SysConfigBo.class);
        if (!sysConfigService.checkConfigKeyUnique(config)) {
            return error(CONFIG_KEY_EXIST, config.getConfigName());
        }
        Long sysConfigId = sysConfigService.addSysConfig(addReq);
        return success(sysConfigId);
    }

    /**
     * 修改参数配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@RequestBody SysConfigUpdateReq updateReq) {
        SysConfigBo configBo = BeanUtils.toBean(updateReq, SysConfigBo.class);
        if (!sysConfigService.checkConfigKeyUnique(configBo)) {
            return error(UPDATE_CONFIG_KEY_EXIST, configBo.getConfigName());
        }
        Boolean result = sysConfigService.updateSysConfig(configBo);
        return success(result);
    }

    /**
     * 删除参数配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@PathVariable Long[] ids) {
        Boolean result = sysConfigService.deleteSysConfig(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取参数配置详细信息
     * @param id 数据id
     * @return 参数配置对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysConfigResp> getSysConfig(@PathVariable("id") Long id) {
        SysConfig sysConfig = sysConfigService.getSysConfig(id);
        return success(BeanUtils.toBean(sysConfig, SysConfigResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 参数配置分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysConfigResp>> getSysConfigPage(SysConfigPageReq pageReq) {
        PageResult<SysConfig> pageResult = sysConfigService.getSysConfigPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysConfigResp.class));
    }


    /**
     * 导出参数配置列表
     */
    @SaCheckPermission("system:config:export")
    @PostMapping("/export")
    public void export(SysConfigBo config, HttpServletResponse response) {
        List<SysConfigResp> list = sysConfigService.selectConfigList(config);
        ExcelUtil.exportExcel(list, "参数数据", SysConfigResp.class, response);
    }

    /**
     * 根据参数键名查询参数值
     *
     * @param configKey 参数Key
     */
    @GetMapping(value = "/configKey/{configKey}")
    public CommonResult<String> getConfigKey(@PathVariable String configKey) {
        return success(sysConfigService.selectConfigByKey(configKey));
    }


    /**
     * 根据参数键名修改参数配置
     */
    @SaCheckPermission("system:config:edit")
    @PutMapping("/updateByKey")
    public CommonResult<Void> updateByKey(@RequestBody SysConfigBo config) {
        sysConfigService.updateSysConfig(config);
        return success();
    }

    /**
     * 刷新参数缓存
     */
    @SaCheckPermission("system:config:remove")
    @DeleteMapping("/refreshCache")
    public CommonResult<Void> refreshCache() {
        sysConfigService.resetConfigCache();
        return success();
    }


}
