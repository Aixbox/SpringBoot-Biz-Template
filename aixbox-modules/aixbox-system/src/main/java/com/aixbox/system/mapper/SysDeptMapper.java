package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.datePermission.annotation.DataColumn;
import com.aixbox.common.datePermission.annotation.DataPermission;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.common.mybatis.core.util.MyBatisUtils;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;
import java.util.List;

/**
* 部门 Mapper接口
*/
@Mapper
public interface SysDeptMapper extends BaseMapperX<SysDept> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysDept> selectPage(SysDeptPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysDept>()
                .likeIfPresent(SysDept::getAncestors, reqVO.getKeyword())
                .likeIfPresent(SysDept::getDeptName, reqVO.getKeyword())
                .likeIfPresent(SysDept::getDeptCategory, reqVO.getKeyword())
                .likeIfPresent(SysDept::getPhone, reqVO.getKeyword())
                .likeIfPresent(SysDept::getEmail, reqVO.getKeyword())
                .likeIfPresent(SysDept::getStatus, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

    /**
     * 根据父部门ID查询其所有子部门的列表
     *
     * @param deptId 父部门ID
     * @return 包含子部门的列表
     */
    default List<SysDept> selectListByParentId(Long deptId) {
        return this.selectList(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getId)
                .apply(MyBatisUtils.findInSet("ancestors", deptId)));
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    List<Long> selectDeptListByRoleId(Long roleId, Long deptCheckStrictly);

    /**
     * 查询部门管理数据
     *
     * @param queryWrapper 查询条件
     * @return 部门信息集合
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "dept_id")
    })
    List<SysDept> selectDeptList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);

    /**
     * 统计指定部门ID的部门数量
     *
     * @param deptId 部门ID
     * @return 该部门ID的部门数量
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "dept_id")
    })
    long countDeptById(Long deptId);


}




