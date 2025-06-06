package com.aixbox.demo.domain.vo.request;


import lombok.Data;

/**
 * demo新增参数
 */
@Data
public class DemoTestSaveReq {

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
            private Long isOrNot;


}
