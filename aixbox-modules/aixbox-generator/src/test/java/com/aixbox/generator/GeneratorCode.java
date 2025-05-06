package com.aixbox.generator;


import cn.hutool.core.util.StrUtil;
import com.aixbox.generator.domain.entity.GenTable;
import com.aixbox.generator.service.GenTableService;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
@RequiredArgsConstructor
@SpringBootTest
public class GeneratorCode {

    private final GenTableService genTableService;


    @Test
    public void generatorCode() {
        String dataName = scanner("数据源名称不能为空");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");

        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames, dataName);
        List<Long> tableIds = genTableService.importGenTable(tableList, dataName);
        for (Long tableId : tableIds) {
            byte[] data = genTableService.downloadCode(tableId);
            saveZip(data);
        }
    }


    public static void saveZip(byte[] data) {
        // 定义要保存的文件路径（当前目录下的 *.zip）
        String filePath = "./generated-code.zip";  // 当前项目根目录下

        // 写入到文件
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
            System.out.println("文件已保存至: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
