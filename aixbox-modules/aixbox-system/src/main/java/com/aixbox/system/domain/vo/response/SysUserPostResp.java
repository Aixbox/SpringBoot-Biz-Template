package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户岗位关联 返回参数
 */
@Data
public class SysUserPostResp {

    /**
    * id
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 岗位ID
    */
    private Long postId;
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
