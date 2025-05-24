package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.bo.SysDictDataBo;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.vo.request.dict.SysDictDataPageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictDataResp;
import com.aixbox.system.mapper.SysDictDataMapper;
import com.aixbox.system.service.SysDictDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
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
        SysDictDataBo dictDataBo = BeanUtils.toBean(pageReq, SysDictDataBo.class);
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictDataBo);
        return sysDictDataMapper.selectPage(pageReq, lqw);
    }

    @Override
    public List<SysDictDataResp> selectDictDataList(SysDictDataQueryReq dictData) {
        SysDictDataBo dictDataBo = BeanUtils.toBean(dictData, SysDictDataBo.class);
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictDataBo);
        List<SysDictData> sysDictData = sysDictDataMapper.selectList(lqw);
        return BeanUtils.toBean(sysDictData, SysDictDataResp.class);
    }

    private LambdaQueryWrapper<SysDictData> buildQueryWrapper(SysDictDataBo bo) {
        LambdaQueryWrapper<SysDictData> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDictSort() != null, SysDictData::getDictSort, bo.getDictSort());
        lqw.like(StringUtils.isNotBlank(bo.getDictLabel()), SysDictData::getDictLabel, bo.getDictLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getDictType()), SysDictData::getDictType, bo.getDictType());
        lqw.orderByAsc(SysDictData::getDictSort);
        return lqw;
    }
}




