package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysLoginLog;
import com.aixbox.system.domain.vo.request.SysLoginLogPageReq;
import com.aixbox.system.domain.vo.request.SysLoginLogSaveReq;
import com.aixbox.system.domain.vo.request.SysLoginLogUpdateReq;
import com.aixbox.system.domain.vo.response.SysLoginLogResp;
import com.aixbox.system.service.SysLoginLogService;
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
 * 访问日志 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/loginLog")
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    /**
     * 新增访问日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysLoginLogSaveReq addReq) {
        Long sysLoginLogId = sysLoginLogService.addSysLoginLog(addReq);
        return success(sysLoginLogId);
    }

    /**
     * 修改访问日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysLoginLogUpdateReq updateReq) {
        Boolean result = sysLoginLogService.updateSysLoginLog(updateReq);
        return success(result);
    }

    /**
     * 删除访问日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysLoginLogService.deleteSysLoginLog(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取访问日志详细信息
     * @param id 数据id
     * @return 访问日志对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysLoginLogResp> getSysLoginLog(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getSysLoginLog(id);
        return success(BeanUtils.toBean(sysLoginLog, SysLoginLogResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 访问日志分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysLoginLogResp>> getSysLoginLogPage(@Valid SysLoginLogPageReq pageReq) {
        PageResult<SysLoginLog> pageResult = sysLoginLogService.getSysLoginLogPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysLoginLogResp.class));
    }



}
