package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.user.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserQueryReq;
import com.aixbox.system.domain.vo.request.user.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysUserResp;
import jakarta.validation.Valid;

import java.util.List;

/**
* 用户 Service接口
*/
public interface SysUserService {

    /**
     * 新增用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysUser(SysUserSaveReqVO addReqVO);

    /**
     * 修改用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysUser(SysUserUpdateReqVO updateReqVO);

    /**
     * 删除用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysUser(List<Long> ids);

    /**
     * 获取用户详细数据
     * @param id 数据id
     * @return 用户对象
     */
    SysUser getSysUser(Long id);

    /**
     * 分页查询用户
     * @param pageReqVO 分页查询参数
     * @return 用户分页对象
     */
    PageResult<SysUser> getSysUserPage(SysUserPageReqVO pageReqVO);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(SysUser user);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUserResp selectUserById(Long userId);


    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user      用户信息
     * @param pageQuery 分页
     * @return 用户信息集合信息
     */
    PageResult<SysUserResp> selectAllocatedList(SysUserQueryReq user, PageParam pageQuery);

    /**
     * 导出用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserResp> selectUserExportList(SysUserBo user);


    /**
     * 通过手机号查询用户
     *
     * @param phonenumber 手机号
     * @return 用户对象信息
     */
    SysUserResp selectUserByUserName(String phonenumber);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUserBo user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user      用户信息
     * @param pageQuery 分页
     * @return 用户信息集合信息
     */
    PageResult<SysUserResp> selectUnallocatedList(SysUserBo user, @Valid PageParam pageQuery);

    /**
     * 校验用户是否允许操作
     *
     * @param userId 用户ID
     */
    void checkUserAllowed(Long userId);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    void checkUserDataScope(Long userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUserBo user);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkUserNameUnique(SysUserBo user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkPhoneUnique(SysUserBo user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkEmailUnique(SysUserBo user);
}
























