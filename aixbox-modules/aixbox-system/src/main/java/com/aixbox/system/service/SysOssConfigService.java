package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.entity.SysOssConfig;
import com.aixbox.system.domain.vo.request.SysOssConfigPageReq;
import com.aixbox.system.domain.vo.request.SysOssConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysOssConfigUpdateReq;

import java.util.List;

/**
 * 对象存储配置Service接口
 */
public interface SysOssConfigService {

    /**
     * 新增对象存储配置
     * @param addReq 新增参数
     * @return 对象存储配置id
     */
     Long addSysOssConfig(SysOssConfigSaveReq addReq);

    /**
     * 修改对象存储配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysOssConfig(SysOssConfigUpdateReq updateReq);

    /**
     * 删除对象存储配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysOssConfig(List<Long> ids);

    /**
     * 获取对象存储配置详细数据
     * @param id 数据id
     * @return 对象存储配置对象
     */
     SysOssConfig getSysOssConfig(Long id);

    /**
     * 分页查询对象存储配置
     * @param pageReq 分页查询参数
     * @return 对象存储配置分页对象
     */
    PageResult<SysOssConfig> getSysOssConfigPage(SysOssConfigPageReq pageReq);

    /**
     * 启用停用状态
     */
    int updateOssConfigStatus(SysOssConfigUpdateReq bo);
}
