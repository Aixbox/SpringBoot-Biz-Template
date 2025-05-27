package com.aixbox.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptResp;

import java.util.List;

/**
* 部门 Service接口
*/
public interface SysDeptService {

    /**
     * 新增部门
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysDept(SysDeptSaveReqVO addReqVO);

    /**
     * 修改部门
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysDept(SysDeptUpdateReqVO updateReqVO);

    /**
     * 删除部门
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysDept(List<Long> ids);

    /**
     * 获取部门详细数据
     * @param id 数据id
     * @return 部门对象
     */
    SysDeptResp getSysDept(Long id);

    /**
     * 分页查询部门
     * @param pageReqVO 分页查询参数
     * @return 部门分页对象
     */
    PageResult<SysDept> getSysDeptPage(SysDeptPageReqVO pageReqVO);

    SysDeptResp selectDeptById(Long deptId);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    List<Long> selectDeptListByRoleId(Long roleId);

    /**
     * 查询部门树结构信息
     *
     * @param sysDeptBo 部门信息
     * @return 部门树信息集合
     */
    List<Tree<Long>> selectDeptTreeList(SysDeptBo sysDeptBo);

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    void checkDeptDataScope(Long deptId);

    /**
     * 查询部门管理数据
     *
     * @param sysDeptBo 部门信息
     * @return 部门信息集合
     */
    List<SysDeptResp> selectDeptList(SysDeptBo sysDeptBo);

    /**
     * 校验部门名称是否唯一
     *
     * @param sysDept 部门信息
     * @return 结果
     */
    boolean checkDeptNameUnique(SysDeptBo sysDept);

    /**
     * 新增保存部门信息
     *
     * @param sysDept 部门信息
     * @return 结果
     */
    int insertDept(SysDept sysDept);

    /**
     * 根据ID查询所有子部门数（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    long selectNormalChildrenDeptById(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDeptBo dept);
}
