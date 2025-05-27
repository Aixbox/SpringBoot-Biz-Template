package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.user.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserQueryReq;
import com.aixbox.system.domain.vo.request.user.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysRoleResp;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.mapper.SysDeptMapper;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.mapper.SysUserPostMapper;
import com.aixbox.system.mapper.SysUserRoleMapper;
import com.aixbox.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_NO_PERMISSION;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_SUPER_ADMIN;

/**
* 用户 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysDeptMapper deptMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysUserPostMapper sysUserPostMapper;

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
        SysUserBo sysUser = BeanUtils.toBean(pageReqVO, SysUserBo.class);
        return sysUserMapper.selectPageUserList(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()), this.buildQueryWrapper(sysUser));
    }

    private Wrapper<SysUser> buildQueryWrapper(SysUserBo user) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.deleted", SystemConstants.NORMAL)
               .eq(ObjectUtil.isNotNull(user.getId()), "u.id", user.getId())
               .in(StrUtils.isNotBlank(user.getUserIds()), "u.id", StrUtils.splitTo(user.getUserIds(), Convert::toLong))
               .like(StrUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
               .eq(StrUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
               .like(StrUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
               .and(ObjectUtil.isNotNull(user.getDeptId()), w -> {
                   List<SysDept> deptList = deptMapper.selectListByParentId(user.getDeptId());
                   List<Long> ids = StreamUtils.toList(deptList, SysDept::getId);
                   ids.add(user.getDeptId());
                   w.in("u.dept_id", ids);
               }).orderByAsc("u.id");
        if (StrUtils.isNotBlank(user.getExcludeUserIds())) {
            wrapper.notIn("u.id", StrUtils.splitTo(user.getExcludeUserIds(), Convert::toLong));
        }
        return wrapper;
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
        List<SysRoleResp> sysRoleResp = MapstructUtils.convert(sysRoles, SysRoleResp.class);
        userResp.setRoles(sysRoleResp);
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

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUserResp> selectUserExportList(SysUserBo user) {
        return sysUserMapper.selectUserExportList(this.buildQueryWrapper(user));
    }

    /**
     * 通过手机号查询用户
     *
     * @param phonenumber 手机号
     * @return 用户对象信息
     */
    @Override
    public SysUserResp selectUserByUserName(String phonenumber) {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, phonenumber));
        return BeanUtils.toBean(sysUser, SysUserResp.class);
    }

    @Override
    public int insertUser(SysUserBo user) {
        SysUser sysUser = BeanUtils.toBean(user, SysUser.class);
        // 新增用户信息
        int rows = sysUserMapper.insert(sysUser);
        user.setId(sysUser.getId());
        //todo
        // 新增用户岗位关联
        //insertUserPost(user, false);
        // 新增用户与角色管理
        //insertUserRole(user, false);
        return rows;
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public PageResult<SysUserResp> selectUnallocatedList(SysUserBo user, PageParam pageQuery) {
        List<Long> userIds = sysUserRoleMapper.selectUserIdsByRoleId(user.getRoleId());
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.deleted", SystemConstants.NORMAL)
               .and(w -> w.ne("r.id", user.getRoleId()).or().isNull("r.id"))
               .notIn(CollUtil.isNotEmpty(userIds), "u.id", userIds)
               .like(StrUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
               .like(StrUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
               .orderByAsc("u.id");
        Page<SysUser> page = sysUserMapper.selectUnallocatedList(new Page<>(pageQuery.getPageNo()
                        , pageQuery.getPageSize()), wrapper);
        List<SysUserResp> userResps = BeanUtils.toBean(page.getRecords(), SysUserResp.class);
        return new PageResult<>(userResps, page.getTotal());
    }

    /**
     * 校验用户是否允许操作
     *
     * @param userId 用户ID
     */
    @Override
    public void checkUserAllowed(Long userId) {
        if (ObjectUtil.isNotNull(userId) && LoginHelper.isSuperAdmin(userId)) {
            throw exception(USERNAME_SUPER_ADMIN);
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (ObjectUtil.isNull(userId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        if (sysUserMapper.countUserById(userId) == 0) {
            throw exception(USERNAME_NO_PERMISSION);
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @CacheEvict(cacheNames = CacheNames.SYS_NICKNAME, key = "#user.userId")
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUserBo user) {
        // 新增用户与角色管理
        insertUserRole(user, true);
        // 新增用户与岗位管理
        insertUserPost(user, true);
        SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
        // 防止错误更新后导致的数据误删除
        int flag = sysUserMapper.updateById(sysUser);
        if (flag < 1) {
            throw new ServiceException("修改用户" + user.getUserName() + "信息失败");
        }
        return flag;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(SysUserBo user) {
        boolean exist = sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, user.getUserName())
                .ne(ObjectUtil.isNotNull(user.getId()), SysUser::getId, user.getId()));
        return !exist;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     */
    @Override
    public boolean checkPhoneUnique(SysUserBo user) {
        boolean exist = sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPhonenumber, user.getPhonenumber())
                .ne(ObjectUtil.isNotNull(user.getId()), SysUser::getId, user.getId()));
        return !exist;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     */
    @Override
    public boolean checkEmailUnique(SysUserBo user) {
        boolean exist = sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEmail, user.getEmail())
                .ne(ObjectUtil.isNotNull(user.getId()), SysUser::getId, user.getId()));
        return !exist;
    }

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 帐号状态
     * @return 结果
     */
    @Override
    public int updateUserStatus(Long userId, String status) {
        return sysUserMapper.update(null,
                new LambdaUpdateWrapper<SysUser>()
                        .set(SysUser::getStatus, status)
                        .eq(SysUser::getId, userId));
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(List<Long> userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(userId);
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId,
                userIds));
        // 删除用户与岗位表
        sysUserPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getUserId,
                userIds));
        // 防止更新失败导致的数据删除
        int flag = sysUserMapper.deleteByIds(userIds);
        if (flag < 1) {
            throw exception(DELETE_USER_ERROR);
        }
        return flag;
    }

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(Long userId, String password) {
        return sysUserMapper.update(null,
                new LambdaUpdateWrapper<SysUser>()
                        .set(SysUser::getPassword, password)
                        .eq(SysUser::getId, userId));
    }

    @Override
    public List<SysUserResp> selectUserListByDept(Long deptId) {
        LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(SysUser::getDeptId, deptId);
        lqw.orderByAsc(SysUser::getId);
        List<SysUser> sysUsers = sysUserMapper.selectList(lqw);
        return BeanUtils.toBean(sysUsers, SysUserResp.class);
    }

    /**
     * 新增用户岗位信息
     *
     * @param user  用户对象
     * @param clear 清除已存在的关联数据
     */
    private void insertUserPost(SysUserBo user, boolean clear) {
        Long[] posts = user.getPostIds();
        if (ArrayUtil.isNotEmpty(posts)) {
            if (clear) {
                // 删除用户与岗位关联
                sysUserPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, user.getId()));
            }
            // 新增用户与岗位管理
            List<SysUserPost> list = StreamUtils.toList(List.of(posts), postId -> {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getId());
                up.setPostId(postId);
                return up;
            });
            sysUserPostMapper.insertBatch(list);
        }
    }


    /**
     * 新增用户角色信息
     *
     * @param user  用户对象
     * @param clear 清除已存在的关联数据
     */
    private void insertUserRole(SysUserBo user, boolean clear) {
        this.insertUserRole(user.getId(), user.getRoleIds(), clear);
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     * @param clear   清除已存在的关联数据
     */
    private void insertUserRole(Long userId, Long[] roleIds, boolean clear) {
        if (ArrayUtil.isNotEmpty(roleIds)) {
            List<Long> roleList = new ArrayList<>(List.of(roleIds));
            if (!LoginHelper.isSuperAdmin(userId)) {
                roleList.remove(SystemConstants.SUPER_ADMIN_ID);
            }
            // 判断是否具有此角色的操作权限
            List<SysRole> roles = sysRoleMapper.selectRoleList(
                    new QueryWrapper<SysRole>().in("r.id", roleList));
            if (CollUtil.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色的数据");
            }
            if (clear) {
                // 删除用户与角色关联
                sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
            }
            // 新增用户与角色管理
            List<SysUserRole> list = StreamUtils.toList(roleList, roleId -> {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            });
            sysUserRoleMapper.insertBatch(list);
        }
    }


}



























