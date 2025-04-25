package com.aixbox.system.mapper;

import com.aixbox.system.domain.entity.Test;
import org.apache.ibatis.annotations.Mapper;


/**
* 
*/
@Mapper
public interface TestMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Test record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Test record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Test selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Test record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Test record);
}