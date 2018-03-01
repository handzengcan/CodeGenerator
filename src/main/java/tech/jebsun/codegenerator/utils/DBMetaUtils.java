package tech.jebsun.codegenerator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;

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
     * 获取数据库元数据
     * @return
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection().getMetaData();
    }

    /**
     * 获取数据库类型
     * @return
     */
    public DataBaseTypeEnum getDataBaseType() throws SQLException{
        String dataBaseName = getMetaData().getDatabaseProductName();
        return getDataBaseType(dataBaseName);
    }

    /**
     * 获取数据库类型
     * @return
     */
    public DataBaseTypeEnum getDataBaseType(String dataBaseName) throws SQLException {

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
