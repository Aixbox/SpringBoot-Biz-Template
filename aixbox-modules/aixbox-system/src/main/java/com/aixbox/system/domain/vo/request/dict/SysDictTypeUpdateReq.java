package com.aixbox.system.domain.vo.request.dict;

import com.aixbox.system.domain.entity.SysDictType;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 字典类型 更新参数
 */
@Data
@AutoMapper(target = SysDictType.class)
public class SysDictTypeUpdateReq {

    /**
    * 字典主键
    */
    @NotNull
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
    * 备注
    */
    private String remark;

}
