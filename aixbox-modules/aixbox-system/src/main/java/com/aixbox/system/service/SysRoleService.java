package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleUpdateReqVO;

import java.util.List;

/**
* 角色 Service接口
*/
public interface SysRoleService {

    /**
     * 新增角色
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysRole(SysRoleSaveReqVO addReqVO);

    /**
     * 修改角色
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysRole(SysRoleUpdateReqVO updateReqVO);

    /**
     * 删除角色
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysRole(List<Long> ids);

    /**
     * 获取角色详细数据
     * @param id 数据id
     * @return 角色对象
     */
    SysRole getSysRole(Long id);

    /**
     * 分页查询角色
     * @param pageReqVO 分页查询参数
     * @return 角色分页对象
     */
    PageResult<SysRole> getSysRolePage(SysRolePageReqVO pageReqVO);
}
