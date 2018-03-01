package tech.jebsun.codegenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jebsun.codegenerator.constants.ColumnCategoryConstants;
import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.enums.JavaTypeEnum;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.utils.DBMetaUtils;
import tech.jebsun.codegenerator.utils.JavaTypeResolver;
import tech.jebsun.codegenerator.utils.StringUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JebSun on 2018/2/27.
 */
@Service
public class BaseGeneratorServiceImpl implements IGeneratorService {

    @Autowired
    private DBMetaUtils dbMetaUtils;

    public DBMetaUtils getDbMetaUtils() {
        return dbMetaUtils;
    }

    /**
     * 获取数据库信息树 根节点
     * 初始化数据库名称和版本号
     * @return
     * @throws SQLException
     */
    @Override
    public TreeNode getDatabaseRootNode() throws SQLException {
        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        //获取数据库名称、版本
        String dataBaseName = meta.getDatabaseProductName();
        int dataBaseVersion = meta.getDatabaseMajorVersion();
        TreeNode rootNode = new TreeNode();
        rootNode.setTitle(dataBaseName + dataBaseVersion);
        rootNode.setNodeType("ROOT-NODE");
        rootNode.setExpand(true);

        return rootNode;
    }

    /**
     * 获取当前数据库下所有Schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<String> getDatabaseSchemasList() throws SQLException {

        DatabaseMetaData databaseMetaData = getDbMetaUtils().getMetaData();
        ResultSet schemaRs = databaseMetaData.getSchemas();
        List<String> schemaList = new ArrayList<>();
        while (schemaRs.next()) {
            String schema = schemaRs.getString("TABLE_SCHEM");
            if(schema != null){
                schemaList.add(schema);
            }
        }
        return schemaList;
    }

    /**
     * 获取当前数据库下所有Schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<TreeNode> getSchemaTreeNodeList() throws SQLException {

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();

        List<TreeNode> schemaList = new ArrayList<>();
        List<String> schemaNameList = getDatabaseSchemasList();

        for(String schemaName : schemaNameList) {
            TreeNode schemaNode = new TreeNode();
            schemaNode.setTitle(schemaName);
            schemaNode.setNodeType("SCHEMA-NODE");
            schemaList.add(schemaNode);
        }

        //设置表/视图子节点
        for(TreeNode schemaNode : schemaList){
            List<TreeNode> childNode = new ArrayList<>();

            TreeNode tableNode = new TreeNode();
            tableNode.setTitle("TABLE");
            tableNode.setNodeType("TABLE-NODE");
            tableNode.setSchema(schemaNode.getTitle());
            tableNode.setChildren(new ArrayList<>());

            TreeNode viewNode = new TreeNode();
            viewNode.setTitle("VIEW");
            viewNode.setNodeType("VIEW-NODE");
            viewNode.setSchema(schemaNode.getTitle());
            viewNode.setChildren(new ArrayList<>());

            childNode.add(tableNode);
            childNode.add(viewNode);
            schemaNode.setChildren(childNode);
        }

        return schemaList;
    }

    /**
     * 获取数据库信息
     * @return
     * @throws SQLException
     */
    @Override
    public TreeNode getDatabaseInfoTree() throws SQLException {

        //1. 获取数据库根节点
        TreeNode rootNode = getDatabaseRootNode();

        //2. 获取数据库shcemas
        List<TreeNode> schemaList = getSchemaTreeNodeList();

        if(schemaList.size() > 0){
            rootNode.setChildren(schemaList);
        }

        return rootNode;
    }

    private void fillSchemaObjectNode(ResultSet objectResultSet, List<TreeNode> tableList) throws SQLException{

        while (objectResultSet.next()){
            TreeNode tableNode = new TreeNode();
            String schemaName = objectResultSet.getString("TABLE_SCHEM");
            String tableName = objectResultSet.getString("TABLE_NAME");
            String tabletType = objectResultSet.getString("TABLE_TYPE");
            tableNode.setTitle(tableName);
            tableNode.setSchema(schemaName);
            tableNode.setNodeType(tabletType+"-LEAF");
            tableList.add(tableNode);
        }
    }

    @Override
    public List<TreeNode> getSchemaTables(String schema) throws SQLException {

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        List<TreeNode> tableList = new ArrayList<>();
        ResultSet tableRs = meta.getTables(null, schema, null, new String[]{"TABLE"});
        fillSchemaObjectNode(tableRs, tableList);

        return tableList;
    }

    @Override
    public List<TreeNode> getSchemaViews(String schema) throws SQLException {

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        List<TreeNode> viewList = new ArrayList<>();
        ResultSet viewRs = meta.getTables(null, schema, null, new String[]{"VIEW"});
        fillSchemaObjectNode(viewRs, viewList);

        return viewList;
    }

    /**
     * 获取表字段备注
     * @param columnRs
     * @return
     */
    public String getColumComment(ResultSet columnRs, String tableName, String columnName){
        try {
            return columnRs.getString("REMARKS");
        } catch (SQLException ex) {
            return "";
        }
    }

    @Override
    public List<Column> getTableColumns(Table table) throws SQLException {

        String schema  = table.getSchema();
        String tableName = table.getTableName();

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        List<Column> columnList = new ArrayList<Column>();
        List<String> primaryKeyList = new ArrayList<String>();
        List<String> uniqueKeyList = new ArrayList<String>();

        //主键
        ResultSet primaryKeyRs = meta.getPrimaryKeys(null, schema, tableName);
        while (primaryKeyRs.next()){
            String columnName = primaryKeyRs.getString("COLUMN_NAME");
            primaryKeyList.add(columnName);
        }

        //唯一键
        ResultSet uniqueKeyRs = meta.getIndexInfo(null, schema, tableName, true, true);
        while (uniqueKeyRs.next()){
            String columnName = uniqueKeyRs.getString("COLUMN_NAME");
            uniqueKeyList.add(columnName);
        }

        ResultSet columnRs = meta.getColumns(null, schema, tableName, null);

        while (columnRs.next()){
            Column column = new Column();
            String columnName = columnRs.getString("COLUMN_NAME");
            int sqlType = columnRs.getInt("DATA_TYPE");
            String sqlTypeName = columnRs.getString("TYPE_NAME");
            String defaultValue = columnRs.getString("COLUMN_DEF");
            boolean isNullable = (DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE"));
            int size = columnRs.getInt("COLUMN_SIZE");
            boolean isPk = primaryKeyList.contains(columnName);
            boolean isUnique = uniqueKeyList.contains(columnName);
            String comment = getColumComment(columnRs, tableName, columnName);

            column.setColumnName(columnName);
            column.setColumnCategory(ColumnCategoryConstants.calculateFieldCategory(columnName));
            column.setJavaProperty(StringUtils.getCamelCaseString(columnName, false));
            column.setJavaPropertyFirstUpper(StringUtils.getCamelCaseString(columnName, true));

            column.setColumnDataBaseType(sqlType);
            column.setColumnDataBaseTypeName(sqlTypeName);
            column.setColumnSize(size);
            column.setDefaultValue(defaultValue);
            column.setNullable(isNullable);
            column.setUnique(isUnique);
            column.setPrimaryKey(isPk);
            JavaTypeEnum javaType = JavaTypeResolver.calculateJavaType(column);
            column.setJavaTypeName(javaType.getTypeName());
            column.setFullJavaTypeName(javaType.getFullTypeName());
            column.setColumnComment(comment == null ? "" : StringUtils.ReplaceNewlineBlank(comment));

            columnList.add(column);
        }
        return columnList;
    }
}
