package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysRoleMenu;
import com.aixbox.system.domain.vo.request.SysRoleMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysRoleMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysRoleMenuUpdateReqVO;
import com.aixbox.system.mapper.SysRoleMenuMapper;
import com.aixbox.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 角色和菜单关联 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 新增角色和菜单关联
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysRoleMenu(SysRoleMenuSaveReqVO addReqVO) {
        SysRoleMenu sysRoleMenu = BeanUtils.toBean(addReqVO, SysRoleMenu.class);
        sysRoleMenuMapper.insert(sysRoleMenu);
        return sysRoleMenu.getId();
    }

    /**
     * 修改角色和菜单关联
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysRoleMenu(SysRoleMenuUpdateReqVO updateReqVO) {
        SysRoleMenu sysRoleMenu = MapstructUtils.convert(updateReqVO, SysRoleMenu.class);
        return sysRoleMenuMapper.updateById(sysRoleMenu) > 0;
    }

    /**
     * 删除角色和菜单关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysRoleMenu(List<Long> ids) {
        return sysRoleMenuMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取角色和菜单关联详细数据
     * @param id 数据id
     * @return 角色和菜单关联对象
     */
    @Override
    public SysRoleMenu getSysRoleMenu(Long id) {
        return sysRoleMenuMapper.selectById(id);
    }

    /**
     * 分页查询角色和菜单关联
     * @param pageReqVO 分页查询参数
     * @return 角色和菜单关联分页对象
     */
    @Override
    public PageResult<SysRoleMenu> getSysRoleMenuPage(SysRoleMenuPageReqVO pageReqVO) {
        return sysRoleMenuMapper.selectPage(pageReqVO);
    }
}




