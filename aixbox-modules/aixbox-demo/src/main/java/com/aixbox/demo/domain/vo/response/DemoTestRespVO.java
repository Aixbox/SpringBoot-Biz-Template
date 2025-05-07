package com.aixbox.demo.domain.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【请填写功能名称】 返回参数
 */
@Data
public class DemoTestRespVO {

    /**
    * id
    */
    private Long id;
    /**
    * 名字
    */
    private String name;
    /**
    * 创建时间
    */
    private LocalDateTime createTime;
                        

}
