package com.aixbox.system.constant;


import com.aixbox.common.core.constant.GlobalConstants;

/**
 * 缓存组名称常量
 * <p>
 * key 格式为 cacheNames#ttl#maxIdleTime#maxSize
 * <p>
 * ttl 过期时间 如果设置为0则不过期 默认为0
 * maxIdleTime 最大空闲时间 根据LRU算法清理空闲数据 如果设置为0则不检测 默认为0
 * maxSize 组最大长度 根据LRU算法清理溢出数据 如果设置为0则无限长 默认为0
 * <p>
 * 例子: test#60s、test#0#60s、test#0#1m#1000、test#1h#0#500
 *
 * @author Lion Li
 */
public interface CacheNames {

    /**
     * 客户端
     */
    String SYS_CLIENT = GlobalConstants.GLOBAL_REDIS_KEY + "sys_client#30d";

    /**
     * 部门
     */
    String SYS_DEPT = "sys_dept#30d";

    /**
     * 数据字典
     */
    String SYS_DICT = "sys_dict";

    /**
     * 数据字典类型
     */
    String SYS_DICT_TYPE = "sys_dict_type";

    /**
     * 用户名称
     */
    String SYS_NICKNAME = "sys_nickname#30d";

    /**
     * 部门及以下权限
     */
    String SYS_DEPT_AND_CHILD = "sys_dept_and_child#30d";

    /**
     * 系统配置
     */
    String SYS_CONFIG = "sys_config";

}
