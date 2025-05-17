package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.SysDictTypePageReq;
import com.aixbox.system.domain.vo.request.SysDictTypeSaveReq;
import com.aixbox.system.domain.vo.request.SysDictTypeUpdateReq;

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
}
