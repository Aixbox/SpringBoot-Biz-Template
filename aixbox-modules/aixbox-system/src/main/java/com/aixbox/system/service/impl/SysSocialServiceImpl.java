package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.system.domain.entity.SysSocial;
import com.aixbox.system.domain.vo.request.user.SysSocialPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysSocialSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysSocialUpdateReqVO;
import com.aixbox.system.mapper.SysSocialMapper;
import com.aixbox.system.service.SysSocialService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 社交用户 Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysSocialServiceImpl implements SysSocialService {

    private final SysSocialMapper sysSocialMapper;

    /**
     * 新增社交用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addSysSocial(SysSocialSaveReqVO addReqVO) {
        SysSocial sysSocial = BeanUtils.toBean(addReqVO, SysSocial.class);
        sysSocialMapper.insert(sysSocial);
        return sysSocial.getId();
    }

    /**
     * 修改社交用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysSocial(SysSocialUpdateReqVO updateReqVO) {
        SysSocial sysSocial = MapstructUtils.convert(updateReqVO, SysSocial.class);
        return sysSocialMapper.updateById(sysSocial) > 0;
    }

    /**
     * 删除社交用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysSocial(List<Long> ids) {
        return sysSocialMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取社交用户详细数据
     * @param id 数据id
     * @return 社交用户对象
     */
    @Override
    public SysSocial getSysSocial(Long id) {
        return sysSocialMapper.selectById(id);
    }

    /**
     * 分页查询社交用户
     * @param pageReqVO 分页查询参数
     * @return 社交用户分页对象
     */
    @Override
    public PageResult<SysSocial> getSysSocialPage(SysSocialPageReqVO pageReqVO) {
        return sysSocialMapper.selectPage(pageReqVO);
    }

    /**
     * 根据 authId 查询用户信息
     *
     * @param authId 认证id
     * @return 授权信息
     */
    @Override
    public List<SysSocial> selectByAuthId(String authId) {
        return sysSocialMapper.selectList(new LambdaQueryWrapper<SysSocial>().eq(SysSocial::getAuthId, authId));
    }

    /**
     * 授权列表
     */
    @Override
    public List<SysSocial> queryList(SysSocial bo) {
        LambdaQueryWrapper<SysSocial> lqw = new LambdaQueryWrapper<SysSocial>()
                .eq(ObjectUtil.isNotNull(bo.getUserId()), SysSocial::getUserId, bo.getUserId())
                .eq(StrUtils.isNotBlank(bo.getAuthId()), SysSocial::getAuthId, bo.getAuthId())
                .eq(StrUtils.isNotBlank(bo.getSource()), SysSocial::getSource, bo.getSource());
        return sysSocialMapper.selectList(lqw);
    }

    @Override
    public Boolean insertByBo(SysSocial bo) {
        validEntityBeforeSave(bo);
        return sysSocialMapper.insert(bo) > 0;
    }

    /**
     * 更新社会化关系
     */
    @Override
    public Boolean updateByBo(SysSocial bo) {
        SysSocial update = MapstructUtils.convert(bo, SysSocial.class);
        validEntityBeforeSave(update);
        return sysSocialMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysSocial entity) {
        //TODO 做一些数据校验,如唯一约束
    }
}




