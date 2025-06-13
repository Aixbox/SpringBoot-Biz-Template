package com.aixbox.common.oss.entity;


import lombok.Builder;
import lombok.Data;

/**
 * 上传返回体
 */
@Data
@Builder
public class UploadResult {

    /**
     * 文件路径
     */
    private String url;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 已上传对象的实体标记（用来校验文件）
     */
    private String eTag;

    /**
     * 用于分片上传任务的 Upload ID
     * 在初始化分片上传时获取，并在后续的分片上传和完成上传过程中使用
     */
    private String uploadId;

    /**
     * 用于私有预签名 URL
     * 用于上传文件或分片的预签名 URL，确保文件或分片在上传时具有私有访问权限
     */
    private String privateUrl;

}
