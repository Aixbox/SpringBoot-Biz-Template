package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.SysDictTypePageReq;
import com.aixbox.system.domain.vo.request.SysDictTypeSaveReq;
import com.aixbox.system.domain.vo.request.SysDictTypeUpdateReq;
import com.aixbox.system.mapper.SysDictTypeMapper;
import com.aixbox.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 字典类型 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;

    /**
     * 新增字典类型
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysDictType(SysDictTypeSaveReq addReq) {
        SysDictType sysDictType = BeanUtils.toBean(addReq, SysDictType.class);
        sysDictTypeMapper.insert(sysDictType);
        return sysDictType.getId();
    }

    /**
     * 修改字典类型
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysDictType(SysDictTypeUpdateReq updateReq) {
        SysDictType sysDictType = MapstructUtils.convert(updateReq, SysDictType.class);
        return sysDictTypeMapper.updateById(sysDictType) > 0;
    }

    /**
     * 删除字典类型
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysDictType(List<Long> ids) {
        return sysDictTypeMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取字典类型详细数据
     * @param id 数据id
     * @return 字典类型对象
     */
    @Override
    public SysDictType getSysDictType(Long id) {
        return sysDictTypeMapper.selectById(id);
    }

    /**
     * 分页查询字典类型
     * @param pageReq 分页查询参数
     * @return 字典类型分页对象
     */
    @Override
    public PageResult<SysDictType> getSysDictTypePage(SysDictTypePageReq pageReq) {
        return sysDictTypeMapper.selectPage(pageReq);
    }
}




