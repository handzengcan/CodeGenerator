package tech.jebsun.codegenerator.service;

import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.entity.Table;
import tech.jebsun.codegenerator.entity.TreeNode;
import tech.jebsun.codegenerator.exceptions.AppException;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by JebSun on 2018/2/27.
 */
public interface IGeneratorService {

    /**
     * 获取数据库信息
     *
     * @return
     * @throws SQLException
     */
    TreeNode getDatabaseInfoTree() throws AppException;

    /**
     * 获取数据库信息树 根节点
     * 初始化数据库名称和版本号
     *
     * @return
     * @throws SQLException
     */
    TreeNode getDatabaseRootNode(DatabaseMetaData metaData) throws AppException;

    /**
     * 获取数据库下所有Schemas
     *
     * @return
     * @throws SQLException
     */
    List<String> getDatabaseSchemasList(DatabaseMetaData metaData) throws AppException;

    /**
     * 获取数据库下所有Schemas
     *
     * @return 树型节点列表
     * @throws SQLException
     */
    List<TreeNode> getSchemaTreeNodeList(DatabaseMetaData metaData) throws AppException;

    /**
     * 获取数据库下所有表对象
     *
     * @param schema
     * @return
     * @throws SQLException
     */
    List<TreeNode> getSchemaTables(String schema) throws AppException;

    /**
     * 获取数据库下所有视图对象
     * @param schema
     * @return
     * @throws SQLException
     */
    List<TreeNode> getSchemaViews(String schema) throws AppException;

    /**
     * 获取表所有列
     * @param table
     * @return
     * @throws SQLException
     */
    List<Column> getTableColumns(Table table) throws AppException;

}
