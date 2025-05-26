package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysRoleDept;
import com.aixbox.system.domain.vo.request.SysRoleDeptPageReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptSaveReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptUpdateReq;
import com.aixbox.system.mapper.SysRoleDeptMapper;
import com.aixbox.system.service.SysRoleDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 角色和部门关联 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysRoleDeptServiceImpl implements SysRoleDeptService {

    private final SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 新增角色和部门关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysRoleDept(SysRoleDeptSaveReq addReq) {
        SysRoleDept sysRoleDept = BeanUtils.toBean(addReq, SysRoleDept.class);
        sysRoleDeptMapper.insert(sysRoleDept);
        return sysRoleDept.getId();
    }

    /**
     * 修改角色和部门关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysRoleDept(SysRoleDeptUpdateReq updateReq) {
        SysRoleDept sysRoleDept = MapstructUtils.convert(updateReq, SysRoleDept.class);
        return sysRoleDeptMapper.updateById(sysRoleDept) > 0;
    }

    /**
     * 删除角色和部门关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysRoleDept(List<Long> ids) {
        return sysRoleDeptMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取角色和部门关联详细数据
     * @param id 数据id
     * @return 角色和部门关联对象
     */
    @Override
    public SysRoleDept getSysRoleDept(Long id) {
        return sysRoleDeptMapper.selectById(id);
    }

    /**
     * 分页查询角色和部门关联
     * @param pageReq 分页查询参数
     * @return 角色和部门关联分页对象
     */
    @Override
    public PageResult<SysRoleDept> getSysRoleDeptPage(SysRoleDeptPageReq pageReq) {
        return sysRoleDeptMapper.selectPage(pageReq);
    }
}




