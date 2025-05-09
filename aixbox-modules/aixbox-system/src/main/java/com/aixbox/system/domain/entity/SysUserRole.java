package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联对象
 */
@TableName(value ="sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseDO {

    /**
    * 自增编号
    */
    @TableId(value = "id")
    private Long id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 角色ID
    */
    private Long roleId;


}