package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户和角色关联 返回参数
 */
@Data
public class SysUserRoleRespVO {

    /**
    * 自增编号
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
