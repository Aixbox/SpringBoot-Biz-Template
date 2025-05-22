package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.dict.SysDictTypePageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictTypeResp;
import jakarta.validation.Valid;

import java.util.List;

/**
* 字典类型 Service接口
*/
public interface SysDictTypeService {

    /**
     * 新增字典类型
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysDictType(SysDictTypeSaveReq addReq);

    /**
     * 修改字典类型
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysDictType(SysDictTypeUpdateReq updateReq);

    /**
     * 删除字典类型
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysDictType(List<Long> ids);

    /**
     * 获取字典类型详细数据
     * @param id 数据id
     * @return 字典类型对象
     */
    SysDictType getSysDictType(Long id);

    /**
     * 分页查询字典类型
     * @param pageReq 分页查询参数
     * @return 字典类型分页对象
     */
    PageResult<SysDictType> getSysDictTypePage(SysDictTypePageReq pageReq);

    PageResult<SysDictType> selectPageDictTypeList(@Valid SysDictTypePageReq pageQuery);

    /**
     * 重置字典缓存数据
     */
    void resetDictCache();

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    List<SysDictTypeResp> selectDictTypeList(SysDictTypeQueryReq dictType);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    SysDictType selectDictTypeByType(String dictType);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeAll();
}
