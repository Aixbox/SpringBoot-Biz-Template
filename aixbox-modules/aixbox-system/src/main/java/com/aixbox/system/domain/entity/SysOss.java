package com.aixbox.system.domain.entity;

import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

/**
 * OSS对象存储对象
 */
@TableName(value ="sys_oss")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOss extends BaseDO {

            /**
             * 对象存储主键
             */
            @TableId(value = "id")
            private Long id;

            /**
             * 文件名
             */
            private String fileName;

            /**
             * 原名
             */
            private String originalName;

            /**
             * 文件后缀名
             */
            private String fileSuffix;

            /**
             * URL地址
             */
            private String url;

            /**
             * 扩展字段
             */
            private String ext1;

            /**
             * 服务商
             */
            private String service;

            /**
             * 更新者
             */
            private String updater;



}