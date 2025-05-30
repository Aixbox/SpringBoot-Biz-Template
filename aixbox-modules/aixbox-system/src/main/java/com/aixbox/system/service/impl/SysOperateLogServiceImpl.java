package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysOperateLog;
import com.aixbox.system.domain.vo.request.SysOperateLogPageReq;
import com.aixbox.system.domain.vo.request.SysOperateLogSaveReq;
import com.aixbox.system.domain.vo.request.SysOperateLogUpdateReq;
import com.aixbox.system.mapper.SysOperateLogMapper;
import com.aixbox.system.service.SysOperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 操作日志 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysOperateLogServiceImpl implements SysOperateLogService {

    private final SysOperateLogMapper sysOperateLogMapper;

    /**
     * 新增操作日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysOperateLog(SysOperateLogSaveReq addReq) {
        SysOperateLog sysOperateLog = BeanUtils.toBean(addReq, SysOperateLog.class);
        sysOperateLogMapper.insert(sysOperateLog);
        return sysOperateLog.getId();
    }

    /**
     * 修改操作日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysOperateLog(SysOperateLogUpdateReq updateReq) {
        SysOperateLog sysOperateLog = MapstructUtils.convert(updateReq, SysOperateLog.class);
        return sysOperateLogMapper.updateById(sysOperateLog) > 0;
    }

    /**
     * 删除操作日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysOperateLog(List<Long> ids) {
        return sysOperateLogMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取操作日志详细数据
     * @param id 数据id
     * @return 操作日志对象
     */
    @Override
    public SysOperateLog getSysOperateLog(Long id) {
        return sysOperateLogMapper.selectById(id);
    }

    /**
     * 分页查询操作日志
     * @param pageReq 分页查询参数
     * @return 操作日志分页对象
     */
    @Override
    public PageResult<SysOperateLog> getSysOperateLogPage(SysOperateLogPageReq pageReq) {
        return sysOperateLogMapper.selectPage(pageReq);
    }
}




