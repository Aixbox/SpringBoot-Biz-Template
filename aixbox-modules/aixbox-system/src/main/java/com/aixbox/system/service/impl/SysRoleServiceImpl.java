package com.aixbox.system.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.AdminConstants;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysRoleBo;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.role.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleQueryReq;
import com.aixbox.system.domain.vo.request.role.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.role.SysRoleUpdateDataScopeReq;
import com.aixbox.system.domain.vo.request.role.SysRoleUpdateReq;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysRoleMenuMapper;
import com.aixbox.system.mapper.SysUserRoleMapper;
import com.aixbox.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 角色 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper roleMenuMapper;

    /**
     * 新增角色
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysRole(SysRoleSaveReqVO addReqVO) {
        SysRole sysRole = BeanUtils.toBean(addReqVO, SysRole.class);
        sysRoleMapper.insert(sysRole);
        return sysRole.getId();
    }

    /**
     * 修改角色
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysRole(SysRoleUpdateReq updateReqVO) {
        SysRole sysRole = MapstructUtils.convert(updateReqVO, SysRole.class);
        return sysRoleMapper.updateById(sysRole) > 0;
    }

    /**
     * 删除角色
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysRole(List<Long> ids) {
        for (Long roleId : ids) {
            SysRole role = sysRoleMapper.selectById(roleId);
            checkRoleAllowed(BeanUtil.toBean(role, SysRoleBo.class));
            checkRoleDataScope(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配，不能删除!", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));
        // todo 删除角色与部门关联
        //roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().in(SysRoleDept::getRoleId,ids));
        return sysRoleMapper.deleteByIds(ids) >  0;
    }

    /**
     * 获取角色详细数据
     * @param id 数据id
     * @return 角色对象
     */
    @Override
    public SysRole getSysRole(Long id) {
        return sysRoleMapper.selectById(id);
    }

    /**
     * 分页查询角色
     * @param pageReqVO 分页查询参数
     * @return 角色分页对象
     */
    @Override
    public PageResult<SysRole> getSysRolePage(SysRolePageReqVO pageReqVO) {
        SysRoleBo roleBo = BeanUtils.toBean(pageReqVO, SysRoleBo.class);
        return sysRoleMapper.selectPage(pageReqVO, this.buildQueryWrapper(roleBo));

    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(StrUtils.splitList(perm.getRoleKey().trim()));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        List<Long> ids = List.of(userIds);
        int rows = sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, ids));
        if (rows > 0) {
            cleanOnlineUser(ids);
        }
        return rows;
    }

    @Override
    public void cleanOnlineUser(List<Long> userIds) {
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        if (CollUtil.isEmpty(keys)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        keys.parallelStream().forEach(key -> {
            String token = StringUtils.substringAfterLast(key, ":");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                return;
            }
            LoginUser loginUser = LoginHelper.getLoginUser(token);
            if (ObjectUtil.isNull(loginUser)) {
                return;
            }
            if (userIds.contains(loginUser.getUserId())) {
                try {
                    StpUtil.logoutByTokenValue(token);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        int rows = sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, userRole.getRoleId())
                .eq(SysUserRole::getUserId, userRole.getUserId()));
        if (rows > 0) {
            cleanOnlineUser(List.of(userRole.getUserId()));
        }
        return rows;
    }

    @Override
    public void checkRoleDataScope(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        List<SysRole> roles = this.selectRoleList(new SysRoleQueryReq(roleId));
        if (CollUtil.isEmpty(roles)) {
            throw new ServiceException("没有权限访问角色数据！");
        }
    }



    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRoleQueryReq role) {
        SysRoleBo roleBo = BeanUtils.toBean(role, SysRoleBo.class);
        return sysRoleMapper.selectRoleList(this.buildQueryWrapper(roleBo));
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        int rows = 1;
        List<Long> ids = List.of(userIds);
        List<SysUserRole> list = StreamUtils.toList(ids, userId -> {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            return ur;
        });
        if (CollUtil.isNotEmpty(list)) {
            rows = sysUserRoleMapper.insertBatch(list) ? list.size() : 0;
        }
        if (rows > 0) {
            cleanOnlineUser(ids);
        }
        return rows;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRoleBo role) {
        if (ObjectUtil.isNotNull(role.getRoleId()) && LoginHelper.isSuperAdmin(role.getRoleId())) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
        String[] keys = new String[]{AdminConstants.SUPER_ADMIN_ROLE_KEY};
        // 新增不允许使用 管理员标识符
        if (ObjectUtil.isNull(role.getRoleId())
                && StringUtils.equalsAny(role.getRoleKey(), keys)) {
            throw new ServiceException("不允许使用系统内置管理员角色标识符!");
        }
        // 修改不允许修改 管理员标识符
        if (ObjectUtil.isNotNull(role.getRoleId())) {
            SysRole sysRole = sysRoleMapper.selectById(role.getRoleId());
            // 如果标识符不相等 判断为修改了管理员标识符
            if (!StringUtils.equals(sysRole.getRoleKey(), role.getRoleKey())) {
                if (StringUtils.equalsAny(sysRole.getRoleKey(), keys)) {
                    throw new ServiceException("不允许修改系统内置管理员角色标识符!");
                } else if (StringUtils.equalsAny(role.getRoleKey(), keys)) {
                    throw new ServiceException("不允许使用系统内置管理员角色标识符!");
                }
            }
        }
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态
     * @return 结果
     */
    @Override
    public int updateRoleStatus(Long roleId, String status) {
        if (SystemConstants.DISABLE.equals(status) && this.countUserRoleByRoleId(roleId) > 0) {
            throw new ServiceException("角色已分配，不能禁用!");
        }
        return sysRoleMapper.update(null,
                new LambdaUpdateWrapper<SysRole>()
                        .set(SysRole::getStatus, status)
                        .eq(SysRole::getId, roleId));
    }


    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public long countUserRoleByRoleId(Long roleId) {
        return sysUserRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId,
                roleId));
    }


    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int authDataScope(SysRoleUpdateDataScopeReq role) {
        SysRole roleEntity = BeanUtils.toBean(role, SysRole.class);
        roleEntity.setId(role.getRoleId());

        // 修改角色信息
        sysRoleMapper.updateById(roleEntity);
        // todo 添加角色部门关联表 删除角色与部门关联
        //roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, role.getRoleId()));
        //// 新增角色和部门信息（数据权限）
        //return insertRoleDept(bo);
        return 1;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param sysRolebo 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRoleBo sysRolebo) {
        boolean exist = sysRoleMapper.exists(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleName, sysRolebo.getRoleName())
                .ne(ObjectUtil.isNotNull(sysRolebo.getRoleId()), SysRole::getId, sysRolebo.getRoleId()));
        return !exist;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param sysRolebo 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRoleBo sysRolebo) {
        boolean exist = sysRoleMapper.exists(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleKey, sysRolebo.getRoleKey())
                .ne(ObjectUtil.isNotNull(sysRolebo.getRoleId()), SysRole::getId, sysRolebo.getRoleId()));
        return !exist;
    }

    @Override
    public void cleanOnlineUserByRole(Long roleId) {
        // 如果角色未绑定用户 直接返回
        Long num = sysUserRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
        if (num == 0) {
            return;
        }
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        if (CollUtil.isEmpty(keys)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        keys.parallelStream().forEach(key -> {
            String token = StringUtils.substringAfterLast(key, ":");
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                return;
            }
            LoginUser loginUser = LoginHelper.getLoginUser(token);
            if (ObjectUtil.isNull(loginUser) || CollUtil.isEmpty(loginUser.getRoles())) {
                return;
            }
            if (loginUser.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {
                try {
                    StpUtil.logoutByTokenValue(token);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }


    private Wrapper<SysRole> buildQueryWrapper(SysRoleBo bo) {
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        wrapper.eq("r.deleted", SystemConstants.NORMAL)
               .eq(ObjectUtil.isNotNull(bo.getRoleId()), "r.id", bo.getRoleId())
               .like(StringUtils.isNotBlank(bo.getRoleName()), "r.role_name", bo.getRoleName())
               .eq(StringUtils.isNotBlank(bo.getStatus()), "r.status", bo.getStatus())
               .like(StringUtils.isNotBlank(bo.getRoleKey()), "r.role_key", bo.getRoleKey())
               .orderByAsc("r.role_sort").orderByAsc("r.create_time");
        return wrapper;
    }


}




