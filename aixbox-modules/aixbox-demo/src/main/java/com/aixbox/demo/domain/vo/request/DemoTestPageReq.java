package com.aixbox.demo.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

            /**
             * 创建时间
             */
        private LocalDate[] createTime;

            /**
             * 性别
             */
        private Long sex;

            /**
             * 是否
             */
        private Long isOrNot;



}
