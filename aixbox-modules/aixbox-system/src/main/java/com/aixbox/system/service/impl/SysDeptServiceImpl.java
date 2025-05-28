package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.TreeBuildUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.object.ObjectUtils;
import com.aixbox.common.mybatis.core.util.MyBatisUtils;
import com.aixbox.common.redis.utils.CacheUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptResp;
import com.aixbox.system.mapper.SysDeptMapper;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.mapper.SysUserMapper;
import com.aixbox.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.NO_DEPT_PERMISSION;

/**
* 部门 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserMapper sysUserMapper;

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
    public SysDeptResp getSysDept(Long id) {
        SysDept dept = sysDeptMapper.selectById(id);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysDeptResp deptResp = BeanUtils.toBean(dept, SysDeptResp.class);
        SysDept parentDept = sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getDeptName).eq(SysDept::getId, deptResp.getParentId()));
        deptResp.setParentName(ObjectUtils.notNullGetter(parentDept, SysDept::getDeptName));
        return deptResp;
    }

    /**
     * 分页查询部门
     * @param pageReqVO 分页查询参数
     * @return 部门分页对象
     */
    @Override
    public PageResult<SysDept> getSysDeptPage(SysDeptPageReqVO pageReqVO) {
        SysDeptBo sysDeptBo = BeanUtils.toBean(pageReqVO, SysDeptBo.class);
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(sysDeptBo);
        return sysDeptMapper.selectPage(pageReqVO, lqw);
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public SysDeptResp selectDeptById(Long deptId) {
        SysDept dept = sysDeptMapper.selectById(deptId);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysDept parentDept = sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getDeptName).eq(SysDept::getId, dept.getParentId()));

        SysDeptResp respVO = BeanUtils.toBean(dept, SysDeptResp.class);
        respVO.setParentName(ObjectUtils.notNullGetter(parentDept, SysDept::getDeptName));
        return respVO;
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);
        return sysDeptMapper.selectDeptListByRoleId(roleId, role.getDeptCheckStrictly());
    }

    /**
     * 查询部门树结构信息
     *
     * @param sysDeptBo 部门信息
     * @return 部门树信息集合
     */
    @Override
    public List<Tree<Long>> selectDeptTreeList(SysDeptBo sysDeptBo) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(sysDeptBo);
        List<SysDept> depts = sysDeptMapper.selectDeptList(lqw);
        return buildDeptTreeSelect(depts);
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (ObjectUtil.isNull(deptId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        if (sysDeptMapper.countDeptById(deptId) == 0) {
            throw exception(NO_DEPT_PERMISSION);
        }
    }

    /**
     * 查询部门管理数据
     *
     * @param sysDeptBo 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDeptResp> selectDeptList(SysDeptBo sysDeptBo) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(sysDeptBo);
        List<SysDept> sysDepts = sysDeptMapper.selectDeptList(lqw);
        return BeanUtils.toBean(sysDepts, SysDeptResp.class);
    }

    @Override
    public boolean checkDeptNameUnique(SysDeptBo sysDept) {
        boolean exist = sysDeptMapper.exists(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getDeptName, sysDept.getDeptName())
                .eq(SysDept::getParentId, sysDept.getParentId())
                .ne(ObjectUtil.isNotNull(sysDept.getId()), SysDept::getId, sysDept.getId()));
        return !exist;
    }

    /**
     * 新增部门
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT_AND_CHILD, allEntries = true)
    @Override
    public int insertDept(SysDept sysDept) {
        SysDept info = sysDeptMapper.selectById(sysDept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!SystemConstants.NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        sysDept.setAncestors(info.getAncestors() + StrUtils.SEPARATOR + sysDept.getParentId());
        return sysDeptMapper.insert(sysDept);
    }

    @Override
    public long selectNormalChildrenDeptById(Long deptId) {
        return sysDeptMapper.selectCount(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getStatus, SystemConstants.NORMAL)
                .apply(MyBatisUtils.findInSet("ancestors", deptId)));
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return sysUserMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getDeptId, deptId));
    }

    /**
     * 修改保存部门信息
     *
     * @param bo 部门信息
     * @return 结果
     */
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#bo.id"),
            @CacheEvict(cacheNames = CacheNames.SYS_DEPT_AND_CHILD, allEntries = true)
    })
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDept(SysDeptBo bo) {
        SysDept dept = MapstructUtils.convert(bo, SysDept.class);
        SysDept oldDept = sysDeptMapper.selectById(dept.getId());
        if (ObjectUtil.isNull(oldDept)) {
            throw new ServiceException("部门不存在，无法修改");
        }
        if (!oldDept.getParentId().equals(dept.getParentId())) {
            // 如果是新父部门 则校验是否具有新父部门权限 避免越权
            this.checkDeptDataScope(dept.getParentId());
            SysDept newParentDept = sysDeptMapper.selectById(dept.getParentId());
            if (ObjectUtil.isNotNull(newParentDept)) {
                String newAncestors =
                        newParentDept.getAncestors() + StrUtils.SEPARATOR + newParentDept.getId();
                String oldAncestors = oldDept.getAncestors();
                dept.setAncestors(newAncestors);
                updateDeptChildren(dept.getId(), newAncestors, oldAncestors);
            }
        } else {
            dept.setAncestors(oldDept.getAncestors());
        }
        int result = sysDeptMapper.updateById(dept);
        // 如果部门状态为启用，且部门祖级列表不为空，且部门祖级列表不等于根部门祖级列表（如果部门祖级列表不等于根部门祖级列表，则说明存在上级部门）
        if (SystemConstants.NORMAL.equals(dept.getStatus())
                && StrUtils.isNotEmpty(dept.getAncestors())
                && !StrUtils.equals(SystemConstants.ROOT_DEPT_ANCESTORS, dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        return sysDeptMapper.exists(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, deptId));
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#deptId"),
            @CacheEvict(cacheNames = CacheNames.SYS_DEPT_AND_CHILD, key = "#deptId")
    })
    @Override
    public int deleteDeptById(Long deptId) {
        return sysDeptMapper.deleteById(deptId);
    }

    @Override
    public List<SysDeptResp> selectDeptByIds(List<Long> deptIds) {
        List<SysDept> sysDepts = sysDeptMapper.selectDeptList(new LambdaQueryWrapper<SysDept>()
                .select(SysDept::getId, SysDept::getDeptName, SysDept::getLeader)
                .eq(SysDept::getStatus, SystemConstants.NORMAL)
                .in(CollUtil.isNotEmpty(deptIds), SysDept::getId, deptIds));
        return BeanUtils.toBean(sysDepts, SysDeptResp.class);
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        sysDeptMapper.update(null, new LambdaUpdateWrapper<SysDept>()
                .set(SysDept::getStatus, SystemConstants.NORMAL)
                .in(SysDept::getId, Arrays.asList(deptIds)));
    }


    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = sysDeptMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .apply(MyBatisUtils.findInSet("ancestors", deptId)));
        List<SysDept> list = new ArrayList<>();
        for (SysDept child : children) {
            SysDept dept = new SysDept();
            dept.setId(child.getId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(dept);
        }
        if (CollUtil.isNotEmpty(list)) {
            if (sysDeptMapper.updateBatch(list)) {
                list.forEach(dept -> CacheUtils.evict(CacheNames.SYS_DEPT, dept.getId()));
            }
        }
    }



    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    private List<Tree<Long>> buildDeptTreeSelect(List<SysDept> depts) {
        if (CollUtil.isEmpty(depts)) {
            return CollUtil.newArrayList();
        }
        // 获取当前列表中每一个节点的parentId，然后在列表中查找是否有id与其parentId对应，若无对应，则表明此时节点列表中，该节点在当前列表中属于顶级节点
        List<Tree<Long>> treeList = CollUtil.newArrayList();
        for (SysDept d : depts) {
            Long parentId = d.getParentId();
            SysDept sysDeptVo = StreamUtils.findFirst(depts,
                    it -> it.getId().longValue() == parentId);
            if (ObjectUtil.isNull(sysDeptVo)) {
                List<Tree<Long>> trees = TreeBuildUtils.build(depts, parentId, (dept, tree) ->
                        tree.setId(dept.getId())
                            .setParentId(dept.getParentId())
                            .setName(dept.getDeptName())
                            .setWeight(dept.getOrderNum())
                            .putExtra("disabled", SystemConstants.DISABLE.equals(dept.getStatus())));
                Tree<Long> tree = StreamUtils.findFirst(trees,
                        it -> it.getId().longValue() == d.getId());
                treeList.add(tree);
            }
        }
        return treeList;
    }

    private LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptBo bo) {
        LambdaQueryWrapper<SysDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(SysDept::getDeleted, SystemConstants.NORMAL);
        lqw.eq(ObjectUtil.isNotNull(bo.getId()), SysDept::getId, bo.getId());
        lqw.eq(ObjectUtil.isNotNull(bo.getParentId()), SysDept::getParentId, bo.getParentId());
        lqw.like(StrUtils.isNotBlank(bo.getDeptName()), SysDept::getDeptName, bo.getDeptName());
        lqw.like(StrUtils.isNotBlank(bo.getDeptCategory()), SysDept::getDeptCategory, bo.getDeptCategory());
        lqw.eq(StrUtils.isNotBlank(bo.getStatus()), SysDept::getStatus, bo.getStatus());
        lqw.orderByAsc(SysDept::getAncestors);
        lqw.orderByAsc(SysDept::getParentId);
        lqw.orderByAsc(SysDept::getOrderNum);
        lqw.orderByAsc(SysDept::getId);
        if (ObjectUtil.isNotNull(bo.getBelongDeptId())) {
            //部门树搜索
            lqw.and(x -> {
                Long parentId = bo.getBelongDeptId();
                List<SysDept> deptList = sysDeptMapper.selectListByParentId(parentId);
                List<Long> deptIds = StreamUtils.toList(deptList, SysDept::getId);
                deptIds.add(parentId);
                x.in(SysDept::getId, deptIds);
            });
        }
        return lqw;
    }
}




