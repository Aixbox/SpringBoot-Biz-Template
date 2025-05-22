package com.aixbox.system.domain.vo.request.post;

import com.aixbox.system.domain.entity.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 岗位 更新参数
 */
@Data
@AutoMapper(target = SysPost.class)
public class SysPostUpdateReqVO {

    /**
    * 岗位ID
    */
    @NotNull
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
    * 备注
    */
    private String remark;

}
