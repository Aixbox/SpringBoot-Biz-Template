package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位 返回参数
 */
@Data
public class SysPostRespVO {

    /**
    * 岗位ID
    */
    private Long id;
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
    * 创建时间
    */
    private LocalDateTime createTime;
                            /**
    * 备注
    */
    private String remark;


}
