package com.aixbox.system.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.system.domain.bo.SysConfigBo;
import com.aixbox.system.domain.entity.SysConfig;
import com.aixbox.system.domain.vo.request.SysConfigPageReq;
import com.aixbox.system.domain.vo.request.SysConfigSaveReq;
import com.aixbox.system.domain.vo.request.SysConfigUpdateReq;
import com.aixbox.system.domain.vo.response.SysConfigResp;

import java.util.List;

/**
* 参数配置 Service接口
*/
public interface SysConfigService {

    /**
     * 新增参数配置
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addSysConfig(SysConfigSaveReq addReq);

    /**
     * 修改参数配置
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateSysConfig(SysConfigBo updateReq);

    /**
     * 删除参数配置
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteSysConfig(List<Long> ids);

    /**
     * 获取参数配置详细数据
     * @param id 数据id
     * @return 参数配置对象
     */
    SysConfig getSysConfig(Long id);

    /**
     * 分页查询参数配置
     * @param pageReq 分页查询参数
     * @return 参数配置分页对象
     */
    PageResult<SysConfig> getSysConfigPage(SysConfigPageReq pageReq);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfigResp> selectConfigList(SysConfigBo config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    boolean checkConfigKeyUnique(SysConfigBo config);

    /**
     * 重置参数缓存数据
     */
    void resetConfigCache();

}
