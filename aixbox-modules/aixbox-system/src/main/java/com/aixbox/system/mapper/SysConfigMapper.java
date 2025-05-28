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

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysConfig> selectPage(SysConfigPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysConfig>()
                .likeIfPresent(SysConfig::getConfigName, req.getKeyword())
                .likeIfPresent(SysConfig::getConfigKey, req.getKeyword())
                .likeIfPresent(SysConfig::getConfigValue, req.getKeyword())
                .likeIfPresent(SysConfig::getConfigType, req.getKeyword())
                .likeIfPresent(SysConfig::getRemark, req.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




