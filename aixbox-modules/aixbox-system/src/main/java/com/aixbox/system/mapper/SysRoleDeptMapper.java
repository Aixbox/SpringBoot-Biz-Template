package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysRoleDept;
import com.aixbox.system.domain.vo.request.SysRoleDeptPageReq;
import org.apache.ibatis.annotations.Mapper;

/**
* 角色和部门关联 Mapper接口
*/
@Mapper
public interface SysRoleDeptMapper extends BaseMapperX<SysRoleDept> {

    /**
    * 分页查询
    * @param req 请求参数
    * @return demo分页对象
    */
    default PageResult<SysRoleDept> selectPage(SysRoleDeptPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysRoleDept>()
                .orderByDesc(BaseDO::getCreateTime));
    }

}




