package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysMenu;
import com.aixbox.system.domain.vo.request.SysMenuPageReqVO;
import com.aixbox.system.domain.vo.request.SysMenuSaveReqVO;
import com.aixbox.system.domain.vo.request.SysMenuUpdateReqVO;
import com.aixbox.system.mapper.SysMenuMapper;
import com.aixbox.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 菜单 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 新增菜单
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysMenu(SysMenuSaveReqVO addReqVO) {
        SysMenu sysMenu = BeanUtils.toBean(addReqVO, SysMenu.class);
        sysMenuMapper.insert(sysMenu);
        return sysMenu.getId();
    }

    /**
     * 修改菜单
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysMenu(SysMenuUpdateReqVO updateReqVO) {
        SysMenu sysMenu = MapstructUtils.convert(updateReqVO, SysMenu.class);
        return sysMenuMapper.updateById(sysMenu) > 0;
    }

    /**
     * 删除菜单
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysMenu(List<Long> ids) {
        return sysMenuMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取菜单详细数据
     * @param id 数据id
     * @return 菜单对象
     */
    @Override
    public SysMenu getSysMenu(Long id) {
        return sysMenuMapper.selectById(id);
    }

    /**
     * 分页查询菜单
     * @param pageReqVO 分页查询参数
     * @return 菜单分页对象
     */
    @Override
    public PageResult<SysMenu> getSysMenuPage(SysMenuPageReqVO pageReqVO) {
        return sysMenuMapper.selectPage(pageReqVO);
    }
}




