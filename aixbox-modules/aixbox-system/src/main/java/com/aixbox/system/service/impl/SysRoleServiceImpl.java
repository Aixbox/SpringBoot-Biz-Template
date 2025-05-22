package com.aixbox.system.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleQueryReq;
import com.aixbox.system.domain.vo.request.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleUpdateReqVO;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysUserRoleMapper;
import com.aixbox.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
        return sysRoleMapper.selectRoleList(this.buildQueryWrapper(role));
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


    private Wrapper<SysRole> buildQueryWrapper(SysRoleQueryReq bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        wrapper.eq("r.deleted", SystemConstants.NORMAL)
               .eq(ObjectUtil.isNotNull(bo.getRoleId()), "r.id", bo.getRoleId())
               .like(StringUtils.isNotBlank(bo.getRoleName()), "r.role_name", bo.getRoleName())
               .eq(StringUtils.isNotBlank(bo.getStatus()), "r.status", bo.getStatus())
               .like(StringUtils.isNotBlank(bo.getRoleKey()), "r.role_key", bo.getRoleKey())
               .between(params.get("beginTime") != null && params.get("endTime") != null,
                       "r.create_time", params.get("beginTime"), params.get("endTime"))
               .orderByAsc("r.role_sort").orderByAsc("r.create_time");
        return wrapper;
    }


}




