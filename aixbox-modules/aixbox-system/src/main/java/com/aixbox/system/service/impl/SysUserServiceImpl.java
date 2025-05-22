package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.user.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserQueryReq;
import com.aixbox.system.domain.vo.request.user.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysRoleVO;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 用户 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;

    /**
     * 新增用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysUser(SysUserSaveReqVO addReqVO) {
        SysUser sysUser = BeanUtils.toBean(addReqVO, SysUser.class);
        sysUserMapper.insert(sysUser);
        return sysUser.getId();
    }

    /**
     * 修改用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysUser(SysUserUpdateReqVO updateReqVO) {
        SysUser sysUser = MapstructUtils.convert(updateReqVO, SysUser.class);
        return sysUserMapper.updateById(sysUser) > 0;
    }

    /**
     * 删除用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysUser(List<Long> ids) {
        return sysUserMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取用户详细数据
     * @param id 数据id
     * @return 用户对象
     */
    @Override
    public SysUser getSysUser(Long id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * 分页查询用户
     * @param pageReqVO 分页查询参数
     * @return 用户分页对象
     */
    @Override
    public PageResult<SysUser> getSysUserPage(SysUserPageReqVO pageReqVO) {
        return sysUserMapper.selectPage(pageReqVO);
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        user.setCreator("0");
        user.setUpdater("0");
        return sysUserMapper.insert(user) > 0;

    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUserResp selectUserById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            return null;
        }
        SysUserResp userResp = BeanUtils.toBean(user, SysUserResp.class);
        List<SysRole> sysRoles = sysRoleMapper.selectRolesByUserId(user.getId());
        List<SysRoleVO> sysRoleVO = MapstructUtils.convert(sysRoles, SysRoleVO.class);
        userResp.setRoles(sysRoleVO);
        return userResp;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public PageResult<SysUserResp> selectAllocatedList(SysUserQueryReq user, PageParam pageQuery) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.deleted", SystemConstants.NORMAL)
               .eq(ObjectUtil.isNotNull(user.getRoleId()), "r.role_id", user.getRoleId())
               .like(StrUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
               .eq(StrUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
               .like(StrUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
               .orderByAsc("u.id");
        Page<SysUser> page = sysUserMapper.selectAllocatedList(new Page<>(pageQuery.getPageNo(),
                pageQuery.getPageSize()), wrapper);
        return new PageResult<>(BeanUtils.toBean(page.getRecords(), SysUserResp.class), page.getTotal());
    }
}




