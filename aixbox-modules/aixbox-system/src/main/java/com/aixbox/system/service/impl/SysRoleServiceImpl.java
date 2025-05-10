package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.vo.request.SysRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleUpdateReqVO;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
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
}




