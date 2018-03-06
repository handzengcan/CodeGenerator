package tech.jebsun.codegenerator.service.impl;

import org.springframework.stereotype.Service;
import tech.jebsun.codegenerator.exceptions.AppException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JebSun on 2018/2/28.
 */
@Service
public class MysqlGeneratorServiceImpl extends BaseGeneratorServiceImpl {

    /**
     * MySql 获取Schema 需要从MetaData的Catalogs中获取
     * @return
     * @throws SQLException
     */
    @Override
    public List<String> getDatabaseSchemasList() throws AppException {
        try {
            ResultSet schemaRs = getDbMetaUtils().getMetaData().getCatalogs();
            List<String> schemaList = new ArrayList<>();
            while (schemaRs.next()) {
                String schema = schemaRs.getString("TABLE_CAT");
                if (schema != null) {
                    schemaList.add(schema);
                }
            }
            return schemaList;
        }catch (SQLException ex) {
            throw new AppException(ex);
        }
    }

}
