package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysUserPost;
import com.aixbox.system.domain.vo.request.SysUserPostPageReq;
import com.aixbox.system.domain.vo.request.SysUserPostSaveReq;
import com.aixbox.system.domain.vo.request.SysUserPostUpdateReq;

import java.util.List;

/**
* 用户岗位关联 Service接口
*/
public interface SysUserPostService {

    /**
     * 新增用户岗位关联
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysUserPost(SysUserPostSaveReq addReq);

    /**
     * 修改用户岗位关联
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysUserPost(SysUserPostUpdateReq updateReq);

    /**
     * 删除用户岗位关联
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysUserPost(List<Long> ids);

    /**
     * 获取用户岗位关联详细数据
     * @param id 数据id
     * @return 用户岗位关联对象
     */
    SysUserPost getSysUserPost(Long id);

    /**
     * 分页查询用户岗位关联
     * @param pageReq 分页查询参数
     * @return 用户岗位关联分页对象
     */
    PageResult<SysUserPost> getSysUserPostPage(SysUserPostPageReq pageReq);
}
