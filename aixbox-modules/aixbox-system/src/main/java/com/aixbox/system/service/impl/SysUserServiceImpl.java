package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.SysUserUpdateReqVO;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysUserService;
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
}




