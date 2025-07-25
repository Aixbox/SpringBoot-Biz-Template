package com.aixbox.system.mapper;

import com.aixbox.common.datePermission.annotation.DataColumn;
import com.aixbox.common.datePermission.annotation.DataPermission;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 用户 Mapper接口
*/
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUser> {

    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param queryWrapper 查询条件
     * @return 用户信息集合信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "u.user_id")
    })
    Page<SysUser> selectAllocatedList(@Param("page") Page<SysUser> page,
                                      @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);


    /**
     * 分页查询用户列表，并进行数据权限控制
     *
     * @param page         分页参数
     * @param queryWrapper 查询条件
     * @return 分页的用户信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "u.dept_id"),
            @DataColumn(key = "userName", value = "u.user_id")
    })
    Page<SysUser> selectPageUserList(@Param("page") Page<SysUser> page,
                                  @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

    /**
     * 根据条件分页查询用户列表
     *
     * @param queryWrapper 查询条件
     * @return 用户信息集合信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "u.user_id")
    })
    List<SysUserResp> selectUserExportList(@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param queryWrapper 查询条件
     * @return 用户信息集合信息
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "u.user_id")
    })
    Page<SysUser> selectUnallocatedList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

    /**
     * 根据用户ID统计用户数量
     *
     * @param userId 用户ID
     * @return 用户数量
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "dept_id"),
            @DataColumn(key = "userName", value = "user_id")
    })
    long countUserById(Long userId);
}




