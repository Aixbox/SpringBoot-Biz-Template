package com.aixbox.demo.domain.vo.request;

import com.aixbox.demo.domain.entity.DemoTest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * demo更新参数
 */
@Data
public class DemoTestUpdateReq {

            /**
             * 名字
             */
        private String name;


}
