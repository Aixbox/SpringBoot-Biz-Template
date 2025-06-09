package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import com.aixbox.system.domain.entity.SysOssConfig;
import com.aixbox.system.domain.vo.request.SysOssConfigPageReq;

import java.util.List;




/**
* 对象存储配置 Mapper接口
*/
@Mapper
public interface SysOssConfigMapper extends BaseMapperX<SysOssConfig> {
    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return SysOssConfig分页对象
    */
    default PageResult<SysOssConfig> selectPage(SysOssConfigPageReq reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysOssConfig>()
                    .eqIfPresent(SysOssConfig::getConfigKey, reqVO.getConfigKey())
                    .eqIfPresent(SysOssConfig::getAccessKey, reqVO.getAccessKey())
                    .eqIfPresent(SysOssConfig::getSecretKey, reqVO.getSecretKey())
                    .likeIfPresent(SysOssConfig::getBucketName, reqVO.getBucketName())
                    .eqIfPresent(SysOssConfig::getPrefix, reqVO.getPrefix())
                    .eqIfPresent(SysOssConfig::getEndpoint, reqVO.getEndpoint())
                    .eqIfPresent(SysOssConfig::getDomain, reqVO.getDomain())
                    .eqIfPresent(SysOssConfig::getIsHttps, reqVO.getIsHttps())
                    .eqIfPresent(SysOssConfig::getRegion, reqVO.getRegion())
                    .eqIfPresent(SysOssConfig::getAccessPolicy, reqVO.getAccessPolicy())
                    .eqIfPresent(SysOssConfig::getStatus, reqVO.getStatus())
                    .eqIfPresent(SysOssConfig::getExt1, reqVO.getExt1())
                    .eqIfPresent(SysOssConfig::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysOssConfig::getId));    }

    default Long countByIds(List<Long> ids) {
        return selectCount(new LambdaQueryWrapperX<SysOssConfig>().in(SysOssConfig::getId, ids));
    }
}




