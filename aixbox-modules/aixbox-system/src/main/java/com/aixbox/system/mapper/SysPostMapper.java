package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.SysPostPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 岗位 Mapper接口
*/
@Mapper
public interface SysPostMapper extends BaseMapperX<SysPost> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysPost> selectPage(SysPostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysPost>()
                .likeIfPresent(SysPost::getPostCode, reqVO.getKeyword())
                .likeIfPresent(SysPost::getPostCategory, reqVO.getKeyword())
                .likeIfPresent(SysPost::getPostName, reqVO.getKeyword())
                .likeIfPresent(SysPost::getStatus, reqVO.getKeyword())
                .likeIfPresent(SysPost::getRemark, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




