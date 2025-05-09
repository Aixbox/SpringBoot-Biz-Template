package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 用户和角色关联 新增参数
 */
@Data
public class SysUserRoleSaveReqVO {

            /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;
                                        
}
