package com.aixbox.system.domain.vo.request.dict;


import lombok.Data;

/**
 *
 */
@Data
public class SysDictDataQueryReq {

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

}
