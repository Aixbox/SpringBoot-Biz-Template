package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.SysPostSaveReqVO;
import com.aixbox.system.domain.vo.request.SysPostUpdateReqVO;
import com.aixbox.system.mapper.SysPostMapper;
import com.aixbox.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 岗位 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl implements SysPostService {

    private final SysPostMapper sysPostMapper;

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
        return sysPostMapper.selectPage(pageReqVO);
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
}




