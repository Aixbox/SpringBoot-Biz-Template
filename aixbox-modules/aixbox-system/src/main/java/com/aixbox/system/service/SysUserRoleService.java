package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysUserRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleUpdateReqVO;

import java.util.List;

/**
* 用户和角色关联 Service接口
*/
public interface SysUserRoleService {

    /**
     * 新增用户和角色关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysUserRole(SysUserRoleSaveReqVO addReqVO);

    /**
     * 修改用户和角色关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysUserRole(SysUserRoleUpdateReqVO updateReqVO);

    /**
     * 删除用户和角色关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysUserRole(List<Long> ids);

    /**
     * 获取用户和角色关联详细数据
     * @param id 数据id
     * @return 用户和角色关联对象
     */
    SysUserRole getSysUserRole(Long id);

    /**
     * 分页查询用户和角色关联
     * @param pageReqVO 分页查询参数
     * @return 用户和角色关联分页对象
     */
    PageResult<SysUserRole> getSysUserRolePage(SysUserRolePageReqVO pageReqVO);
}
