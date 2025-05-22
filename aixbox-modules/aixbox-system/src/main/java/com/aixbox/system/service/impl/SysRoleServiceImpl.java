package com.aixbox.system.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleUpdateReqVO;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysUserRoleMapper;
import com.aixbox.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* 角色 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

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
    public Boolean updateSysRole(SysRoleUpdateReqVO updateReqVO) {
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
        return sysRoleMapper.deleteByIds(ids) > 0;
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
        return sysRoleMapper.selectPage(pageReqVO);
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
}




