package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysPostBo;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.post.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostSaveReq;
import com.aixbox.system.domain.vo.request.post.SysPostUpdateReq;
import com.aixbox.system.domain.vo.response.SysPostResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 岗位 Service接口
*/
public interface SysPostService {

    /**
     * 新增岗位
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addSysPost(SysPostSaveReq addReqVO);

    /**
     * 修改岗位
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysPost(SysPostUpdateReq updateReqVO);

    /**
     * 删除岗位
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysPost(List<Long> ids);

    /**
     * 获取岗位详细数据
     * @param id 数据id
     * @return 岗位对象
     */
    SysPost getSysPost(Long id);

    /**
     * 分页查询岗位
     * @param pageReqVO 分页查询参数
     * @return 岗位分页对象
     */
    PageResult<SysPost> getSysPostPage(SysPostPageReqVO pageReqVO);

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 岗位ID
     */
    List<SysPost> selectPostsByUserId(@Param("userId") Long userId);

    /**
     * 查询岗位信息集合
     *
     * @param postBo 岗位信息
     * @return 岗位列表
     */
    List<SysPostResp> selectPostList(SysPostBo postBo);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * 通过部门ID查询岗位使用数量
     *
     * @param deptId 部门id
     * @return 结果
     */
    long countPostByDeptId(Long deptId);

    /**
     * 校验岗位名称
     *
     * @param postBo 岗位信息
     * @return 结果
     */
    boolean checkPostNameUnique(SysPostBo postBo);

    /**
     * 校验岗位编码
     *
     * @param postBo 岗位信息
     * @return 结果
     */
    boolean checkPostCodeUnique(SysPostBo postBo);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    long countUserPostById(Long postId);

    /**
     * 通过岗位ID串查询岗位
     *
     * @param postIds 岗位id串
     * @return 岗位列表信息
     */
    List<SysPostResp> selectPostByIds(List<Long> postIds);
}
