package tech.jebsun.codegenerator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.exceptions.AppException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Created by JebSun on 2018/2/28.
 */
@Component
public class DBMetaUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws AppException
     */
    public Connection getConnection() throws AppException {
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            if (connection == null)
                throw new AppException("连接数据库失败!");
            return connection;
        } catch (SQLException ex) {
            throw new AppException(ex);
        }
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {

            }
        }
    }

    /**
     * 获取数据库元数据
     * @return
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws AppException {
        Connection connection = getConnection();
        try {
            connection = getConnection();
            if (connection == null)
                throw new AppException("连接数据库失败!");
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            return databaseMetaData;
        } catch (SQLException ex) {
            throw new AppException("获取数据库元数据失败", ex);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 获取数据库类型
     * @return
     */
    public DataBaseTypeEnum getDataBaseType() throws AppException {
        try {
            String dataBaseName = getMetaData().getDatabaseProductName();
            return getDataBaseType(dataBaseName);
        } catch (SQLException ex) {
            throw new AppException("获取数据库类型失败!", ex);
        }
    }

    /**
     * 获取数据库类型
     * @return
     */
    public DataBaseTypeEnum getDataBaseType(String dataBaseName) {

        // 空类型
        if (null == dataBaseName || dataBaseName.trim().length() < 1) {
            return DataBaseTypeEnum.EMPTY;
        }
        // 截断首尾空格,转换为大写
        dataBaseName = dataBaseName.trim().toUpperCase();
        // Oracle数据库
        if (dataBaseName.contains("ORACLE")) {
            return DataBaseTypeEnum.ORACLE;
        }
        // MYSQL 数据库
        if (dataBaseName.contains("MYSQL")) {
            return DataBaseTypeEnum.MYSQL;
        }
        // SQL SERVER 数据库
        if (dataBaseName.contains("SQL") && dataBaseName.contains("SERVER")) {
            if (dataBaseName.contains("2005") || dataBaseName.contains("2008") || dataBaseName.contains("2012")) {
                /*
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                */
                return DataBaseTypeEnum.SQLSERVER2005;
            } else {
                /*
                try {
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                */
                return DataBaseTypeEnum.SQLSERVER;
            }
        }
        // DB2 数据库
        if (dataBaseName.contains("DB2")) {
            return DataBaseTypeEnum.DB2;
        }
        // INFORMIX 数据库
        if (dataBaseName.contains("INFORMIX")) {
            return DataBaseTypeEnum.INFORMIX;
        }
        // SYBASE 数据库
        if (dataBaseName.contains("SYBASE")) {
            return DataBaseTypeEnum.SYBASE;
        }

        // 默认,返回其他
        return DataBaseTypeEnum.OTHER;
    }
}
