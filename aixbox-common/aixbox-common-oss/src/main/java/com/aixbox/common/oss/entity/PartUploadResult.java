package com.aixbox.common.oss.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 上传部件返回体
 *
 * @author AprilWind
 */
@Data
@Builder
public class PartUploadResult {

    /**
     * 分片编号（从1开始递增）
     */
    private Integer partNumber;

    /**
     * 从上传部分的内容生成的实体标签
     */
    private String eTag;

}