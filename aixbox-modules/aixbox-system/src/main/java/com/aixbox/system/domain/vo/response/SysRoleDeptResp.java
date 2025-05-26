package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色和部门关联 返回参数
 */
@Data
public class SysRoleDeptResp {

    /**
    * id
    */
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 部门ID
    */
    private Long deptId;
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
