package com.aixbox.system.domain.vo.request.post;

import com.aixbox.system.domain.entity.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 岗位 更新参数
 */
@Data
@AutoMapper(target = SysPost.class)
public class SysPostUpdateReq {

    /**
    * 岗位ID
    */
    @NotNull
    private Long id;
    /**
    * 部门id
    */
    @NotNull(message = "部门id不能为空")
    private Long deptId;
    /**
    * 岗位编码
    */
    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 0, max = 64, message = "岗位编码长度不能超过{max}个字符")
    private String postCode;
    /**
    * 岗位类别编码
    */
    @Size(min = 0, max = 100, message = "类别编码长度不能超过{max}个字符")
    private String postCategory;
    /**
    * 岗位名称
    */
    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 0, max = 50, message = "岗位名称长度不能超过{max}个字符")
    private String postName;
    /**
    * 显示顺序
    */
    @NotNull(message = "显示顺序不能为空")
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
