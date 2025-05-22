package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.dict.SysDictTypePageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 字典类型 Mapper接口
*/
@Mapper
public interface SysDictTypeMapper extends BaseMapperX<SysDictType> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysDictType> selectPage(SysDictTypePageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysDictType>()
                .orderByDesc(BaseDO::getCreateTime));
    }

}




