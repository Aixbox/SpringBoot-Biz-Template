package generator;


import com.aixbox.generator.domain.entity.GenTable;
import com.aixbox.generator.service.GenTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 代码生成器
 */
@Slf4j
@SpringBootTest(classes = com.aixbox.web.AixboxApplication.class)
public class GeneratorCode {

    @Autowired
    private GenTableService genTableService;


    @Test
    void generatorCode() {

        //数据源
        String dataName = "master";
        //表名
        String[] tableNames = {"sys_dict_data"};
        //包路径
        String packageName = "com.aixbox.system";
        //模块名
        String moduleName = "system";
        //功能名
        String functionName = "字典数据";


        //查询数据库是否存在表
        Long tableId = genTableService.selectTableId(tableNames[0], dataName);
        if (tableId == null) {
            //不存在导入表信息
            //查询表信息
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames, dataName);
            //导入表信息
            List<Long> tableIds = genTableService.importGenTable(tableList, dataName);
            tableId = tableIds.get(0);
        }

        //同步表信息
        genTableService.synchDb(tableId);

        //修改表信息
        GenTable genTable = new GenTable()
                .setTableId(tableId)
                .setPackageName(packageName)
                .setModuleName(moduleName)
                .setFunctionName(functionName);
        genTableService.updateGenTable(genTable);

        //生成代码
        byte[] data = genTableService.downloadCode(tableId);

        // 定义要保存的文件路径（当前目录下的 *.zip）下
        String uniqueFilePath = genTableService.getUniqueFilePath(tableId);
        saveZip(data, uniqueFilePath);
    }


    public static void saveZip(byte[] data, String filePath) {

        // 将字符串路径转为 File 对象
        File file = new File(filePath);

        // 创建父目录（如果不存在）
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // 多级目录创建
        }

        // 写入到文件
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
            System.out.println("文件已保存至: " + filePath);
        } catch (IOException e) {
            log.error("文件保存失败", e);
        }
    }


}
