package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.SysUserUpdateReqVO;

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
}
