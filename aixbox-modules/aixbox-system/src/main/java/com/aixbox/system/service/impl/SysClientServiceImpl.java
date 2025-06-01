package com.aixbox.system.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysClientBo;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.client.SysClientPageReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientSaveReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysClientResp;
import com.aixbox.system.mapper.SysClientMapper;
import com.aixbox.system.service.SysClientService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
        SysClient add = BeanUtils.toBean(addReqVO, SysClient.class);
        validEntityBeforeSave(add);
        add.setGrantType(String.join(",", addReqVO.getGrantTypeList()));
        // 生成clientid
        String clientKey = addReqVO.getClientKey();
        String clientSecret = addReqVO.getClientSecret();
        add.setClientId(SecureUtil.md5(clientKey + clientSecret));
        sysClientMapper.insert(add);
        return add.getId();




    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysClient entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 修改客户端
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysClient(SysClientUpdateReqVO updateReqVO) {
        SysClient sysClient = MapstructUtils.convert(updateReqVO, SysClient.class);
        validEntityBeforeSave(sysClient);
        sysClient.setGrantType(String.join(",", updateReqVO.getGrantTypeList()));
        return sysClientMapper.updateById(sysClient) > 0;
    }

    /**
     * 删除客户端
     * @param ids 删除id数组
     * @return 是否成功
     */
    @CacheEvict(cacheNames = CacheNames.SYS_CLIENT, allEntries = true)
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
    public PageResult<SysClientResp> getSysClientPage(SysClientPageReqVO pageReqVO) {
        SysClientBo bo = BeanUtils.toBean(pageReqVO, SysClientBo.class);
        LambdaQueryWrapper<SysClient> lqw = buildQueryWrapper(bo);
        PageResult<SysClient> result = sysClientMapper.selectPage(pageReqVO, lqw);
        PageResult<SysClientResp> bean = BeanUtils.toBean(result, SysClientResp.class);
        bean.getList().forEach(r -> r.setGrantTypeList(List.of(r.getGrantType().split(","))));
        return bean;
    }

    private LambdaQueryWrapper<SysClient> buildQueryWrapper(SysClientBo bo) {
        LambdaQueryWrapper<SysClient> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtils.isNotBlank(bo.getClientId()), SysClient::getClientId, bo.getClientId());
        lqw.eq(StrUtils.isNotBlank(bo.getClientKey()), SysClient::getClientKey, bo.getClientKey());
        lqw.eq(StrUtils.isNotBlank(bo.getClientSecret()), SysClient::getClientSecret, bo.getClientSecret());
        lqw.eq(StrUtils.isNotBlank(bo.getStatus()), SysClient::getStatus, bo.getStatus());
        lqw.orderByAsc(SysClient::getId);
        return lqw;
    }

    /**
     * 查询客户端管理
     */
    @Cacheable(cacheNames = CacheNames.SYS_CLIENT, key = "#clientId")
    @Override
    public SysClient queryByClientId(String clientId) {
        return sysClientMapper.selectOne(new LambdaQueryWrapper<SysClient>().eq(SysClient::getClientId, clientId));
    }

    /**
     * 修改状态
     */
    @CacheEvict(cacheNames = CacheNames.SYS_CLIENT, key = "#clientId")
    @Override
    public int updateClientStatus(String clientId, String status) {
        return sysClientMapper.update(null,
                new LambdaUpdateWrapper<SysClient>()
                        .set(SysClient::getStatus, status)
                        .eq(SysClient::getClientId, clientId));
    }

    @Override
    public List<SysClientResp> queryList(SysClientBo bo) {
        LambdaQueryWrapper<SysClient> lqw = buildQueryWrapper(bo);
        List<SysClient> sysClients = sysClientMapper.selectList(lqw);
        return BeanUtils.toBean(sysClients, SysClientResp.class);
    }

}




