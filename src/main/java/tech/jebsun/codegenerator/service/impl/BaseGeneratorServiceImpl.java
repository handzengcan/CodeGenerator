package tech.jebsun.codegenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.enums.JavaTypeEnum;
import tech.jebsun.codegenerator.exceptions.AppException;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.utils.DBMetaUtils;
import tech.jebsun.codegenerator.utils.JavaTypeResolver;
import tech.jebsun.codegenerator.utils.StringUtils;

import java.sql.Connection;
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
    public TreeNode getDatabaseRootNode(DatabaseMetaData metaData) throws AppException {
        try {
            //获取数据库名称、版本
            String dataBaseName = metaData.getDatabaseProductName();
            int dataBaseVersion = metaData.getDatabaseMajorVersion();
            TreeNode rootNode = new TreeNode();
            rootNode.setLabel(dataBaseName + dataBaseVersion);
            rootNode.setNodeType("ROOT-NODE");
            rootNode.setIcon("storage");
            rootNode.setNoTick(true);
            rootNode.setExpandable(true);

            return rootNode;
        } catch (SQLException ex) {
            throw new AppException(ex);
        }
    }

    /**
     * 获取当前数据库下所有Schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<String> getDatabaseSchemasList(DatabaseMetaData metaData) throws AppException {
        try {
            ResultSet schemaRs = metaData.getSchemas();
            List<String> schemaList = new ArrayList<>();
            while (schemaRs.next()) {
                String schema = schemaRs.getString("TABLE_SCHEM");
                if(schema != null){
                    schemaList.add(schema);
                }
            }
            return schemaList;
        } catch (SQLException ex) {
            throw new AppException(ex);
        }

    }

    /**
     * 获取当前数据库下所有Schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<TreeNode> getSchemaTreeNodeList(DatabaseMetaData metaData) throws AppException {

        List<TreeNode> schemaList = new ArrayList<>();
        List<String> schemaNameList = getDatabaseSchemasList(metaData);

        for(String schemaName : schemaNameList) {
            TreeNode schemaNode = new TreeNode();
            schemaNode.setLabel(schemaName);
            schemaNode.setNodeType("SCHEMA-NODE");
            schemaNode.setIcon("sd storage");
            schemaNode.setNoTick(true);
            schemaList.add(schemaNode);
        }

        //设置表/视图子节点
        for(TreeNode schemaNode : schemaList){
            List<TreeNode> childNode = new ArrayList<>();

            TreeNode tableNode = new TreeNode();
            tableNode.setLabel("TABLE");
            tableNode.setNodeType("TABLE-NODE");
            tableNode.setIcon("folder");
            tableNode.setSchema(schemaNode.getLabel());
            tableNode.setLazy(true);
            tableNode.setChildren(new ArrayList<>());
            tableNode.setNoTick(true);

            TreeNode viewNode = new TreeNode();
            viewNode.setLabel("VIEW");
            viewNode.setNodeType("VIEW-NODE");
            viewNode.setIcon("folder open");
            viewNode.setLazy(true);
            viewNode.setSchema(schemaNode.getLabel());
            viewNode.setChildren(new ArrayList<>());
            viewNode.setNoTick(true);

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
    public TreeNode getDatabaseInfoTree() throws AppException {

        DatabaseMetaData metaData = getDbMetaUtils().getMetaData();

        //1. 获取数据库根节点
        TreeNode rootNode = getDatabaseRootNode(metaData);

        //2. 获取数据库shcemas
        List<TreeNode> schemaList = getSchemaTreeNodeList(metaData);

        if (schemaList.size() > 0) {
            rootNode.setChildren(schemaList);
        }

        return rootNode;
    }

    /**
     * 填充Shcema下 Table节点
     * @param objectResultSet
     * @param schema
     * @param tableList
     * @throws SQLException
     */
    private void fillSchemaObjectNode(ResultSet objectResultSet, String schema
            , List<TreeNode> tableList) throws AppException{
        try{
            while (objectResultSet.next()){
                TreeNode leafNode = new TreeNode();
                String tableName = objectResultSet.getString("TABLE_NAME");
                String tabletType = objectResultSet.getString("TABLE_TYPE");
                leafNode.setLabel(tableName);
                leafNode.setSchema(schema);
                leafNode.setNodeType(tabletType+"-LEAF");
                leafNode.setIcon("description");
                tableList.add(leafNode);
            }
        } catch (SQLException ex) {
            throw new AppException(ex);
        }
    }

    /**
     * 获取Schema下所有表对象
     * @param schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<TreeNode> getSchemaTables(String schema) throws AppException {

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        ResultSet tableRs = null;
        try{
            List<TreeNode> tableList = new ArrayList<>();
            tableRs = meta.getTables(null, schema, null, new String[]{"TABLE"});
            fillSchemaObjectNode(tableRs, schema, tableList);
            return tableList;
        } catch (SQLException ex) {
            throw new AppException("获取shcema下所有表对象失败!", ex);
        } finally {
            getDbMetaUtils().closeResultSet(tableRs);
        }
    }

    /**
     * 获取Schema下所有视图对象
     * @param schema
     * @return
     * @throws SQLException
     */
    @Override
    public List<TreeNode> getSchemaViews(String schema) throws AppException {

        DatabaseMetaData meta = getDbMetaUtils().getMetaData();
        ResultSet viewRs = null;
        try {
            List<TreeNode> viewList = new ArrayList<>();
            viewRs = meta.getTables(null, schema, null, new String[]{"VIEW"});
            fillSchemaObjectNode(viewRs, schema, viewList);
            return viewList;
        } catch (SQLException ex) {
            throw new AppException("获取shcema下所有视图对象失败!", ex);
        } finally {
            getDbMetaUtils().closeResultSet(viewRs);
        }
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

    /**
     * 获取表字段详情
     * @param table
     * @return
     * @throws SQLException
     */
    @Override
    public List<Column> getTableColumns(Table table) throws AppException {
        ResultSet primaryKeyRs = null,
                uniqueKeyRs = null,
                columnRs = null;

        DatabaseMetaData meta = dbMetaUtils.getMetaData();
        try {
            String schema = table.getSchema();
            String tableName = table.getTableName();

            List<Column> columnList = new ArrayList<Column>();
            List<String> primaryKeyList = new ArrayList<String>();
            List<String> uniqueKeyList = new ArrayList<String>();

            //主键
            primaryKeyRs = meta.getPrimaryKeys(null, schema, tableName);
            while (primaryKeyRs.next()) {
                String columnName = primaryKeyRs.getString("COLUMN_NAME");
                primaryKeyList.add(columnName);
            }

            //唯一键
            uniqueKeyRs = meta.getIndexInfo(null, schema, tableName, true, true);
            while (uniqueKeyRs.next()) {
                String columnName = uniqueKeyRs.getString("COLUMN_NAME");
                uniqueKeyList.add(columnName);
            }

            columnRs = meta.getColumns(null, schema, tableName, null);

            while (columnRs.next()) {
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
                column.setColumnCategory(column.calculateColumnCategory());
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
        } catch (SQLException ex) {
            throw new AppException("获取表字段详情失败!", ex);
        } finally {
            dbMetaUtils.closeResultSet(primaryKeyRs);
            dbMetaUtils.closeResultSet(uniqueKeyRs);
            dbMetaUtils.closeResultSet(columnRs);
        }
    }
}
