package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 参数配置 Mapper接口
*/
@Mapper
public interface SysConfigMapper extends BaseMapperX<SysConfig> {

}




