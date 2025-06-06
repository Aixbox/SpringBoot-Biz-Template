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
             * id
             */
        private Long id;

            /**
             * 名字
             */
        private String name;

            /**
             * 创建者
             */
        private String creator;

            /**
             * 性别
             */
        private Long sex;

            /**
             * 是否
             */
        private Boolean isOrNot;


}
