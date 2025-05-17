package com.aixbox.system.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据 返回参数
 */
@Data
public class SysDictDataResp {

    /**
    * 字典编码dict_code
    */
    private Long id;
    /**
    * 字典排序
    */
    private Long dictSort;
    /**
    * 字典标签
    */
    private String dictLabel;
    /**
    * 字典键值
    */
    private String dictValue;
    /**
    * 字典类型
    */
    private String dictType;
    /**
    * 样式属性（其他样式扩展）
    */
    private String cssClass;
    /**
    * 表格回显样式
    */
    private String listClass;
    /**
    * 是否默认（Y是 N否）
    */
    private String isDefault;
            /**
    * 创建时间
    */
    private LocalDateTime createTime;
                            /**
    * 备注
    */
    private String remark;


}
