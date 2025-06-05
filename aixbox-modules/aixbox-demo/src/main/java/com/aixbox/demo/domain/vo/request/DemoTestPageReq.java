package com.aixbox.demo.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * demo 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoTestPageReq extends PageParam {

            /**
             * 名字
             */
        private String name;

            /**
             * 创建者
             */
        private String creator;



}
