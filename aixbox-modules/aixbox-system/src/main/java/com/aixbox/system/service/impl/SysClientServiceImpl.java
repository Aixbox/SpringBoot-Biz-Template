package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.client.SysClientPageReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientSaveReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientUpdateReqVO;
import com.aixbox.system.mapper.SysClientMapper;
import com.aixbox.system.service.SysClientService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 客户端 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysClientServiceImpl implements SysClientService {

    private final SysClientMapper sysClientMapper;

    /**
     * 新增客户端
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysClient(SysClientSaveReqVO addReqVO) {
        SysClient sysClient = BeanUtils.toBean(addReqVO, SysClient.class);
        sysClientMapper.insert(sysClient);
        return sysClient.getId();
    }

    /**
     * 修改客户端
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysClient(SysClientUpdateReqVO updateReqVO) {
        SysClient sysClient = MapstructUtils.convert(updateReqVO, SysClient.class);
        return sysClientMapper.updateById(sysClient) > 0;
    }

    /**
     * 删除客户端
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysClient(List<Long> ids) {
        return sysClientMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取客户端详细数据
     * @param id 数据id
     * @return 客户端对象
     */
    @Override
    public SysClient getSysClient(Long id) {
        return sysClientMapper.selectById(id);
    }

    /**
     * 分页查询客户端
     * @param pageReqVO 分页查询参数
     * @return 客户端分页对象
     */
    @Override
    public PageResult<SysClient> getSysClientPage(SysClientPageReqVO pageReqVO) {
        return sysClientMapper.selectPage(pageReqVO);
    }

    /**
     * 查询客户端管理
     */
    @Cacheable(cacheNames = CacheNames.SYS_CLIENT, key = "#clientId")
    @Override
    public SysClient queryByClientId(String clientId) {
        return sysClientMapper.selectOne(new LambdaQueryWrapper<SysClient>().eq(SysClient::getClientId, clientId));
    }

}




