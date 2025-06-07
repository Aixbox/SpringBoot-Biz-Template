package com.aixbox.demo.domain.vo.request;


import com.aixbox.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 测试 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoTestPageReq extends PageParam {

            /**
             * id
             */
            private Long id;


            /**
             * 名字
             */
            private String inputType;


            /**
             * 性别
             */
            private Long sex;


            /**
             * 创建时间
             */
            private LocalDate[] createTime;


            /**
             * int类型
             */
            private String integerType;


            /**
             * 文本域类型
             */
            private String textareaType;


            /**
             * 选择类型
             */
            private String selectType;


            /**
             * 是否
             */
            private Boolean radioIsOrNot;


            /**
             * 复选框类型
             */
            private String checkboxType;




}
