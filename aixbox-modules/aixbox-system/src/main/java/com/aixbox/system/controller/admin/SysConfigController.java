package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import com.aixbox.system.domain.vo.request.SysConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysConfigUpdateReq;
import com.aixbox.system.domain.vo.response.SysConfigResp;
import com.aixbox.system.service.SysConfigService;
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
 * 参数配置 Controller
 */
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
    public CommonResult<Long> add(@Valid @RequestBody SysConfigSaveReq addReq) {
        Long sysConfigId = sysConfigService.addSysConfig(addReq);
        return success(sysConfigId);
    }

    /**
     * 修改参数配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysConfigUpdateReq updateReq) {
        Boolean result = sysConfigService.updateSysConfig(updateReq);
        return success(result);
    }

    /**
     * 删除参数配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysConfigService.deleteSysConfig(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取参数配置详细信息
     * @param id 数据id
     * @return 参数配置对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysConfigResp> getSysConfig(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysConfig sysConfig = sysConfigService.getSysConfig(id);
        return success(BeanUtils.toBean(sysConfig, SysConfigResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 参数配置分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysConfigResp>> getSysConfigPage(@Valid SysConfigPageReq pageReq) {
        PageResult<SysConfig> pageResult = sysConfigService.getSysConfigPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysConfigResp.class));
    }



}
