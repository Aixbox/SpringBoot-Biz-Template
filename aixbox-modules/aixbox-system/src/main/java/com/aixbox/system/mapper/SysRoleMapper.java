package com.aixbox.system.mapper;

import com.aixbox.common.datePermission.annotation.DataColumn;
import com.aixbox.common.datePermission.annotation.DataPermission;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.system.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 角色 Mapper接口
*/
@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);


    /**
     * 根据条件分页查询角色数据
     *
     * @param queryWrapper 查询条件
     * @return 角色数据集合信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "r.create_by")
    })
    List<SysRole> selectRoleList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    /**
     * 分页查询角色列表
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return 包含角色信息的分页结果
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "r.create_by")
    })
    Page<SysRole> selectPageRoleList(@Param("page") Page<SysRole> page, @Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);
}




