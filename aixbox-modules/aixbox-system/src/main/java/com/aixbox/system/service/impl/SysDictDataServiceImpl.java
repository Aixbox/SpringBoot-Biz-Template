package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.vo.request.SysDictDataPageReq;
import com.aixbox.system.domain.vo.request.SysDictDataSaveReq;
import com.aixbox.system.domain.vo.request.SysDictDataUpdateReq;
import com.aixbox.system.mapper.SysDictDataMapper;
import com.aixbox.system.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 字典数据 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 新增字典数据
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysDictData(SysDictDataSaveReq addReq) {
        SysDictData sysDictData = BeanUtils.toBean(addReq, SysDictData.class);
        sysDictDataMapper.insert(sysDictData);
        return sysDictData.getId();
    }

    /**
     * 修改字典数据
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysDictData(SysDictDataUpdateReq updateReq) {
        SysDictData sysDictData = MapstructUtils.convert(updateReq, SysDictData.class);
        return sysDictDataMapper.updateById(sysDictData) > 0;
    }

    /**
     * 删除字典数据
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysDictData(List<Long> ids) {
        return sysDictDataMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取字典数据详细数据
     * @param id 数据id
     * @return 字典数据对象
     */
    @Override
    public SysDictData getSysDictData(Long id) {
        return sysDictDataMapper.selectById(id);
    }

    /**
     * 分页查询字典数据
     * @param pageReq 分页查询参数
     * @return 字典数据分页对象
     */
    @Override
    public PageResult<SysDictData> getSysDictDataPage(SysDictDataPageReq pageReq) {
        return sysDictDataMapper.selectPage(pageReq);
    }
}




