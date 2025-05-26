package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysRoleDept;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 角色和部门关联 更新参数
 */
@Data
@AutoMapper(target = SysRoleDept.class)
public class SysRoleDeptUpdateReq {

    /**
    * id
    */
    @NotNull
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 部门ID
    */
    private Long deptId;
                                        
}
