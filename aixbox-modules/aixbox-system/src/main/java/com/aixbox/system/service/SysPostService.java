package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.vo.request.SysPostPageReqVO;
import com.aixbox.system.domain.vo.request.SysPostSaveReqVO;
import com.aixbox.system.domain.vo.request.SysPostUpdateReqVO;
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
    Long addSysPost(SysPostSaveReqVO addReqVO);

    /**
     * 修改岗位
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateSysPost(SysPostUpdateReqVO updateReqVO);

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
}
