package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.SysMenuPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 菜单 Mapper接口
*/
@Mapper
public interface SysMenuMapper extends BaseMapperX<SysMenu> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysMenu> selectPage(SysMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysMenu>()
                .likeIfPresent(SysMenu::getMenuName, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getPath, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getComponent, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getQueryParam, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getMenuType, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getVisible, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getStatus, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getPerms, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getIcon, reqVO.getKeyword())
                .likeIfPresent(SysMenu::getRemark, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




