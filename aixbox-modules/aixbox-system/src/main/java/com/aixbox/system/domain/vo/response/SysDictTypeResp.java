package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型 返回参数
 */
@Data
public class SysDictTypeResp {

    /**
    * 字典主键
    */
    private Long id;
    /**
    * 字典名称
    */
    private String dictName;
    /**
    * 字典类型
    */
    private String dictType;
    /**
    * 创建时间
    */
    private LocalDateTime createTime;
    /**
    * 备注
    */
    private String remark;


}
