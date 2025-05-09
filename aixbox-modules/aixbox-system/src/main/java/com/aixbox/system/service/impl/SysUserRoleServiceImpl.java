package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysUserRole;
import com.aixbox.system.domain.vo.request.SysUserRolePageReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleSaveReqVO;
import com.aixbox.system.domain.vo.request.SysUserRoleUpdateReqVO;
import com.aixbox.system.mapper.SysUserRoleMapper;
import com.aixbox.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 用户和角色关联 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 新增用户和角色关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysUserRole(SysUserRoleSaveReqVO addReqVO) {
        SysUserRole sysUserRole = BeanUtils.toBean(addReqVO, SysUserRole.class);
        sysUserRoleMapper.insert(sysUserRole);
        return sysUserRole.getId();
    }

    /**
     * 修改用户和角色关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysUserRole(SysUserRoleUpdateReqVO updateReqVO) {
        SysUserRole sysUserRole = MapstructUtils.convert(updateReqVO, SysUserRole.class);
        return sysUserRoleMapper.updateById(sysUserRole) > 0;
    }

    /**
     * 删除用户和角色关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysUserRole(List<Long> ids) {
        return sysUserRoleMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取用户和角色关联详细数据
     * @param id 数据id
     * @return 用户和角色关联对象
     */
    @Override
    public SysUserRole getSysUserRole(Long id) {
        return sysUserRoleMapper.selectById(id);
    }

    /**
     * 分页查询用户和角色关联
     * @param pageReqVO 分页查询参数
     * @return 用户和角色关联分页对象
     */
    @Override
    public PageResult<SysUserRole> getSysUserRolePage(SysUserRolePageReqVO pageReqVO) {
        return sysUserRoleMapper.selectPage(pageReqVO);
    }
}




