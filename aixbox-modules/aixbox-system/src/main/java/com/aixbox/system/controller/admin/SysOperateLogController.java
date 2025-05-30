package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysOperateLog;
import com.aixbox.system.domain.vo.request.SysOperateLogPageReq;
import com.aixbox.system.domain.vo.request.SysOperateLogSaveReq;
import com.aixbox.system.domain.vo.request.SysOperateLogUpdateReq;
import com.aixbox.system.domain.vo.response.SysOperateLogResp;
import com.aixbox.system.service.SysOperateLogService;
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
 * 操作日志 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/operateLog")
public class SysOperateLogController {

    private final SysOperateLogService sysOperateLogService;

    /**
     * 新增操作日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysOperateLogSaveReq addReq) {
        Long sysOperateLogId = sysOperateLogService.addSysOperateLog(addReq);
        return success(sysOperateLogId);
    }

    /**
     * 修改操作日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysOperateLogUpdateReq updateReq) {
        Boolean result = sysOperateLogService.updateSysOperateLog(updateReq);
        return success(result);
    }

    /**
     * 删除操作日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysOperateLogService.deleteSysOperateLog(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取操作日志详细信息
     * @param id 数据id
     * @return 操作日志对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysOperateLogResp> getSysOperateLog(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysOperateLog sysOperateLog = sysOperateLogService.getSysOperateLog(id);
        return success(BeanUtils.toBean(sysOperateLog, SysOperateLogResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 操作日志分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysOperateLogResp>> getSysOperateLogPage(@Valid SysOperateLogPageReq pageReq) {
        PageResult<SysOperateLog> pageResult = sysOperateLogService.getSysOperateLogPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysOperateLogResp.class));
    }



}
