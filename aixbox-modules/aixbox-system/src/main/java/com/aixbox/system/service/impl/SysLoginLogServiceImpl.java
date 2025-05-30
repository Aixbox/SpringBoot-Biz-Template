package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysLoginLog;
import com.aixbox.system.domain.vo.request.SysLoginLogPageReq;
import com.aixbox.system.domain.vo.request.SysLoginLogSaveReq;
import com.aixbox.system.domain.vo.request.SysLoginLogUpdateReq;
import com.aixbox.system.mapper.SysLoginLogMapper;
import com.aixbox.system.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 访问日志 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    private final SysLoginLogMapper sysLoginLogMapper;

    /**
     * 新增访问日志
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysLoginLog(SysLoginLogSaveReq addReq) {
        SysLoginLog sysLoginLog = BeanUtils.toBean(addReq, SysLoginLog.class);
        sysLoginLogMapper.insert(sysLoginLog);
        return sysLoginLog.getId();
    }

    /**
     * 修改访问日志
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysLoginLog(SysLoginLogUpdateReq updateReq) {
        SysLoginLog sysLoginLog = MapstructUtils.convert(updateReq, SysLoginLog.class);
        return sysLoginLogMapper.updateById(sysLoginLog) > 0;
    }

    /**
     * 删除访问日志
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysLoginLog(List<Long> ids) {
        return sysLoginLogMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取访问日志详细数据
     * @param id 数据id
     * @return 访问日志对象
     */
    @Override
    public SysLoginLog getSysLoginLog(Long id) {
        return sysLoginLogMapper.selectById(id);
    }

    /**
     * 分页查询访问日志
     * @param pageReq 分页查询参数
     * @return 访问日志分页对象
     */
    @Override
    public PageResult<SysLoginLog> getSysLoginLogPage(SysLoginLogPageReq pageReq) {
        return sysLoginLogMapper.selectPage(pageReq);
    }
}




