package com.aixbox.demo.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TestDemo 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TestDemoPageReqVO extends PageParam {

    /**
     * key键
     */
    private String testKey;

    /**
     * 值
     */
    private String value;

}
