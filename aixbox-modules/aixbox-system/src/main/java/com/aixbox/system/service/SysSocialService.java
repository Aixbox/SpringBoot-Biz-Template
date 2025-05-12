package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.vo.request.SysSocialPageReqVO;
import com.aixbox.system.domain.vo.request.SysSocialSaveReqVO;
import com.aixbox.system.domain.vo.request.SysSocialUpdateReqVO;

import java.util.List;

/**
* 社交用户 Service接口
*/
public interface SysSocialService {

    /**
     * 新增社交用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysSocial(SysSocialSaveReqVO addReqVO);

    /**
     * 修改社交用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysSocial(SysSocialUpdateReqVO updateReqVO);

    /**
     * 删除社交用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysSocial(List<Long> ids);

    /**
     * 获取社交用户详细数据
     * @param id 数据id
     * @return 社交用户对象
     */
    SysSocial getSysSocial(Long id);

    /**
     * 分页查询社交用户
     * @param pageReqVO 分页查询参数
     * @return 社交用户分页对象
     */
    PageResult<SysSocial> getSysSocialPage(SysSocialPageReqVO pageReqVO);

    List<SysSocial> selectByAuthId(String authId);

    List<SysSocial> queryList(SysSocial params);

    Boolean insertByBo(SysSocial bo);

    Boolean updateByBo(SysSocial bo);
}
