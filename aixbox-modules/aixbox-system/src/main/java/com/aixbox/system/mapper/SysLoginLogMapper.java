package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysLoginLog;
import com.aixbox.system.domain.vo.request.SysLoginLogPageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 访问日志 Mapper接口
*/
@Mapper
public interface SysLoginLogMapper extends BaseMapperX<SysLoginLog> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysLoginLog> selectPage(SysLoginLogPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysLoginLog>()
                .likeIfPresent(SysLoginLog::getTraceId, req.getKeyword())
                .likeIfPresent(SysLoginLog::getUsername, req.getKeyword())
                .likeIfPresent(SysLoginLog::getUserIp, req.getKeyword())
                .likeIfPresent(SysLoginLog::getUserAgent, req.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




