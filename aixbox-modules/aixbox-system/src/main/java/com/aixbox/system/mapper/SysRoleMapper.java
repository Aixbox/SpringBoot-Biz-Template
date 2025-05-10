package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 角色 Mapper接口
*/
@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRole> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysRole> selectPage(SysRolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRole>()
                .likeIfPresent(SysRole::getRoleName, reqVO.getKeyword())
                .likeIfPresent(SysRole::getRoleKey, reqVO.getKeyword())
                .likeIfPresent(SysRole::getDataScope, reqVO.getKeyword())
                .likeIfPresent(SysRole::getStatus, reqVO.getKeyword())
                .likeIfPresent(SysRole::getRemark, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);

}




