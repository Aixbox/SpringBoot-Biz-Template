package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysDictDataBo;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.vo.request.dict.SysDictDataPageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictDataResp;
import jakarta.validation.Valid;

import java.util.List;

/**
* 字典数据 Service接口
*/
public interface SysDictDataService {

    /**
     * 新增字典数据
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysDictData(SysDictDataSaveReq addReq);

    /**
     * 修改字典数据
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysDictData(SysDictDataUpdateReq updateReq);

    /**
     * 删除字典数据
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysDictData(List<Long> ids);

    /**
     * 获取字典数据详细数据
     * @param id 数据id
     * @return 字典数据对象
     */
    SysDictData getSysDictData(Long id);

    /**
     * 分页查询字典数据
     * @param pageReq 分页查询参数
     * @return 字典数据分页对象
     */
    PageResult<SysDictData> getSysDictDataPage(SysDictDataPageReq pageReq);

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    List<SysDictDataResp> selectDictDataList(SysDictDataQueryReq dictData);

    /**
     * 校验字典键值是否唯一
     *
     * @param dict 字典数据
     * @return 结果
     */
    boolean checkDictDataUnique(SysDictDataBo dict);
}
