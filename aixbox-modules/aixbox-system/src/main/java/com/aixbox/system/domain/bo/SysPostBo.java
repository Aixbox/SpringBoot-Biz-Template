package com.aixbox.system.domain.bo;


import lombok.Data;

/**
 *
 */
@Data
public class SysPostBo {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位类别编码
     */
    private String postCategory;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 归属部门id（部门树）
     */
    private Long belongDeptId;

}
