package com.aixbox.system.domain.vo.request.dict;

import com.aixbox.system.domain.entity.SysDictData;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 字典数据 更新参数
 */
@Data
@AutoMapper(target = SysDictData.class)
public class SysDictDataUpdateReq {

    /**
    * 字典编码dict_code
    */
    @NotNull
    private Long id;
    /**
    * 字典排序
    */
    private Long dictSort;
    /**
    * 字典标签
    */
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过{max}个字符")
    private String dictLabel;
    /**
    * 字典键值
    */
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过{max}个字符")
    private String dictValue;
    /**
    * 字典类型
    */
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过{max}个字符")
    private String dictType;
    /**
    * 样式属性（其他样式扩展）
    */
    @Size(min = 0, max = 100, message = "样式属性长度不能超过{max}个字符")
    private String cssClass;
    /**
    * 表格回显样式
    */
    private String listClass;
    /**
    * 是否默认（Y是 N否）
    */
    private String isDefault;
    /**
    * 备注
    */
    private String remark;

}
