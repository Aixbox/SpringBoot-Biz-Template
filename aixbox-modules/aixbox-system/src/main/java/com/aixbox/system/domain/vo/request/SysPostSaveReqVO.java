package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 岗位 新增参数
 */
@Data
public class SysPostSaveReqVO {

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
    * 显示顺序
    */
    private Long postSort;
    /**
    * 状态（0正常 1停用）
    */
    private String status;
                                            /**
    * 备注
    */
    private String remark;

}
