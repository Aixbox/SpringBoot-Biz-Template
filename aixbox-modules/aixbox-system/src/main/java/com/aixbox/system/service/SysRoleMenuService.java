package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.vo.request.SysRoleMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleMenuUpdateReqVO;

import java.util.List;

/**
* 角色和菜单关联 Service接口
*/
public interface SysRoleMenuService {

    /**
     * 新增角色和菜单关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysRoleMenu(SysRoleMenuSaveReqVO addReqVO);

    /**
     * 修改角色和菜单关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysRoleMenu(SysRoleMenuUpdateReqVO updateReqVO);

    /**
     * 删除角色和菜单关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysRoleMenu(List<Long> ids);

    /**
     * 获取角色和菜单关联详细数据
     * @param id 数据id
     * @return 角色和菜单关联对象
     */
    SysRoleMenu getSysRoleMenu(Long id);

    /**
     * 分页查询角色和菜单关联
     * @param pageReqVO 分页查询参数
     * @return 角色和菜单关联分页对象
     */
    PageResult<SysRoleMenu> getSysRoleMenuPage(SysRoleMenuPageReqVO pageReqVO);
}
