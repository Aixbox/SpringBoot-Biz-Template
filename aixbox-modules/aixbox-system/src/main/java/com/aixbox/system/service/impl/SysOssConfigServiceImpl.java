package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.CacheNames;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.object.ObjectUtils;
import com.aixbox.common.oss.constant.OssConstant;
import com.aixbox.common.redis.utils.CacheUtils;
import com.aixbox.common.redis.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.aixbox.system.domain.entity.SysOssConfig;
import com.aixbox.system.domain.vo.request.SysOssConfigPageReq;
import com.aixbox.system.domain.vo.request.SysOssConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysOssConfigUpdateReq;
import com.aixbox.system.mapper.SysOssConfigMapper;
import com.aixbox.system.service.SysOssConfigService;
import org.springframework.transaction.annotation.Transactional;

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
        boolean flag = sysOssConfigMapper.insert(sysOssConfig) > 0;
        if (flag) {
            // 从数据库查询完整的数据做缓存
            sysOssConfig = sysOssConfigMapper.selectById(sysOssConfig.getId());
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
        }
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
        boolean flag = sysOssConfigMapper.updateById(sysOssConfig) > 0;
        if (flag) {
            // 从数据库查询完整的数据做缓存
            sysOssConfig = sysOssConfigMapper.selectById(sysOssConfig.getId());
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey(), JsonUtils.toJsonString(sysOssConfig));
        }
        return flag;
    }

    /**
     * 删除对象存储配置
     * @param ids 对象存储配置id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysOssConfig(List<Long> ids) {
        if (CollUtil.containsAny(ids, OssConstant.SYSTEM_DATA_IDS)) {
            throw new ServiceException("系统内置, 不可删除!");
        }
        validateSysOssConfigExists(ids);
        List<SysOssConfig> list = CollUtil.newArrayList();
        for (Long configId : ids) {
            SysOssConfig config = sysOssConfigMapper.selectById(configId);
            list.add(config);
        }
        boolean flag = sysOssConfigMapper.deleteByIds(ids) > 0;
        if (flag) {
            list.forEach(sysOssConfig ->
                    CacheUtils.evict(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey()));
        }
        return flag;
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
     * 启用禁用状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOssConfigStatus(SysOssConfigUpdateReq bo) {
        SysOssConfig sysOssConfig = BeanUtils.toBean(bo, SysOssConfig.class);
        int row = sysOssConfigMapper.update(null, new LambdaUpdateWrapper<SysOssConfig>()
                .set(SysOssConfig::getStatus, "1"));
        row += sysOssConfigMapper.updateById(sysOssConfig);
        if (row > 0) {
            RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, sysOssConfig.getConfigKey());
        }
        return row;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOssConfig entity){
        if (StrUtils.isNotEmpty(entity.getConfigKey())
                && !checkConfigKeyUnique(entity)) {
            throw new ServiceException("操作配置'" + entity.getConfigKey() + "'失败, 配置key已存在!");
        }
    }

    /**
     * 判断configKey是否唯一
     */
    private boolean checkConfigKeyUnique(SysOssConfig sysOssConfig) {
        long ossConfigId = ObjectUtils.notNull(sysOssConfig.getId(), -1L);
        SysOssConfig info = sysOssConfigMapper.selectOne(new LambdaQueryWrapper<SysOssConfig>()
                .select(SysOssConfig::getId, SysOssConfig::getConfigKey)
                .eq(SysOssConfig::getConfigKey, sysOssConfig.getConfigKey()));
        if (ObjectUtil.isNotNull(info) && info.getId() != ossConfigId) {
            return false;
        }
        return true;
    }
}




