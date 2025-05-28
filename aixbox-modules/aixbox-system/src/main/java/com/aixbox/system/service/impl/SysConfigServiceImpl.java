package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import com.aixbox.system.domain.vo.request.SysConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysConfigUpdateReq;
import com.aixbox.system.mapper.SysConfigMapper;
import com.aixbox.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 参数配置 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    /**
     * 新增参数配置
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysConfig(SysConfigSaveReq addReq) {
        SysConfig sysConfig = BeanUtils.toBean(addReq, SysConfig.class);
        sysConfigMapper.insert(sysConfig);
        return sysConfig.getId();
    }

    /**
     * 修改参数配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysConfig(SysConfigUpdateReq updateReq) {
        SysConfig sysConfig = MapstructUtils.convert(updateReq, SysConfig.class);
        return sysConfigMapper.updateById(sysConfig) > 0;
    }

    /**
     * 删除参数配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysConfig(List<Long> ids) {
        return sysConfigMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取参数配置详细数据
     * @param id 数据id
     * @return 参数配置对象
     */
    @Override
    public SysConfig getSysConfig(Long id) {
        return sysConfigMapper.selectById(id);
    }

    /**
     * 分页查询参数配置
     * @param pageReq 分页查询参数
     * @return 参数配置分页对象
     */
    @Override
    public PageResult<SysConfig> getSysConfigPage(SysConfigPageReq pageReq) {
        return sysConfigMapper.selectPage(pageReq);
    }
}




