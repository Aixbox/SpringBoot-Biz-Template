package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.SysDeptPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 部门 Mapper接口
*/
@Mapper
public interface SysDeptMapper extends BaseMapperX<SysDept> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysDept> selectPage(SysDeptPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDept>()
                .likeIfPresent(SysDept::getAncestors, reqVO.getKeyword())
                .likeIfPresent(SysDept::getDeptName, reqVO.getKeyword())
                .likeIfPresent(SysDept::getDeptCategory, reqVO.getKeyword())
                .likeIfPresent(SysDept::getPhone, reqVO.getKeyword())
                .likeIfPresent(SysDept::getEmail, reqVO.getKeyword())
                .likeIfPresent(SysDept::getStatus, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




