package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysOperateLog;
import com.aixbox.system.domain.vo.request.SysOperateLogPageReq;
import com.aixbox.system.domain.vo.request.SysOperateLogSaveReq;
import com.aixbox.system.domain.vo.request.SysOperateLogUpdateReq;

import java.util.List;

/**
* 操作日志 Service接口
*/
public interface SysOperateLogService {

    /**
     * 新增操作日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysOperateLog(SysOperateLogSaveReq addReq);

    /**
     * 修改操作日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysOperateLog(SysOperateLogUpdateReq updateReq);

    /**
     * 删除操作日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysOperateLog(List<Long> ids);

    /**
     * 获取操作日志详细数据
     * @param id 数据id
     * @return 操作日志对象
     */
    SysOperateLog getSysOperateLog(Long id);

    /**
     * 分页查询操作日志
     * @param pageReq 分页查询参数
     * @return 操作日志分页对象
     */
    PageResult<SysOperateLog> getSysOperateLogPage(SysOperateLogPageReq pageReq);
}
