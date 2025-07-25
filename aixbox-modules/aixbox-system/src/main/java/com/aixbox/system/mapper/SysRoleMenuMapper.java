package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.vo.request.role.SysRoleMenuPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 角色和菜单关联 Mapper接口
*/
@Mapper
public interface SysRoleMenuMapper extends BaseMapperX<SysRoleMenu> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysRoleMenu> selectPage(SysRoleMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRoleMenu>()
                .orderByDesc(BaseDO::getCreateTime));
    }

}




