package com.aixbox.generator.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.date.DateUtils;
import com.aixbox.common.core.utils.json.JsonUtils;
import com.aixbox.common.mybatis.core.util.JdbcUtils;
import com.aixbox.generator.constant.GenConstants;
import com.aixbox.generator.domain.entity.GenTable;
import com.aixbox.generator.domain.entity.GenTableColumn;
import com.baomidou.mybatisplus.annotation.DbType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模板处理工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VelocityUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";


    /**
     * 默认上级菜单，系统工具
     */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StrUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StrUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StrUtils.capitalize(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        velocityContext.put("dicts", getDicts(genTable));
        velocityContext.put("CLASS_NAME", StrUtils.camelToUnderscoreUpper(genTable.getClassName()));
        setMenuVelocityContext(velocityContext, genTable);
        if (GenConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * todo 这里区分开编辑、查询、新增等不同类的导入包函数
     * 根据列类型获取导入包
     *
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        HashSet<String> importList = new HashSet<>();
        for (GenTableColumn column : columns) {
            if (GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.time.LocalDate");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            } else if (!column.isSuperColumn() && "imageUpload".equals(column.getHtmlType())) {
                //todo 后期如果进入这个逻辑，修改成自己的类
                importList.add("org.dromara.common.translation.annotation.Translation");
                importList.add("org.dromara.common.translation.constant.TransConstant");
            } else if (column.isRequired() && GenConstants.TYPE_STRING.equals(column.getJavaType())) {
                importList.add("jakarta.validation.constraints.NotBlank");
            } else if (column.isRequired() && !GenConstants.TYPE_STRING.equals(column.getJavaType())) {
                importList.add("jakarta.validation.constraints.NotNull");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return StrUtils.format("{}:{}", moduleName, businessName);
    }

    /**
     * 根据列类型获取字典组
     *
     * @param genTable 业务表对象
     * @return 返回字典组
     */
    public static String getDicts(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        Set<String> dicts = new HashSet<>();
        addDicts(dicts, columns);
        return StringUtils.join(dicts, ", ");
    }

    /**
     * 添加字典列表
     *
     * @param dicts 字典列表
     * @param columns 列集合
     */
    public static void addDicts(Set<String> dicts, List<GenTableColumn> columns) {
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDictType()) && StringUtils.equalsAny(
                    column.getHtmlType(),
                    new String[] { GenConstants.HTML_SELECT, GenConstants.HTML_RADIO, GenConstants.HTML_CHECKBOX })) {
                dicts.add("'" + column.getDictType() + "'");
            }
        }
    }

    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)
                && StringUtils.isNotEmpty(paramsObj.getStr(GenConstants.PARENT_MENU_ID))) {
            return paramsObj.getStr(GenConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.get(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.get(GenConstants.TREE_NAME));
        }
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable) {
        String options = genTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String treeName = paramsObj.getStr(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(Map<String, Object> paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.TREE_CODE)) {
            return StrUtils.toCamelCase(Convert.toStr(paramsObj.get(GenConstants.TREE_CODE)));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            return StrUtils.toCamelCase(paramsObj.getStr(GenConstants.TREE_PARENT_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.TREE_NAME)) {
            return StrUtils.toCamelCase(paramsObj.getStr(GenConstants.TREE_NAME));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/controller/Controller.java.vm");
        templates.add("vm/java/domain/entity.java.vm");
        templates.add("vm/java/domain/PageReq.java.vm");
        templates.add("vm/java/domain/Resp.java.vm");
        templates.add("vm/java/domain/SaveReq.java.vm");
        templates.add("vm/java/domain/UpdateReq.java.vm");
        templates.add("vm/java/mapper/Mapper.java.vm");
        templates.add("vm/java/mapper/Mapper.xml.vm");
        templates.add("vm/java/service/Service.java.vm");
        templates.add("vm/java/service/ServiceImpl.java.vm");
        templates.add("vm/java/test/ServiceImplTest.java.vm");
        templates.add("vm/java/constant/ErrorCodeConstants.java.vm");
        templates.add("vm/vue/api/index.ts.vm");
        templates.add("vm/vue/api/model.d.ts.vm");
        templates.add("vm/vue/view/data.ts.vm");
        templates.add("vm/vue/view/index.vue.vm");
        templates.add("vm/vue/view/modal.vue.vm");
        DbType dbType = JdbcUtils.getDbType();
        //todo 要修改为自己的vm文件
        //if (ObjectUtil.equals(dbType, DbType.ORACLE)) {
        //    templates.add("vm/sql/oracle/sql.vm");
        //} else if (ObjectUtil.equals(dbType, DbType.POSTGRE_SQL)) {
        //    templates.add("vm/sql/postgres/sql.vm");
        //} else if (ObjectUtil.equals(dbType, DbType.SQL_SERVER)) {
        //    templates.add("vm/sql/sqlserver/sql.vm");
        //} else {
        //    templates.add("vm/sql/sql.vm");
        //}
        //templates.add("vm/ts/api.ts.vm");
        //templates.add("vm/ts/types.ts.vm");
        //if (GenConstants.TPL_CRUD.equals(tplCategory)) {
        //    templates.add("vm/vue/index.vue.vm");
        //} else if (GenConstants.TPL_TREE.equals(tplCategory)) {
        //    templates.add("vm/vue/index-tree.vue.vm");
        //}
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue/src";

        //todo 文件路径修改成自己的路径
        if (template.contains("Controller.java.vm")) {
            fileName = StrUtils.format("{}/controller/admin/{}Controller.java", javaPath, className);
        } else if (template.contains("entity.java.vm")) {
            fileName = StrUtils.format("{}/domain/entity/{}.java", javaPath, className);
        } else if (template.contains("PageReq.java.vm")) {
            fileName = StrUtils.format("{}/domain/vo/request/{}PageReq.java", javaPath, className);
        } else if (template.contains("Resp.java.vm")) {
            fileName = StrUtils.format("{}/domain/vo/response/{}Resp.java", javaPath, className);
        } else if (template.contains("SaveReq.java.vm")) {
            fileName = StrUtils.format("{}/domain/vo/request/{}SaveReq.java", javaPath, className);
        } else if (template.contains("UpdateReq.java.vm")) {
            fileName = StrUtils.format("{}/domain/vo/request/{}UpdateReq.java", javaPath, className);
        } else if (template.contains("Mapper.java.vm")) {
            fileName = StrUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("Mapper.xml.vm")) {
            fileName = StrUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains("Service.java.vm")) {
            fileName = StrUtils.format("{}/service/{}Service.java", javaPath, className);
        } else if (template.contains("ServiceImpl.java.vm")) {
            fileName = StrUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("ErrorCodeConstants.java.vm")) {
            fileName = StrUtils.format("{}/constant/ErrorCodeConstants.java", javaPath, className);
        } else if (template.contains("h2.sql.vm")) {
            fileName = StrUtils.format("sql/{}H2.sql", className);
        } else if (template.contains("index.ts.vm")) {
            fileName = StrUtils.format("{}/api/{}/{}/index.ts", vuePath, moduleName, businessName);
        } else if (template.contains("model.d.ts.vm")) {
            fileName = StrUtils.format("{}/api/{}/{}/model.d.ts", vuePath, moduleName, businessName);
        } else if (template.contains("data.ts.vm")) {
            fileName = StrUtils.format("{}/view/{}/{}/data.ts", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StrUtils.format("{}/view/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("modal.vue.vm")) {
            fileName = StrUtils.format("{}/view/{}/{}/{}-model.vue", vuePath, moduleName,
                    businessName, businessName);
        }



        //if (template.contains("domain.java.vm")) {
        //    fileName = StrUtils.format("{}/domain/{}.java", javaPath, className);
        //}
        //if (template.contains("vo.java.vm")) {
        //    fileName = StrUtils.format("{}/domain/vo/{}Vo.java", javaPath, className);
        //}
        //if (template.contains("bo.java.vm")) {
        //    fileName = StrUtils.format("{}/domain/bo/{}Bo.java", javaPath, className);
        //}
        //if (template.contains("mapper.java.vm")) {
        //    fileName = StrUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        //} else if (template.contains("service.java.vm")) {
        //    fileName = StrUtils.format("{}/service/I{}Service.java", javaPath, className);
        //} else if (template.contains("serviceImpl.java.vm")) {
        //    fileName = StrUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        //} else if (template.contains("controller.java.vm")) {
        //    fileName = StrUtils.format("{}/controller/{}Controller.java", javaPath, className);
        //} else if (template.contains("mapper.xml.vm")) {
        //    fileName = StrUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        //} else if (template.contains("sql.vm")) {
        //    fileName = businessName + "Menu.sql";
        //} else if (template.contains("api.ts.vm")) {
        //    fileName = StrUtils.format("{}/api/{}/{}/index.ts", vuePath, moduleName, businessName);
        //} else if (template.contains("types.ts.vm")) {
        //    fileName = StrUtils.format("{}/api/{}/{}/types.ts", vuePath, moduleName, businessName);
        //} else if (template.contains("index.vue.vm")) {
        //    fileName = StrUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        //} else if (template.contains("index-tree.vue.vm")) {
        //    fileName = StrUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        //}
        return fileName;
    }

}
