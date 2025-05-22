package com.aixbox.system.mapper;

import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.menu.SysMenuPageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    default List<SysMenu> selectMenuTreeAll() {
        LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<SysMenu>()
                .in(SysMenu::getMenuType, SystemConstants.TYPE_DIR, SystemConstants.TYPE_MENU)
                .eq(SysMenu::getStatus, SystemConstants.NORMAL)
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getOrderNum);
        return this.selectList(lqw);
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);


    /**
     * 根据用户查询系统菜单列表
     *
     * @param queryWrapper 查询条件
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(QueryWrapper<SysMenu> queryWrapper);


    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId            角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId, Long menuCheckStrictly);
}




