package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 角色和部门关联 新增参数
 */
@Data
public class SysRoleDeptSaveReq {

            /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 部门ID
    */
    private Long deptId;
                                        
}
