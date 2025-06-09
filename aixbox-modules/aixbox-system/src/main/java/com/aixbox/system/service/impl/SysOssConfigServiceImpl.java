package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.aixbox.system.domain.entity.SysOssConfig;
import com.aixbox.system.domain.vo.request.SysOssConfigPageReq;
import com.aixbox.system.domain.vo.request.SysOssConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysOssConfigUpdateReq;
import com.aixbox.system.mapper.SysOssConfigMapper;
import com.aixbox.system.service.SysOssConfigService;

import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.SYS_OSS_CONFIG_NOT_EXISTS;

/**
* 对象存储配置Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysOssConfigServiceImpl implements SysOssConfigService {

    private final SysOssConfigMapper sysOssConfigMapper;

    /**
     * 新增对象存储配置
     * @param addReq 新增参数
     * @return 对象存储配置id
     */
    @Override
    public Long addSysOssConfig(SysOssConfigSaveReq addReq) {
        SysOssConfig  sysOssConfig = BeanUtils.toBean(addReq, SysOssConfig.class);
        validEntityBeforeSave(sysOssConfig);
        sysOssConfigMapper.insert(sysOssConfig);
        return sysOssConfig.getId();
    }

    /**
     * 修改对象存储配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysOssConfig(SysOssConfigUpdateReq updateReq) {
        SysOssConfig sysOssConfig = BeanUtils.toBean(updateReq, SysOssConfig.class);
        validEntityBeforeSave(sysOssConfig);
        return sysOssConfigMapper.updateById(sysOssConfig) > 0;
    }

    /**
     * 删除对象存储配置
     * @param ids 对象存储配置id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysOssConfig(List<Long> ids) {
        validateSysOssConfigExists(ids);
        return sysOssConfigMapper.deleteByIds(ids) > 0;
    }

    /**
     * 验证对象存储配置是否存在
     * @param ids 对象存储配置id数组
     */
    private void validateSysOssConfigExists(List<Long> ids) {
        Long count = sysOssConfigMapper.countByIds(ids);
        if (count != ids.size()) {
            throw exception(SYS_OSS_CONFIG_NOT_EXISTS);
        }
    }

    /**
     * 获取对象存储配置详细数据
     * @param id 对象存储配置id
     * @return 对象存储配置对象
     */
    @Override
    public SysOssConfig getSysOssConfig(Long id) {
        return sysOssConfigMapper.selectById(id);
    }

    /**
     * 分页查询对象存储配置
     * @param pageReq 分页查询参数
     * @return 对象存储配置分页对象
     */
    @Override
    public PageResult<SysOssConfig> getSysOssConfigPage(SysOssConfigPageReq pageReq) {
        return sysOssConfigMapper.selectPage(pageReq);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOssConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }
}




