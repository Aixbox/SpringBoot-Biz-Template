package com.aixbox.system.domain.vo.request.dept;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TestDemo 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptPageReqVO extends PageParam {

    /**
     * 关键字
     */
    private String keyword;

}
