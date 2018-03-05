package tech.jebsun.codegenerator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.entity.ResponseData;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.exceptions.AppException;
import tech.jebsun.codegenerator.factory.GeneratorServiceFactory;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.utils.DBMetaUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private synchronized IGeneratorService getGeneratorService() throws SQLException, AppException {
        if(generatorService != null)
            return generatorService;
        else {
            DataBaseTypeEnum dataBaseType = dbMetaUtils.getDataBaseType();
            generatorService = generatorServiceFactory.getGeneratorService(dataBaseType);
            if(generatorService == null) {
                throw new AppException("获取数据库类型失败 !");
            }
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
            logger.error("获取数据库类型错误! " + e.getCause().getMessage());
            responseData.setSuccess(false);
            responseData.setMessage("获取数据库类型错误! ");
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
            responseData.setMessage("获取数据库类型错误! ");
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
}
