package com.aixbox.generator.mapper;


import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.generator.domain.entity.GenTable;
import org.apache.ibatis.annotations.Mapper;

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

}
