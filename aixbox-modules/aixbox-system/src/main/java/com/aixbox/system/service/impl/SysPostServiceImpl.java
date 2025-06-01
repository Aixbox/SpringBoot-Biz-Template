package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.system.domain.bo.SysPostBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.vo.request.post.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostSaveReqVO;
import com.aixbox.system.domain.vo.request.post.SysPostUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysPostResp;
import com.aixbox.system.mapper.SysDeptMapper;
import com.aixbox.system.mapper.SysPostMapper;
import com.aixbox.system.mapper.SysUserPostMapper;
import com.aixbox.system.service.SysPostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* 岗位 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl implements SysPostService {

    private final SysPostMapper sysPostMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysUserPostMapper userPostMapper;

    /**
     * 新增岗位
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysPost(SysPostSaveReqVO addReqVO) {
        SysPost sysPost = BeanUtils.toBean(addReqVO, SysPost.class);
        sysPostMapper.insert(sysPost);
        return sysPost.getId();
    }

    /**
     * 修改岗位
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysPost(SysPostUpdateReqVO updateReqVO) {
        SysPost sysPost = MapstructUtils.convert(updateReqVO, SysPost.class);
        return sysPostMapper.updateById(sysPost) > 0;
    }

    /**
     * 删除岗位
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysPost(List<Long> ids) {
        for (Long postId : ids) {
            SysPost post = sysPostMapper.selectById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配，不能删除!", post.getPostName()));
            }
        }
        return sysPostMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取岗位详细数据
     * @param id 数据id
     * @return 岗位对象
     */
    @Override
    public SysPost getSysPost(Long id) {
        return sysPostMapper.selectById(id);
    }

    /**
     * 分页查询岗位
     * @param pageReqVO 分页查询参数
     * @return 岗位分页对象
     */
    @Override
    public PageResult<SysPost> getSysPostPage(SysPostPageReqVO pageReqVO) {
        SysPostBo postBo = BeanUtils.toBean(pageReqVO, SysPostBo.class);
        Page<SysPost> page = sysPostMapper.selectPagePostList(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()),
                buildQueryWrapper(postBo));
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 岗位ID
     */
    @Override
    public List<SysPost> selectPostsByUserId(Long userId) {
        return sysPostMapper.selectPostsByUserId(userId);
    }

    /**
     * 查询岗位信息集合
     *
     * @param postBo 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPostResp> selectPostList(SysPostBo postBo) {
        List<SysPost> sysPosts = sysPostMapper.selectList(buildQueryWrapper(postBo));
        return BeanUtils.toBean(sysPosts, SysPostResp.class);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        List<SysPost> list = sysPostMapper.selectPostsByUserId(userId);
        return StreamUtils.toList(list, SysPost::getId);
    }

    /**
     * 通过部门ID查询岗位使用数量
     *
     * @param deptId 部门id
     * @return 结果
     */
    @Override
    public long countPostByDeptId(Long deptId) {
        return sysPostMapper.selectCount(new LambdaQueryWrapper<SysPost>().eq(SysPost::getDeptId, deptId));
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPostBo post) {
        boolean exist = sysPostMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostName, post.getPostName())
                .eq(SysPost::getDeptId, post.getDeptId())
                .ne(ObjectUtil.isNotNull(post.getId()), SysPost::getId, post.getId()));
        return !exist;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPostBo post) {
        boolean exist = sysPostMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostCode, post.getPostCode())
                .ne(ObjectUtil.isNotNull(post.getId()), SysPost::getId, post.getId()));
        return !exist;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public long countUserPostById(Long postId) {
        return userPostMapper.selectCount(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getPostId, postId));
    }

    /**
     * 通过岗位ID串查询岗位
     *
     * @param postIds 岗位id串
     * @return 岗位列表信息
     */
    @Override
    public List<SysPostResp> selectPostByIds(List<Long> postIds) {
        List<SysPost> sysPosts = sysPostMapper.selectList(new LambdaQueryWrapper<SysPost>()
                .select(SysPost::getId, SysPost::getPostName, SysPost::getPostCode)
                .eq(SysPost::getStatus, SystemConstants.NORMAL)
                .in(CollUtil.isNotEmpty(postIds), SysPost::getId, postIds));
        return BeanUtils.toBean(sysPosts, SysPostResp.class);
    }

    /**
     * 根据查询条件构建查询包装器
     *
     * @param bo 查询条件对象
     * @return 构建好的查询包装器
     */
    private LambdaQueryWrapper<SysPost> buildQueryWrapper(SysPostBo bo) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtils.isNotBlank(bo.getPostCode()), SysPost::getPostCode, bo.getPostCode())
               .like(StrUtils.isNotBlank(bo.getPostCategory()), SysPost::getPostCategory, bo.getPostCategory())
               .like(StrUtils.isNotBlank(bo.getPostName()), SysPost::getPostName, bo.getPostName())
               .eq(StrUtils.isNotBlank(bo.getStatus()), SysPost::getStatus, bo.getStatus())
                .eq(BaseDO::getDeleted, 0)
               .orderByAsc(SysPost::getPostSort);
        if (ObjectUtil.isNotNull(bo.getDeptId())) {
            //优先单部门搜索
            wrapper.eq(SysPost::getDeptId, bo.getDeptId());
        } else if (ObjectUtil.isNotNull(bo.getBelongDeptId())) {
            //部门树搜索
            wrapper.and(x -> {
                List<SysDept> deptList = sysDeptMapper.selectListByParentId(bo.getBelongDeptId());
                List<Long> deptIds = StreamUtils.toList(deptList, SysDept::getId);
                deptIds.add(bo.getBelongDeptId());
                x.in(SysPost::getDeptId, deptIds);
            });
        }
        return wrapper;
    }

}




