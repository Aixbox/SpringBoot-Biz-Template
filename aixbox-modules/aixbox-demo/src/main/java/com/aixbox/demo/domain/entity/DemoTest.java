package com.aixbox.demo.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * 测试对象
 */
@TableName(value ="demo_test")
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoTest extends BaseDO {

            /**
             * id
             */
            @TableId(value = "id")
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
             * 更新者
             */
            private String updater;

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