package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户岗位关联对象
 */
@TableName(value ="sys_user_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPost extends BaseDO {

    /**
    * id
    */
    @TableId(value = "id")
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