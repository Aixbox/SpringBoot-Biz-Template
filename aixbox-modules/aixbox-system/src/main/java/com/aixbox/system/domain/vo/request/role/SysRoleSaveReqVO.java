package com.aixbox.system.domain.vo.request.role;


import lombok.Data;

/**
 * 角色 新增参数
 */
@Data
public class SysRoleSaveReqVO {

    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色权限字符串
    */
    private String roleKey;
    /**
    * 显示顺序
    */
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
