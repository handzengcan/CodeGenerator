package tech.jebsun.codegenerator.service;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by JebSun on 2018/2/27.
 */
public interface IGeneratorService {

    /**
     * 获取数据库元数据
     * @return
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException;

    /**
     * 获取数据库下所有Schemas
     * @return
     * @throws SQLException
     */
    public List<Map<String,String>> getAllSchemas() throws SQLException;

    //public List<Table> getAllTables() throws SQLException;

    //public List<Object> getTables(Table table) throws SQLException;

    //public List<Column> getTableColumns(Table table) throws SQLException;

    //public TreeNode getDatabaseInfoTree() throws SQLException;

    //public List<Object> getAllJavaTypes();

}
