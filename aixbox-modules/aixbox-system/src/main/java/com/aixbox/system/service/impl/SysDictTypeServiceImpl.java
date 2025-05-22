package com.aixbox.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.aixbox.common.core.domain.dto.DictDataDTO;
import com.aixbox.common.core.domain.dto.DictTypeDTO;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.service.DictService;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.redis.utils.CacheUtils;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.dict.SysDictTypePageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictTypeResp;
import com.aixbox.system.mapper.SysDictDataMapper;
import com.aixbox.system.mapper.SysDictTypeMapper;
import com.aixbox.system.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 字典类型 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService, DictService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper dictDataMapper;

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

    @Override
    public PageResult<SysDictType> selectPageDictTypeList(SysDictTypePageReq pageQuery) {
        LambdaQueryWrapper<SysDictType> lqw = buildQueryWrapper(pageQuery);
        return sysDictTypeMapper.selectPage(pageQuery, lqw);
    }

    @Override
    public void resetDictCache() {
        CacheUtils.clear(CacheNames.SYS_DICT);
        CacheUtils.clear(CacheNames.SYS_DICT_TYPE);
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictTypeResp> selectDictTypeList(SysDictTypeQueryReq dictType) {
        LambdaQueryWrapper<SysDictType> lqw = buildQueryWrapper(BeanUtils.toBean(dictType,
                SysDictTypePageReq.class));
        List<SysDictType> sysDictTypes = sysDictTypeMapper.selectList(lqw);
        return BeanUtils.toBean(sysDictTypes, SysDictTypeResp.class);
    }


    private LambdaQueryWrapper<SysDictType> buildQueryWrapper(SysDictTypePageReq bo) {
        LambdaQueryWrapper<SysDictType> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtils.isNotBlank(bo.getDictName()), SysDictType::getDictName, bo.getDictName());
        lqw.like(StrUtils.isNotBlank(bo.getDictType()), SysDictType::getDictType, bo.getDictType());
        lqw.orderByAsc(SysDictType::getId);
        return lqw;
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Cacheable(cacheNames = CacheNames.SYS_DICT_TYPE, key = "#dictType")
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return sysDictTypeMapper.selectOne(new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getDictType, dictType));
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DICT, key = "#dictType")
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (CollUtil.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        return sysDictTypeMapper.selectList();
    }


    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {
        List<SysDictData> datas = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
        Map<String, String> map = StreamUtils.toMap(datas, SysDictData::getDictValue, SysDictData::getDictLabel);
        if (StringUtils.containsAny(dictValue, separator)) {
            return Arrays.stream(dictValue.split(separator))
                         .map(v -> map.getOrDefault(v, StringUtils.EMPTY))
                         .collect(Collectors.joining(separator));
        } else {
            return map.getOrDefault(dictValue, StringUtils.EMPTY);
        }
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {
        List<SysDictData> datas = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
        Map<String, String> map = StreamUtils.toMap(datas, SysDictData::getDictLabel, SysDictData::getDictValue);
        if (StringUtils.containsAny(dictLabel, separator)) {
            return Arrays.stream(dictLabel.split(separator))
                         .map(l -> map.getOrDefault(l, StringUtils.EMPTY))
                         .collect(Collectors.joining(separator));
        } else {
            return map.getOrDefault(dictLabel, StringUtils.EMPTY);
        }
    }

    /**
     * 获取字典下所有的字典值与标签
     *
     * @param dictType 字典类型
     * @return dictValue为key，dictLabel为值组成的Map
     */
    @Override
    public Map<String, String> getAllDictByDictType(String dictType) {
        List<SysDictData> list = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
        // 保证顺序
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (SysDictData vo : list) {
            map.put(vo.getDictValue(), vo.getDictLabel());
        }
        return map;
    }

    /**
     * 根据字典类型查询详细信息
     *
     * @param dictType 字典类型
     * @return 字典类型详细信息
     */
    @Override
    public DictTypeDTO getDictType(String dictType) {
        SysDictType vo = SpringUtils.getAopProxy(this).selectDictTypeByType(dictType);
        return BeanUtil.toBean(vo, DictTypeDTO.class);
    }

    /**
     * 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    @Override
    public List<DictDataDTO> getDictData(String dictType) {
        List<SysDictData> list = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
        return BeanUtil.copyToList(list, DictDataDTO.class);
    }



}




