package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysOperateLog;
import com.aixbox.system.domain.vo.request.SysOperateLogPageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 操作日志 Mapper接口
*/
@Mapper
public interface SysOperateLogMapper extends BaseMapperX<SysOperateLog> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysOperateLog> selectPage(SysOperateLogPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysOperateLog>()
                .likeIfPresent(SysOperateLog::getTraceId, req.getKeyword())
                .likeIfPresent(SysOperateLog::getType, req.getKeyword())
                .likeIfPresent(SysOperateLog::getSubType, req.getKeyword())
                .likeIfPresent(SysOperateLog::getAction, req.getKeyword())
                .likeIfPresent(SysOperateLog::getExtra, req.getKeyword())
                .likeIfPresent(SysOperateLog::getRequestMethod, req.getKeyword())
                .likeIfPresent(SysOperateLog::getRequestUrl, req.getKeyword())
                .likeIfPresent(SysOperateLog::getUserIp, req.getKeyword())
                .likeIfPresent(SysOperateLog::getUserAgent, req.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




