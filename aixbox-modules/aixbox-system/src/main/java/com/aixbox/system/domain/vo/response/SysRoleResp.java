package com.aixbox.system.domain.vo.response;

import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.excel.annotation.ExcelDictFormat;
import com.aixbox.common.excel.convert.ExcelDictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色 返回参数
 */
@Data
public class SysRoleResp {

    /**
    * 角色ID
    */
    @ExcelProperty(value = "角色序号")
    private Long id;
    /**
    * 角色名称
    */
    @ExcelProperty(value = "角色名称")
    private String roleName;
    /**
    * 角色权限字符串
    */
    @ExcelProperty(value = "角色权限")
    private String roleKey;
    /**
    * 显示顺序
    */
    @ExcelProperty(value = "角色排序")
    private Long roleSort;
    /**
    * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限 6：部门及以下或本人数据权限）
    */
    @ExcelProperty(value = "数据范围", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=全部数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限,6=部门及以下或本人数据权限")
    private String dataScope;
    /**
    * 菜单树选择项是否关联显示
    */
    @ExcelProperty(value = "菜单树选择项是否关联显示")
    private Boolean menuCheckStrictly;
    /**
    * 部门树选择项是否关联显示
    */
    @ExcelProperty(value = "部门树选择项是否关联显示")
    private Boolean deptCheckStrictly;
    /**
    * 角色状态（0正常 1停用）
    */
    @ExcelProperty(value = "角色状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
    * 创建时间
    */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;


    public boolean isSuperAdmin() {
        return SystemConstants.SUPER_ADMIN_ID.equals(this.id);
    }


}
