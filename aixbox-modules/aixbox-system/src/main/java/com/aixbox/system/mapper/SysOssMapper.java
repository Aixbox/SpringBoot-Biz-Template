package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;

import java.util.List;




/**
* OSS对象存储 Mapper接口
*/
@Mapper
public interface SysOssMapper extends BaseMapperX<SysOss> {
    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return SysOss分页对象
    */
    default PageResult<SysOss> selectPage(SysOssPageReq reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysOss>()
                    .likeIfPresent(SysOss::getFileName, reqVO.getFileName())
                    .likeIfPresent(SysOss::getOriginalName, reqVO.getOriginalName())
                    .eqIfPresent(SysOss::getFileSuffix, reqVO.getFileSuffix())
                    .eqIfPresent(SysOss::getUrl, reqVO.getUrl())
                    .eqIfPresent(SysOss::getExt1, reqVO.getExt1())
                    .eqIfPresent(SysOss::getService, reqVO.getService())
                    .eqIfPresent(SysOss::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysOss::getId));    }

    default Long countByIds(List<Long> ids) {
        return selectCount(new LambdaQueryWrapperX<SysOss>().in(SysOss::getId, ids));
    }
}




