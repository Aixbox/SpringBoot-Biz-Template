package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysUserRolePageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户和角色关联 Mapper接口
*/
@Mapper
public interface SysUserRoleMapper extends BaseMapperX<SysUserRole> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysUserRole> selectPage(SysUserRolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUserRole>()
                .orderByDesc(BaseDO::getCreateTime));
    }

}




