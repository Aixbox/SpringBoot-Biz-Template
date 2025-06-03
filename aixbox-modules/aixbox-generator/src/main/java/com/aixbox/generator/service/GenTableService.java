package com.aixbox.generator.service;


import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.generator.domain.entity.GenTable;
import com.aixbox.generator.domain.entity.GenTableColumn;

import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 *
 */
public interface GenTableService {

    /**
     * 生成代码（自定义路径）
     *
     * @param tableId 表名称
     */
    void generatorCode(Long tableId, ZipOutputStream zip);

     /**
     * 获取唯一文件路径
     *
     * @param tableId 表ID
     * @return 唯一文件路径
     */
    String getUniqueFilePath(Long tableId);

    /**
     * 查询数据库列表
     *
     * @param tableNames 表名称组
     * @param dataName   数据源名称
     * @return 数据库表集合
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames, String dataName);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     * @param dataName  数据源名称
     */
    List<Long> importGenTable(List<GenTable> tableList, String dataName);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @param dataName  数据源名称
     * @return 列信息
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName, String dataName);

    /**
     * 同步数据库
     *
     * @param tableId 表名称
     */
    void synchDb(Long tableId);

    /**
     * 生成代码（下载方式）
     *
     * @param tableId 表名称
     * @return 数据
     */
    byte[] downloadCode(Long tableId);

    /**
     * 根据表名称查询表
     */
    Long selectTableId(String tableName, String dataName);

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     */
    void updateGenTable(GenTable genTable);

    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
    PageResult<GenTable> selectPageGenTableList(GenTable genTable, PageParam pageQuery);

    /**
     * 查询数据表信息
     *
     * @param tableId 数据表id
     * @return 业务信息
     */
    GenTable selectGenTableById(Long tableId);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GenTable> selectGenTableAll();

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    PageResult<GenTable> selectPageDbTableList(GenTable genTable, PageParam pageQuery);
}
