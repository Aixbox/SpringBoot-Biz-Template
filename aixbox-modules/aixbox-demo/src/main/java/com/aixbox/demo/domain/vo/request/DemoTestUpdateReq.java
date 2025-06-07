package com.aixbox.demo.domain.vo.request;

import com.aixbox.demo.domain.entity.DemoTest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 测试更新参数
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
                @NotBlank(message = "名字不能为空")
        private String inputType;

            /**
             * 性别
             */
        private Long sex;

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
