package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.SysUserPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户 Mapper接口
*/
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUser> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysUser> selectPage(SysUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUser>()
                .likeIfPresent(SysUser::getUserName, reqVO.getKeyword())
                .likeIfPresent(SysUser::getNickName, reqVO.getKeyword())
                .likeIfPresent(SysUser::getUserType, reqVO.getKeyword())
                .likeIfPresent(SysUser::getPostIds, reqVO.getKeyword())
                .likeIfPresent(SysUser::getEmail, reqVO.getKeyword())
                .likeIfPresent(SysUser::getPhonenumber, reqVO.getKeyword())
                .likeIfPresent(SysUser::getSex, reqVO.getKeyword())
                .likeIfPresent(SysUser::getPassword, reqVO.getKeyword())
                .likeIfPresent(SysUser::getStatus, reqVO.getKeyword())
                .likeIfPresent(SysUser::getLoginIp, reqVO.getKeyword())
                .likeIfPresent(SysUser::getRemark, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




