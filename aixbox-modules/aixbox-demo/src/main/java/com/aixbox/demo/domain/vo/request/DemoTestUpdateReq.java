package com.aixbox.demo.domain.vo.request;

import com.aixbox.demo.domain.entity.DemoTest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 【请填写功能名称】 更新参数
 */
@Data
@AutoMapper(target = DemoTest.class)
public class DemoTestUpdateReq {

    /**
    * id
    */
    @NotNull
    private Long id;
    /**
    * 名字
    */
    private String name;
                                        
}
