package com.aixbox.system.domain.vo.request.role;

import com.aixbox.system.domain.entity.SysRole;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 角色 更新参数
 */
@Data
@AutoMapper(target = SysRole.class)
public class SysRoleUpdateReq {

    /**
    * 角色ID
    */
    @NotNull
    private Long id;
    /**
    * 角色名称
    */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;
    /**
    * 角色权限字符串
    */
    @NotBlank(message = "角色权限字符串不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过{max}个字符")
    private String roleKey;
    /**
    * 显示顺序
    */
    @NotNull(message = "显示顺序不能为空")
    private Long roleSort;
    /**
    * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限 6：部门及以下或本人数据权限）
    */
    private String dataScope;
    /**
    * 菜单树选择项是否关联显示
    */
    private Boolean menuCheckStrictly;
    /**
    * 部门树选择项是否关联显示
    */
    private Boolean deptCheckStrictly;
    /**
    * 角色状态（0正常 1停用）
    */
    private String status;
    /**
    * 备注
    */
    private String remark;

}
