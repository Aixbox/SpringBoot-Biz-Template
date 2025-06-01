package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysClientBo;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.client.SysClientPageReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientSaveReqVO;
import com.aixbox.system.domain.vo.request.client.SysClientUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysClientResp;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* 客户端 Service接口
*/
public interface SysClientService {

    /**
     * 新增客户端
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysClient(SysClientSaveReqVO addReqVO);

    /**
     * 修改客户端
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysClient(SysClientUpdateReqVO updateReqVO);

    /**
     * 删除客户端
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysClient(List<Long> ids);

    /**
     * 获取客户端详细数据
     * @param id 数据id
     * @return 客户端对象
     */
    SysClient getSysClient(Long id);

    /**
     * 分页查询客户端
     * @param pageReqVO 分页查询参数
     * @return 客户端分页对象
     */
    PageResult<SysClientResp> getSysClientPage(SysClientPageReqVO pageReqVO);


    SysClient queryByClientId(String clientId);

    /**
     * 修改状态
     */
    int updateClientStatus(String clientId, String status);

    /**
     * 查询客户端管理列表
     */
    List<SysClientResp> queryList(SysClientBo bo);
}
