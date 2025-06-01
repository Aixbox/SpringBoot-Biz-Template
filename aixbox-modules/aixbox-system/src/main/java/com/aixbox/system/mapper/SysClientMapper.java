package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.client.SysClientPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 客户端 Mapper接口
*/
@Mapper
public interface SysClientMapper extends BaseMapperX<SysClient> {

}




