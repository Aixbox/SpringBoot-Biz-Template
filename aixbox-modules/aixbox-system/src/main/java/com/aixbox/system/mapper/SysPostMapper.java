package com.aixbox.system.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.datePermission.annotation.DataColumn;
import com.aixbox.common.datePermission.annotation.DataPermission;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.post.SysPostPageReqVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 岗位 Mapper接口
*/
@Mapper
public interface SysPostMapper extends BaseMapperX<SysPost> {

    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return demo分页对象
    */
    default PageResult<SysPost> selectPage(SysPostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysPost>()
                .likeIfPresent(SysPost::getPostCode, reqVO.getKeyword())
                .likeIfPresent(SysPost::getPostCategory, reqVO.getKeyword())
                .likeIfPresent(SysPost::getPostName, reqVO.getKeyword())
                .likeIfPresent(SysPost::getStatus, reqVO.getKeyword())
                .likeIfPresent(SysPost::getRemark, reqVO.getKeyword())
                .orderByDesc(BaseDO::getCreateTime));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<SysPost> selectPostsByUserId(Long userId);

    /**
     * 分页查询岗位列表
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return 包含岗位信息的分页结果
     */
    @DataPermission({
            @DataColumn(key = "deptName", value = "dept_id"),
            @DataColumn(key = "userName", value = "create_by")
    })
    Page<SysPost> selectPagePostList(@Param("page") Page<SysPost> page, @Param(Constants.WRAPPER) Wrapper<SysPost> queryWrapper);
}




