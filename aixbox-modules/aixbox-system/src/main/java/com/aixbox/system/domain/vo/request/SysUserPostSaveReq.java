package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 用户岗位关联 新增参数
 */
@Data
public class SysUserPostSaveReq {

            /**
    * 用户ID
    */
    private Long userId;
    /**
    * 岗位ID
    */
    private Long postId;
                                        
}
