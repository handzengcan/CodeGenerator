package tech.jebsun.codegenerator.utils;

import tech.jebsun.codegenerator.constants.ColumnCategoryConstants;
import tech.jebsun.codegenerator.entity.Column;
import tech.jebsun.codegenerator.enums.JavaTypeEnum;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型转Java类型表决器
 * Created by JebSun on 2018/3/1.
 */
public class JavaTypeResolver {

    private static boolean forceBigDecimals = false;
    private static boolean forceBigLong = true;

    private static Map<Integer, JavaTypeEnum> jdbcTypeMap;

    static {

        jdbcTypeMap = new HashMap<Integer, JavaTypeEnum>();
        jdbcTypeMap.put(Types.ARRAY, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.BIGINT, JavaTypeEnum.TYPE_LONG);
        jdbcTypeMap.put(Types.BINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        jdbcTypeMap.put(Types.BIT, JavaTypeEnum.TYPE_BOOLEAN);
        jdbcTypeMap.put(Types.BLOB, JavaTypeEnum.TYPE_BYTE_ARRAY);
        jdbcTypeMap.put(Types.BOOLEAN, JavaTypeEnum.TYPE_BOOLEAN);
        jdbcTypeMap.put(Types.CHAR, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.CLOB, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.DATALINK, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.DATE, JavaTypeEnum.TYPE_DATE);
        jdbcTypeMap.put(Types.DISTINCT, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.DOUBLE, JavaTypeEnum.TYPE_DOUBLE);
        jdbcTypeMap.put(Types.FLOAT, JavaTypeEnum.TYPE_DOUBLE);
        jdbcTypeMap.put(Types.INTEGER, JavaTypeEnum.TYPE_INTEGER);
        jdbcTypeMap.put(Types.JAVA_OBJECT, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.LONGNVARCHAR, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.LONGVARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        jdbcTypeMap.put(Types.LONGVARCHAR, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.NCHAR, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.NCLOB, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.NVARCHAR, JavaTypeEnum.TYPE_STRING);
        jdbcTypeMap.put(Types.NULL, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.OTHER, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.REAL, JavaTypeEnum.TYPE_FLOAT);
        jdbcTypeMap.put(Types.REF, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.SMALLINT, JavaTypeEnum.TYPE_SHORT);
        jdbcTypeMap.put(Types.STRUCT, JavaTypeEnum.TYPE_OBJECT);
        jdbcTypeMap.put(Types.TIME, JavaTypeEnum.TYPE_DATE);
        jdbcTypeMap.put(Types.TIMESTAMP, JavaTypeEnum.TYPE_DATE);
        jdbcTypeMap.put(Types.TINYINT, JavaTypeEnum.TYPE_BYTE);
        jdbcTypeMap.put(Types.VARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        jdbcTypeMap.put(Types.VARCHAR, JavaTypeEnum.TYPE_STRING);
    }

    /**
     * 计算列Java类型
     *
     * @param column
     * @return
     */
    public static JavaTypeEnum calculateJavaType(Column column) {
        JavaTypeEnum answer = JavaTypeEnum.TYPE_OBJECT;
        JavaTypeEnum javaType = jdbcTypeMap.get(column.getColumnDataBaseType());

        if (javaType == null) {
            switch (column.getColumnDataBaseType()) {
                case Types.DECIMAL:
                case Types.NUMERIC:
                    //数字类型 HAP标准who other字段 统一设为Long
                    if(column.isPrimaryKey() || column.isStandardHapColumn())
                        answer = JavaTypeEnum.TYPE_LONG;
                    else
                        answer = calculateNumericType(column);
                    break;
                default:
                    answer = JavaTypeEnum.TYPE_OBJECT;
                    break;
            }
        } else {
            answer = javaType;
        }

        return answer;
    }

    /**
     * 计算数字列值Java类型
     *
     * @param column
     * @return
     */
    private static JavaTypeEnum calculateNumericType(Column column) {
        if (column.getColumnSize() > 18 || forceBigDecimals) {
            return JavaTypeEnum.TYPE_BIGDECIMAL;
        } else if (column.getColumnSize() > 9 || forceBigLong) {
            return JavaTypeEnum.TYPE_LONG;
        } else if (column.getColumnSize() > 4) {
            return JavaTypeEnum.TYPE_INTEGER;
        } else {
            return JavaTypeEnum.TYPE_SHORT;
        }
    }

}
