package tech.jebsun.codegenerator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.factory.GeneratorServiceFactory;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.utils.DBMetaUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JebSun on 2018/2/28.
 */
@RestController
public class GeneratorController {

    @Autowired
    private GeneratorServiceFactory generatorServiceFactory;

    @Autowired
    private DBMetaUtils dbMetaUtils;

    private static IGeneratorService generatorService;

    private final Logger logger = LoggerFactory.getLogger(GeneratorController.class);

    private synchronized IGeneratorService getGeneratorService() throws SQLException {
        if(generatorService != null)
            return generatorService;
        else {
            DataBaseTypeEnum dataBaseType = dbMetaUtils.getDataBaseType();
            generatorService = generatorServiceFactory.getGeneratorService(dataBaseType);
            return generatorService;
        }
    }

    @RequestMapping("/")
    public String index() throws SQLException {

        List<String> shcemaList = getGeneratorService().getDatabaseSchemasList();
        StringBuilder sb = new StringBuilder();

        for(String shcema : shcemaList) {
            sb.append("schema name: ").append(shcema).append("\n");
        }

        return sb.toString();
    }

    @RequestMapping("/getDataBaseTree")
    @ResponseBody
    public Object getDataBaseTree() {
        try {
            TreeNode tableTree = getGeneratorService().getDatabaseInfoTree();
            List<Object> datas = new ArrayList<Object>();
            datas.add(tableTree);
            return datas;
        } catch (SQLException e) {
            logger.error("获取数据库类型错误! ");
            return e.getMessage();
        }
    }

    @RequestMapping("/getSchemaTables")
    @ResponseBody
    public Object getSchemaTables(String schema) {
        try {
            List<TreeNode> tables = getGeneratorService().getSchemaTables(schema);
            return tables;
        } catch (SQLException e) {
            logger.error("获取数据库类型错误! ");
            return e.getMessage();
        }
    }

    @RequestMapping("/getSchemaViews")
    @ResponseBody
    public Object getSchemaViews(String schema) {
        try {
            List<TreeNode> tables = getGeneratorService().getSchemaViews(schema);
            return tables;
        } catch (SQLException e) {
            logger.error("获取数据库类型错误! ");
            return e.getMessage();
        }
    }
}
