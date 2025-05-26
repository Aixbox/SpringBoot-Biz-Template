package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.vo.request.SysUserPostPageReq;
import com.aixbox.system.domain.vo.request.SysUserPostSaveReq;
import com.aixbox.system.domain.vo.request.SysUserPostUpdateReq;
import com.aixbox.system.mapper.SysUserPostMapper;
import com.aixbox.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 用户岗位关联 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysUserPostServiceImpl implements SysUserPostService {

    private final SysUserPostMapper sysUserPostMapper;

    /**
     * 新增用户岗位关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysUserPost(SysUserPostSaveReq addReq) {
        SysUserPost sysUserPost = BeanUtils.toBean(addReq, SysUserPost.class);
        sysUserPostMapper.insert(sysUserPost);
        return sysUserPost.getId();
    }

    /**
     * 修改用户岗位关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysUserPost(SysUserPostUpdateReq updateReq) {
        SysUserPost sysUserPost = MapstructUtils.convert(updateReq, SysUserPost.class);
        return sysUserPostMapper.updateById(sysUserPost) > 0;
    }

    /**
     * 删除用户岗位关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysUserPost(List<Long> ids) {
        return sysUserPostMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取用户岗位关联详细数据
     * @param id 数据id
     * @return 用户岗位关联对象
     */
    @Override
    public SysUserPost getSysUserPost(Long id) {
        return sysUserPostMapper.selectById(id);
    }

    /**
     * 分页查询用户岗位关联
     * @param pageReq 分页查询参数
     * @return 用户岗位关联分页对象
     */
    @Override
    public PageResult<SysUserPost> getSysUserPostPage(SysUserPostPageReq pageReq) {
        return sysUserPostMapper.selectPage(pageReq);
    }
}




