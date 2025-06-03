package com.aixbox.generator.mapper;


import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.generator.domain.entity.GenTable;
import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * GenTable Mapper接口
 */
@Mapper
public interface GenTableMapper extends BaseMapperX<GenTable>  {

    /**
     * 查询表ID业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Long id);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GenTable> selectGenTableAll();

    /**
     * 查询指定数据源下的所有表名列表
     *
     * @param dataName 数据源名称，用于选择不同的数据源
     * @return 当前数据库中的表名列表
     *
     * @DS("") 使用默认数据源执行查询操作
     */
    @DS("")
    List<String> selectTableNameList(String dataName);
}
