package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysRoleBo;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysRoleChangeStatusReq;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleQueryReq;
import com.aixbox.system.domain.vo.request.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleUpdateDataScopeReq;
import com.aixbox.system.domain.vo.request.SysRoleUpdateReq;

import java.util.List;
import java.util.Set;

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
    Boolean updateSysRole(SysRoleUpdateReq updateReqVO);

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

    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, Long[] userIds);

    void cleanOnlineUser(List<Long> userIds);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteAuthUser(SysUserRole userRole);

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    void checkRoleDataScope(Long roleId);


    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(SysRoleQueryReq role);


    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUsers(Long roleId, Long[] userIds);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    void checkRoleAllowed(SysRoleBo role);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态
     * @return 结果
     */
    int updateRoleStatus(Long roleId, String status);

    long countUserRoleByRoleId(Long roleId);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int authDataScope(SysRoleUpdateDataScopeReq role);
}
