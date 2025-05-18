package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.vo.request.SysDictDataPageReq;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 字典数据 Mapper接口
*/
@Mapper
public interface SysDictDataMapper extends BaseMapperX<SysDictData> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysDictData> selectPage(SysDictDataPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysDictData>()
                .likeIfPresent(SysDictData::getDictLabel, req.getKeyword())
                .likeIfPresent(SysDictData::getDictValue, req.getKeyword())
                .likeIfPresent(SysDictData::getDictType, req.getKeyword())
                .likeIfPresent(SysDictData::getCssClass, req.getKeyword())
                .likeIfPresent(SysDictData::getListClass, req.getKeyword())
                .likeIfPresent(SysDictData::getIsDefault, req.getKeyword())
                .likeIfPresent(SysDictData::getRemark, req.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

    /**
     * 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 符合条件的字典数据列表
     */
    default List<SysDictData> selectDictDataByType(String dictType) {
        return selectList(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getDictType, dictType)
                        .orderByAsc(SysDictData::getDictSort));
    }
}




