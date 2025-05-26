package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.common.mybatis.core.util.MyBatisUtils;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

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
}




