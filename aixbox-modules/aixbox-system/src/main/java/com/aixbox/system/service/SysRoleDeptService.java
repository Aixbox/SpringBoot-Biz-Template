package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysRoleDept;
import com.aixbox.system.domain.vo.request.SysRoleDeptPageReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptSaveReq;
import com.aixbox.system.domain.vo.request.SysRoleDeptUpdateReq;

import java.util.List;

/**
* 角色和部门关联 Service接口
*/
public interface SysRoleDeptService {

    /**
     * 新增角色和部门关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysRoleDept(SysRoleDeptSaveReq addReq);

    /**
     * 修改角色和部门关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysRoleDept(SysRoleDeptUpdateReq updateReq);

    /**
     * 删除角色和部门关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysRoleDept(List<Long> ids);

    /**
     * 获取角色和部门关联详细数据
     * @param id 数据id
     * @return 角色和部门关联对象
     */
    SysRoleDept getSysRoleDept(Long id);

    /**
     * 分页查询角色和部门关联
     * @param pageReq 分页查询参数
     * @return 角色和部门关联分页对象
     */
    PageResult<SysRoleDept> getSysRoleDeptPage(SysRoleDeptPageReq pageReq);
}
