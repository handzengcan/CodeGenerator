package tech.jebsun.codegenerator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.entity.ResponseData;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.enums.JavaTypeEnum;
import tech.jebsun.codegenerator.exceptions.AppException;
import tech.jebsun.codegenerator.factory.GeneratorServiceFactory;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.utils.DBMetaUtils;
import tech.jebsun.codegenerator.utils.FreemarkerUtils;
import tech.jebsun.codegenerator.utils.JavaTypeResolver;
import tech.jebsun.codegenerator.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by JebSun on 2018/2/28.
 */
@Controller
public class GeneratorController {

    @Autowired
    private GeneratorServiceFactory generatorServiceFactory;

    @Autowired
    private DBMetaUtils dbMetaUtils;

    private static IGeneratorService generatorService;

    private final Logger logger = LoggerFactory.getLogger(GeneratorController.class);

    private synchronized IGeneratorService getGeneratorService() throws AppException {
        if (generatorService != null)
            return generatorService;
        else {
            DataBaseTypeEnum dataBaseType = dbMetaUtils.getDataBaseType();
            generatorService = generatorServiceFactory.getGeneratorService(dataBaseType);
            return generatorService;
        }
    }

    @RequestMapping("/")
    public String index() throws SQLException {
        return "index.html";
    }

    @RequestMapping("/getDataBaseTree")
    @ResponseBody
    public ResponseData getDataBaseTree() {
        ResponseData responseData = new ResponseData();
        try {
            TreeNode tableTree = getGeneratorService().getDatabaseInfoTree();
            List<Object> datas = new ArrayList<Object>();
            datas.add(tableTree);
            responseData.setRows(datas);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据库类型错误! " + e.getCause().getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("获取数据库类型错误! " + e.getMessage());
        }
        return responseData;
    }

    @RequestMapping("/getSchemaTables")
    @ResponseBody
    public ResponseData getSchemaTables(String schema) {
        ResponseData responseData = new ResponseData();
        try {
            List<TreeNode> tables = getGeneratorService().getSchemaTables(schema);
            responseData.setRows(tables);
        } catch (Exception e) {
            logger.error("获取数据库表对象错误! " + e.getCause().getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("获取数据库表对象错误! ");
        }
        return responseData;
    }

    @RequestMapping("/getSchemaViews")
    @ResponseBody
    public ResponseData getSchemaViews(String schema) {
        ResponseData responseData = new ResponseData();
        try {
            List<TreeNode> tables = getGeneratorService().getSchemaViews(schema);
            responseData.setRows(tables);
        } catch (Exception e) {
            logger.error("获取数据库视图对象错误! " + e.getCause().getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("获取数据库视图对象错误! ");
        }
        return responseData;
    }

    @RequestMapping("/getTableColumns")
    @ResponseBody
    public ResponseData getTableColumns(Table table) {
        ResponseData responseData = new ResponseData();
        try {
            List<Column> columns = getGeneratorService().getTableColumns(table);
            responseData.setRows(columns);
        } catch (Exception e) {
            logger.error("获取表/视图对象详情错误! " + e.getCause().getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("获取表/视图对象详情错误!");
        }
        return responseData;
    }

    @RequestMapping(value = "/getAllJavaTypes")
    @ResponseBody
    public ResponseData getAllJavaTypes() {
        ResponseData responseData = new ResponseData();
        List<Object> datas = JavaTypeResolver.getAllJavaTypesList();
        responseData.setRows(datas);
        return responseData;
    }

    @RequestMapping(value = "/processTable", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData processTable(@RequestBody Table table) {

        Map model = new HashMap<String, Object>();
        FreemarkerUtils freemarkerUtils = new FreemarkerUtils();
        ResponseData responseData = new ResponseData();

        if (table == null) {
            responseData.setSuccess(false);
            responseData.setMessage("无法接收表信息! ");
            return responseData;
        }

        List<Column> columnList = table.getColumnList();
        if (columnList == null || columnList.size() == 0) {
            responseData.setSuccess(false);
            responseData.setMessage("至少选择一个数据库字段！");
            return responseData;
        }

        int pkValueCount = 0;
        for (Column column : table.getColumnList()) {
            if (column.isPrimaryKey()) {
                table.setPrimaryKeyColunm(column);
                pkValueCount++;
            }
        }
        if (table.getForceGenerate().equals("N") && table.getObjectType().equals("table") && pkValueCount == 0) {
            responseData.setSuccess(false);
            responseData.setMessage("表类型对象必须包含主键列！");
            return responseData;
        }

        if (table.getForceGenerate().equals("N") && pkValueCount > 1) {
            responseData.setSuccess(false);
            responseData.setMessage("不支持联合主键！");
            return responseData;
        }
        table.cacluHapStdTable();

        if (table.getObjectType().equals("table") && table.getForceGenerate().equals("N") && !table.getStdHapTable()) {
            responseData.setSuccess(false);
            responseData.setMessage("不是Hap标准表，如需强制生成，将强制生成设为是！");
            return responseData;
        }

        Set<String> packageImportSet = new HashSet<>();

        for(Column column : columnList) {
            String javaTypeName = column.getJavaTypeName();
            JavaTypeEnum javaTypeEnum = JavaTypeEnum.getJavaTypeByShortName(javaTypeName);
            String fullTypeName = javaTypeEnum.getFullTypeName();
            column.setFullJavaTypeName(fullTypeName);
            if(!javaTypeEnum.getJavaLang()) {
                packageImportSet.add(fullTypeName);
            }
        }

        table.setPackageImports(new ArrayList<>(packageImportSet));
        table.setClassName(StringUtils.getCamelCaseString(table.getTableName(), true));
        table.setClassNameFirstLowwer(StringUtils.getCamelCaseString(table.getTableName(), false));
        table.setChildSysName(StringUtils.packagePathToChildSysName(table.getBasePackage()));
        model.put("Table", table);
        try {
            //判断输出文件夹是否存在不存在则创建
            File file = new File(table.getOutPath());
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            freemarkerUtils.init();
            logger.info("Begin Process===");
            freemarkerUtils.process(model, file.getPath(), table.getClassName(), table.getTableName());
            logger.info("End Procss===");
            logger.info("FilePath===" + file.getAbsolutePath());
            responseData.setSuccess(true);
            responseData.setMessage("Success Path=======" + file.getAbsolutePath() + "===============");
        } catch (Exception ex) {
            responseData.setSuccess(false);
            responseData.setMessage("生成失败！" + ex.getMessage());
            return responseData;
        }

        return responseData;
    }
}
