package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.object.ObjectUtils;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptRespVO;
import com.aixbox.system.mapper.SysDeptMapper;
import com.aixbox.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 部门 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    /**
     * 新增部门
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysDept(SysDeptSaveReqVO addReqVO) {
        SysDept sysDept = BeanUtils.toBean(addReqVO, SysDept.class);
        sysDeptMapper.insert(sysDept);
        return sysDept.getId();
    }

    /**
     * 修改部门
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysDept(SysDeptUpdateReqVO updateReqVO) {
        SysDept sysDept = MapstructUtils.convert(updateReqVO, SysDept.class);
        return sysDeptMapper.updateById(sysDept) > 0;
    }

    /**
     * 删除部门
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysDept(List<Long> ids) {
        return sysDeptMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取部门详细数据
     * @param id 数据id
     * @return 部门对象
     */
    @Override
    public SysDept getSysDept(Long id) {
        return sysDeptMapper.selectById(id);
    }

    /**
     * 分页查询部门
     * @param pageReqVO 分页查询参数
     * @return 部门分页对象
     */
    @Override
    public PageResult<SysDept> getSysDeptPage(SysDeptPageReqVO pageReqVO) {
        return sysDeptMapper.selectPage(pageReqVO);
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public SysDeptRespVO selectDeptById(Long deptId) {
        SysDept dept = sysDeptMapper.selectById(deptId);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysDept parentDept = sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getDeptName).eq(SysDept::getId, dept.getParentId()));

        SysDeptRespVO respVO = BeanUtils.toBean(dept, SysDeptRespVO.class);
        respVO.setParentName(ObjectUtils.notNullGetter(parentDept, SysDept::getDeptName));
        return respVO;
    }
}




