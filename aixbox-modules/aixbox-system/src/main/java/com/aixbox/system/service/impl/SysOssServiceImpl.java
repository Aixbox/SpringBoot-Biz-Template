package com.aixbox.system.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;
import com.aixbox.system.domain.vo.request.SysOssSaveReq;
import com.aixbox.system.domain.vo.request.SysOssUpdateReq;
import com.aixbox.system.mapper.SysOssMapper;
import com.aixbox.system.service.SysOssService;

import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.SYS_OSS_NOT_EXISTS;

/**
* OSS对象存储Service实现类
*/
@RequiredArgsConstructor
@Service
public class SysOssServiceImpl implements SysOssService {

    private final SysOssMapper sysOssMapper;

    /**
     * 新增OSS对象存储
     * @param addReq 新增参数
     * @return OSS对象存储id
     */
    @Override
    public Long addSysOss(SysOssSaveReq addReq) {
        SysOss  sysOss = BeanUtils.toBean(addReq, SysOss.class);
        validEntityBeforeSave(sysOss);
        sysOssMapper.insert(sysOss);
        return sysOss.getId();
    }

    /**
     * 修改OSS对象存储
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysOss(SysOssUpdateReq updateReq) {
        SysOss sysOss = BeanUtils.toBean(updateReq, SysOss.class);
        validEntityBeforeSave(sysOss);
        return sysOssMapper.updateById(sysOss) > 0;
    }

    /**
     * 删除OSS对象存储
     * @param ids OSS对象存储id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysOss(List<Long> ids) {
        validateSysOssExists(ids);
        return sysOssMapper.deleteByIds(ids) > 0;
    }

    /**
     * 验证OSS对象存储是否存在
     * @param ids OSS对象存储id数组
     */
    private void validateSysOssExists(List<Long> ids) {
        Long count = sysOssMapper.countByIds(ids);
        if (count != ids.size()) {
            throw exception(SYS_OSS_NOT_EXISTS);
        }
    }

    /**
     * 获取OSS对象存储详细数据
     * @param id OSS对象存储id
     * @return OSS对象存储对象
     */
    @Override
    public SysOss getSysOss(Long id) {
        return sysOssMapper.selectById(id);
    }

    /**
     * 分页查询OSS对象存储
     * @param pageReq 分页查询参数
     * @return OSS对象存储分页对象
     */
    @Override
    public PageResult<SysOss> getSysOssPage(SysOssPageReq pageReq) {
        return sysOssMapper.selectPage(pageReq);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOss entity){
        //TODO 做一些数据校验,如唯一约束
    }
}




