package com.aixbox.system.domain.vo.request;

import com.aixbox.system.domain.entity.SysUserPost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 用户岗位关联 更新参数
 */
@Data
@AutoMapper(target = SysUserPost.class)
public class SysUserPostUpdateReq {

    /**
    * id
    */
    @NotNull
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 岗位ID
    */
    private Long postId;
                                        
}
