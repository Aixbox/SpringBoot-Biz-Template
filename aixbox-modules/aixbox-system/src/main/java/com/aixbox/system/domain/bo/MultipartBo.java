package com.aixbox.system.domain.bo;

import com.aixbox.common.core.validation.AddGroup;
import com.aixbox.common.core.validation.EditGroup;
import com.aixbox.common.core.validation.QueryGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分片上传
 *
 * @author AprilWind
 */
@Data
public class MultipartBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分片类型（必传）
     */
    private String ossStatus;

    /**
     * 文件原名（分片初始化的时候使用）
     */
    @Size(min = 2, max = 255, message = "文件原名长度必须在2到255之间")
    private String originalName;

    /**
     * 用于分片上传任务的 Upload ID
     * 在初始化分片上传时获取，并在后续的分片上传和完成上传过程中使用
     */
    @NotBlank(message = "上传任务ID不能为空", groups = {QueryGroup.class, AddGroup.class, EditGroup.class})
    private String uploadId;

    /**
     * 分片编号（从1开始递增）
     */
    @NotNull(message = "分片编号不能为空", groups = AddGroup.class)
    @Range(min = 1, max = 10000, message = "分片编号必须介于1和10,000之间", groups = AddGroup.class)
    private Integer partNumber;

    /**
     * 内容的 MD5 摘要
     * initiate初始化需要第一片的md5值（或者直接计算整体的md5）用来判断断点续传，以及秒传
     */
    @Size(max = 255, message = "MD5 摘要不能超过255")
    private String md5Digest;

    /**
     * 最大返回的分片数（默认为1000，最大值1000）
     * 最多分片一万，一次性返回会造成前端性能问题，需要前端多次校验
     */
    @NotNull(message = "最大返回的分片数不能为空", groups = QueryGroup.class)
    @Range(max = 1000, message = "最大返回的分片数不能超过1000", groups = QueryGroup.class)
    private Integer maxParts;

    /**
     * 分片编号的标记，用于分页查询（默认为0，表示从第一个分片开始查询）
     */
    @NotNull(message = "分片编号的标记不能为空", groups = QueryGroup.class)
    @Range(min = 0, max = 10000, message = "分片编号的标记长度必须在0到10000之间", groups = QueryGroup.class)
    private Integer partNumberMarker;

    /**
     * 已上传列表（最大长度一万）
     * 必须是唯一且按照递增顺序排列，严格检查是否漏传
     */
    @Valid
    @NotNull(message = "已上传列表不能为空", groups = EditGroup.class)
    @Size(min = 2, max = 10000, message = "已上传列表长度必须在2到10000之间", groups = EditGroup.class)
    private List<PartUploadResult> partUploadList;

    @Data
    public static class PartUploadResult implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 分片编号（从1开始递增）
         */
        @NotNull(message = "分片编号不能为空", groups = EditGroup.class)
        @Range(min = 1, max = 10000, message = "分片编号必须介于1和10,000之间", groups = EditGroup.class)
        private Integer partNumber;

        /**
         * 从上传部分的内容生成的实体标签
         */
        @NotBlank(message = "实体标签不能为空", groups = EditGroup.class)
        @JsonProperty("eTag")
        private String eTag;
    }

}