package tech.jebsun.codegenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Oracle代码生成器实现类
 * Created by JebSun on 2018/2/28.
 */
@Service
public class OracleGeneratorServiceImpl extends BaseGeneratorServiceImpl{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取表字段备注
     * @param columnRs
     * @return
     */
    @Override
    public String getColumComment(ResultSet columnRs, String tableName, String columnName) {
        String comment = "";
        final String commentSql = "SELECT comments FROM hcrm.user_col_comments WHERE table_name=? AND column_name = ?";
        try {
            Object[] args = new Object[]{tableName, columnName};
            comment = jdbcTemplate.queryForObject(commentSql, args, String.class);
        } catch (Exception ex) {

        }

        return comment;
    }
}
