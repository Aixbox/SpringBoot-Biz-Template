package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;
import com.aixbox.system.domain.vo.request.SysOssSaveReq;
import com.aixbox.system.domain.vo.request.SysOssUpdateReq;

import java.util.List;

/**
 * OSS对象存储Service接口
 */
public interface SysOssService {

    /**
     * 新增OSS对象存储
     * @param addReq 新增参数
     * @return OSS对象存储id
     */
     Long addSysOss(SysOssSaveReq addReq);

    /**
     * 修改OSS对象存储
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysOss(SysOssUpdateReq updateReq);

    /**
     * 删除OSS对象存储
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysOss(List<Long> ids);

    /**
     * 获取OSS对象存储详细数据
     * @param id 数据id
     * @return OSS对象存储对象
     */
     SysOss getSysOss(Long id);

    /**
     * 分页查询OSS对象存储
     * @param pageReq 分页查询参数
     * @return OSS对象存储分页对象
     */
    PageResult<SysOss> getSysOssPage(SysOssPageReq pageReq);
}
