package tech.jebsun.codegenerator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.exceptions.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by JebSun on 2018/2/28.
 */
@Component
public class DBMetaUtils {

    private static Logger logger = LoggerFactory.getLogger(DBMetaUtils.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private static DatabaseMetaData databaseMetaData;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws AppException
     */
    public synchronized Connection getConnection() throws AppException {
        try {
            logger.info("DataSource :" + dataSource);
            Connection connection = dataSource.getConnection();
            logger.info("Get Connection : Thread Name = " + Thread.currentThread().getName() + " Connection Name:" + connection);
            return connection;
        } catch (SQLException ex) {
            throw new AppException("连接数据库失败!", ex);
        }
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public synchronized void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                logger.info("Release Connection : Thread Name = " + Thread.currentThread().getName() + " Connection Name:" + connection);
                connection.close();
            } catch (SQLException ex) {
                logger.error("关闭连接失败!");
            }
        }
    }

    /**
     * 关闭ResultSet
     * @param resultSet
     */
    public void closeResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error("关闭ResultSet失败!");
            }
        }
    }

    /**
     * 获取数据库元数据
     * @return
     * @throws SQLException
     */
    public synchronized DatabaseMetaData getMetaData() throws AppException {
        Connection connection = getConnection();
        try {
            if(databaseMetaData == null)
                databaseMetaData = connection.getMetaData();
            return databaseMetaData;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new AppException("获取数据库元数据失败! ", ex);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * 获取数据库类型
     * @return
     */
    public DataBaseTypeEnum getDataBaseType() throws AppException {
        DatabaseMetaData metaData = getMetaData();
        try {
            String dataBaseName = metaData.getDatabaseProductName();
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
