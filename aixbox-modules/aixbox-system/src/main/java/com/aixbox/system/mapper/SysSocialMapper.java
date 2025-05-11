package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.vo.request.SysSocialPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 社交用户 Mapper接口
*/
@Mapper
public interface SysSocialMapper extends BaseMapperX<SysSocial> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysSocial> selectPage(SysSocialPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysSocial>()
                .likeIfPresent(SysSocial::getAuthId, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getSource, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getOpenId, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getUserName, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getNickName, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getEmail, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getAvatar, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getAccessToken, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getRefreshToken, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getAccessCode, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getUnionId, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getScope, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getTokenType, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getIdToken, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getMacAlgorithm, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getMacKey, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getCode, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getOauthToken, reqVO.getKeyword())
                .likeIfPresent(SysSocial::getOauthTokenSecret, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




