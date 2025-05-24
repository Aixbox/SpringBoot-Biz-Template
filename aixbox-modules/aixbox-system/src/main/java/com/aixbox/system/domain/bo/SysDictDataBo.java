package com.aixbox.system.domain.bo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 */
@Data
public class SysDictDataBo {

    private Long id;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典键值
     */
    private String dictValue;



}
