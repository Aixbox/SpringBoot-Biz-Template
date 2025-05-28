package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.object.ObjectUtils;
import com.aixbox.common.redis.utils.CacheUtils;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysConfigBo;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import com.aixbox.system.domain.vo.request.SysConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysConfigUpdateReq;
import com.aixbox.system.domain.vo.response.SysConfigResp;
import com.aixbox.system.mapper.SysConfigMapper;
import com.aixbox.system.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static cn.dev33.satoken.SaManager.config;

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
    @CachePut(cacheNames = CacheNames.SYS_CONFIG, key = "#updateReq.configKey")
    @Override
    public Boolean updateSysConfig(SysConfigBo updateReq) {

        int row = 0;
        SysConfig config = BeanUtils.toBean(updateReq, SysConfig.class);
        if (config.getId() != null) {
            SysConfig temp = sysConfigMapper.selectById(config.getId());
            if (!StrUtils.equals(temp.getConfigKey(), config.getConfigKey())) {
                CacheUtils.evict(CacheNames.SYS_CONFIG, temp.getConfigKey());
            }
            row = sysConfigMapper.updateById(config);
        } else {
            CacheUtils.evict(CacheNames.SYS_CONFIG, config.getConfigKey());
            row = sysConfigMapper.update(config, new LambdaQueryWrapper<SysConfig>()
                    .eq(SysConfig::getConfigKey, config.getConfigKey()));
        }
        if (row > 0) {
            return true;
        }
        throw new ServiceException("操作失败");
    }

    /**
     * 删除参数配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysConfig(List<Long> ids) {
        for (Long configId : ids) {
            SysConfig config = sysConfigMapper.selectById(configId);
            if (StrUtils.equals(SystemConstants.YES, config.getConfigType())) {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            CacheUtils.evict(CacheNames.SYS_CONFIG, config.getConfigKey());
        }
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
        SysConfigBo sysConfigBo = BeanUtils.toBean(pageReq, SysConfigBo.class);
        LambdaQueryWrapper<SysConfig> lqw = buildQueryWrapper(sysConfigBo);
        return sysConfigMapper.selectPage(pageReq, lqw);
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfigResp> selectConfigList(SysConfigBo config) {
        LambdaQueryWrapper<SysConfig> lqw = buildQueryWrapper(config);
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(lqw);
        return BeanUtils.toBean(sysConfigs, SysConfigResp.class);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig retConfig = sysConfigMapper.selectOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getConfigKey, configKey));
        return ObjectUtils.notNullGetter(retConfig, SysConfig::getConfigValue, StrUtils.EMPTY);
    }

    @Override
    public boolean checkConfigKeyUnique(SysConfigBo config) {
        long configId = ObjectUtils.notNull(config.getId(), -1L);
        SysConfig info = sysConfigMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, config.getConfigKey()));
        if (ObjectUtil.isNotNull(info) && info.getId() != configId) {
            return false;
        }
        return true;
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        CacheUtils.clear(CacheNames.SYS_CONFIG);
    }


    /**
     * 查询参数配置列表
     *
     * @param bo 参数配置信息
     * @return 参数配置集合
     */
    private LambdaQueryWrapper<SysConfig> buildQueryWrapper(SysConfigBo bo) {
        LambdaQueryWrapper<SysConfig> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtils.isNotBlank(bo.getConfigName()), SysConfig::getConfigName, bo.getConfigName());
        lqw.eq(StrUtils.isNotBlank(bo.getConfigType()), SysConfig::getConfigType, bo.getConfigType());
        lqw.like(StrUtils.isNotBlank(bo.getConfigKey()), SysConfig::getConfigKey, bo.getConfigKey());
        lqw.orderByAsc(SysConfig::getId);
        return lqw;
    }


}




