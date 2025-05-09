package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysMenuUpdateReqVO;

import java.util.List;

/**
* 菜单 Service接口
*/
public interface SysMenuService {

    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysMenu(SysMenuSaveReqVO addReqVO);

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysMenu(SysMenuUpdateReqVO updateReqVO);

    /**
     * 删除菜单
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysMenu(List<Long> ids);

    /**
     * 获取菜单详细数据
     * @param id 数据id
     * @return 菜单对象
     */
    SysMenu getSysMenu(Long id);

    /**
     * 分页查询菜单
     * @param pageReqVO 分页查询参数
     * @return 菜单分页对象
     */
    PageResult<SysMenu> getSysMenuPage(SysMenuPageReqVO pageReqVO);
}
