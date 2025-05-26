package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.vo.request.SysUserPostPageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户岗位关联 Mapper接口
*/
@Mapper
public interface SysUserPostMapper extends BaseMapperX<SysUserPost> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysUserPost> selectPage(SysUserPostPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysUserPost>()
                .orderByDesc(BaseDO::getCreateTime));
    }

}




