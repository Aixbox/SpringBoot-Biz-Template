package com.aixbox.generator.service;


import com.aixbox.generator.domain.entity.GenTable;
import com.aixbox.generator.domain.entity.GenTableColumn;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 *
 */
public interface GenTableService {
    void generatorCode(Long tableId, ZipOutputStream zip);

    String getUniqueFilePath(Long tableId);

    List<GenTable> selectDbTableListByNames(String[] tableNames, String dataName);

    List<Long> importGenTable(List<GenTable> tableList, String dataName);

    List<GenTableColumn> selectDbTableColumnsByName(String tableName, String dataName);

    void synchDb(Long tableId);

    byte[] downloadCode(Long tableId);

    Long selectTableId(String tableName, String dataName);

    @Transactional(rollbackFor = Exception.class)
    void updateGenTable(GenTable genTable);
}
