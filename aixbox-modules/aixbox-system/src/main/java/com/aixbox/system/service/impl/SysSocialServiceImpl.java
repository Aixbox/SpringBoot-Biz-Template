package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.vo.request.SysSocialPageReqVO;
import com.aixbox.system.domain.vo.request.SysSocialSaveReqVO;
import com.aixbox.system.domain.vo.request.SysSocialUpdateReqVO;
import com.aixbox.system.mapper.SysSocialMapper;
import com.aixbox.system.service.SysSocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 社交用户 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysSocialServiceImpl implements SysSocialService {

    private final SysSocialMapper sysSocialMapper;

    /**
     * 新增社交用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysSocial(SysSocialSaveReqVO addReqVO) {
        SysSocial sysSocial = BeanUtils.toBean(addReqVO, SysSocial.class);
        sysSocialMapper.insert(sysSocial);
        return sysSocial.getId();
    }

    /**
     * 修改社交用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysSocial(SysSocialUpdateReqVO updateReqVO) {
        SysSocial sysSocial = MapstructUtils.convert(updateReqVO, SysSocial.class);
        return sysSocialMapper.updateById(sysSocial) > 0;
    }

    /**
     * 删除社交用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysSocial(List<Long> ids) {
        return sysSocialMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取社交用户详细数据
     * @param id 数据id
     * @return 社交用户对象
     */
    @Override
    public SysSocial getSysSocial(Long id) {
        return sysSocialMapper.selectById(id);
    }

    /**
     * 分页查询社交用户
     * @param pageReqVO 分页查询参数
     * @return 社交用户分页对象
     */
    @Override
    public PageResult<SysSocial> getSysSocialPage(SysSocialPageReqVO pageReqVO) {
        return sysSocialMapper.selectPage(pageReqVO);
    }
}




