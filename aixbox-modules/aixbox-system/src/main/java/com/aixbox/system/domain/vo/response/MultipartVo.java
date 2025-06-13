package com.aixbox.system.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分片上传
 *
 * @author AprilWind
 */
@Data
public class MultipartVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件原名（分片初始化的时候使用）
     */
    private String originalName;

    /**
     * 分片第一片的md5值
     */
    private String md5Digest;

    /**
     * 用于分片上传任务的 Upload ID
     * 在初始化分片上传时获取，并在后续的分片上传和完成上传过程中使用
     */
    private String uploadId;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 分片编号（从1开始递增）
     */
    private Integer partNumber;

    /**
     * 用于私有预签名 URL
     * 用于上传文件或分片的预签名 URL，确保文件或分片在上传时具有私有访问权限
     */
    private String privateUrl;

    /**
     * 已上传列表（最大长度一千）
     * 必须是唯一且按照递增顺序排列，严格检查是否漏传
     * ‘initiate’初始化状态时如果返回值，说明是断点续传，如果前端分片大于一千，需要请求‘query’获取
     */
    private List<PartUploadResult> partUploadList;

    @Data
    @AllArgsConstructor
    public static class PartUploadResult implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 分片编号（从1开始递增）
         */
        private Integer partNumber;

        /**
         * 从上传部分的内容生成的实体标签
         */
        private String eTag;

    }

}