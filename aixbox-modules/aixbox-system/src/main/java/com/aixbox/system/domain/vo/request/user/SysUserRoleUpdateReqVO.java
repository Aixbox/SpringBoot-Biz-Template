package com.aixbox.system.domain.vo.request.user;

import com.aixbox.system.domain.entity.SysUserRole;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 用户和角色关联 更新参数
 */
@Data
@AutoMapper(target = SysUserRole.class)
public class SysUserRoleUpdateReqVO {

    /**
    * 自增编号
    */
    @NotNull
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;
                                        
}
