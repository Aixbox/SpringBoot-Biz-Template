package com.aixbox.system.domain.vo.request;


import lombok.Data;
import org.wildfly.common.annotation.NotNull;

/**
 * 字典类型 新增参数
 */
@Data
public class SysDictTypeSaveReq {

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
