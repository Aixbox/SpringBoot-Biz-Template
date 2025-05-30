package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysLoginLog;
import com.aixbox.system.domain.vo.request.SysLoginLogPageReq;
import com.aixbox.system.domain.vo.request.SysLoginLogSaveReq;
import com.aixbox.system.domain.vo.request.SysLoginLogUpdateReq;

import java.util.List;

/**
* 访问日志 Service接口
*/
public interface SysLoginLogService {

    /**
     * 新增访问日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysLoginLog(SysLoginLogSaveReq addReq);

    /**
     * 修改访问日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysLoginLog(SysLoginLogUpdateReq updateReq);

    /**
     * 删除访问日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysLoginLog(List<Long> ids);

    /**
     * 获取访问日志详细数据
     * @param id 数据id
     * @return 访问日志对象
     */
    SysLoginLog getSysLoginLog(Long id);

    /**
     * 分页查询访问日志
     * @param pageReq 分页查询参数
     * @return 访问日志分页对象
     */
    PageResult<SysLoginLog> getSysLoginLogPage(SysLoginLogPageReq pageReq);
}
