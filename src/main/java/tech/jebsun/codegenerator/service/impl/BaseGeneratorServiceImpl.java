package tech.jebsun.codegenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tech.jebsun.codegenerator.service.IGeneratorService;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JebSun on 2018/2/27.
 */
@Service
public class BaseGeneratorServiceImpl implements IGeneratorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection().getMetaData();
    }

    @Override
    public List<Map<String,String>> getAllSchemas() throws SQLException {

        DatabaseMetaData databaseMetaData = getMetaData();
        ResultSet schemaRs = databaseMetaData.getCatalogs();
        List<Map<String,String>> schemaList = new ArrayList<>();
        while (schemaRs.next()) {
            String schema = schemaRs.getString("TABLE_CAT");
            if(schema!=null){
                Map<String,String> schemaMap = new HashMap<String,String>();
                schemaMap.put("name", schema);
                schemaMap.put("value", schema);
                schemaList.add(schemaMap);
            }
        }
        return schemaList;
    }

}
