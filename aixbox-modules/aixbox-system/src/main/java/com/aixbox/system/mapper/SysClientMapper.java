package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.SysClientPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 客户端 Mapper接口
*/
@Mapper
public interface SysClientMapper extends BaseMapperX<SysClient> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysClient> selectPage(SysClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysClient>()
                .likeIfPresent(SysClient::getClientId, reqVO.getKeyword())
                .likeIfPresent(SysClient::getClientKey, reqVO.getKeyword())
                .likeIfPresent(SysClient::getClientSecret, reqVO.getKeyword())
                .likeIfPresent(SysClient::getGrantType, reqVO.getKeyword())
                .likeIfPresent(SysClient::getDeviceType, reqVO.getKeyword())
                .likeIfPresent(SysClient::getStatus, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




