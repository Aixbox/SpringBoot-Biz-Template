package com.aixbox.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.TreeBuildUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.object.ObjectUtils;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.constant.CacheNames;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.entity.SysDept;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.vo.request.dept.SysDeptPageReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptSaveReqVO;
import com.aixbox.system.domain.vo.request.dept.SysDeptUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysDeptResp;
import com.aixbox.system.mapper.SysDeptMapper;
import com.aixbox.system.mapper.SysRoleMapper;
import com.aixbox.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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




