package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptRespVO;
import org.springframework.cache.annotation.Cacheable;

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
    SysDept getSysDept(Long id);

    /**
     * 分页查询部门
     * @param pageReqVO 分页查询参数
     * @return 部门分页对象
     */
    PageResult<SysDept> getSysDeptPage(SysDeptPageReqVO pageReqVO);

    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    SysDeptRespVO selectDeptById(Long deptId);
}
