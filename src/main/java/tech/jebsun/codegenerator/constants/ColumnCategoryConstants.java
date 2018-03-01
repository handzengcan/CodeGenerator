package tech.jebsun.codegenerator.constants;

import tech.jebsun.codegenerator.entity.Column;

/**
 * Created by JebSun on 2018/2/28.
 */
public class ColumnCategoryConstants {

    /* 标准 WHO 字段
	 * CREATED_BY
	 * CREATION_DATE
	 * LAST_UPDATED_BY
	 * LAST_UPDATE_DATE
	 * LAST_UPDATE_LOGIN
	 * */
    public static final String  COLUMN_TYPE_HAP_WHO = "Hap-Who";
    public static final Integer COLUMN_TYPE_HAP_WHO_COUNT = 5;

    /* 扩展属性字段
     * ATTRIBUTE_CATEGORY
     * ATTRIBUTE1 - ATTRIBUTE15
     * */
    public static final String COLUMN_TYPE_HAP_EXTEND = "Hap-Extend";
    public static final Integer COLUMN_TYPE_HAP_EXTEND_COUNT = 16;

    /* REQUEST_ID: 对Record最后一次操作的系统内部请求id
     * PROGRAM_ID: 对Record最后一次操作的系统内部程序id
     * OBJECT_VERSION_NUMBER: Record的版本号，每发生update则自增.用于实现乐观锁,无法替代数据库锁,目前无应用. */
    public static final String  COLUMN_TYPE_HAP_OTHER = "Hap-Other";
    public static final Integer COLUMN_TYPE_HAP_OTHER_COUNT = 3;
    /*
     * 普通字段
     */
    public static final String COLUMN_TYPE_NORMAL = "Normal";
    /*
     * 普通字段
     */
    public static final String COLUMN_TYPE_UNKNOWN = "Unknown";

    /**
     * 判断字段类型
     * @param columnName 列数据库名称
     * @return
     */
    public static String calculateFieldCategory(String columnName){

        if(columnName == null || columnName.equals("")){
            return COLUMN_TYPE_UNKNOWN;
        }
        switch (columnName.toUpperCase()) {
            case "ATTRIBUTE_CATEGORY":
            case "ATTRIBUTE1":
            case "ATTRIBUTE2":
            case "ATTRIBUTE3":
            case "ATTRIBUTE4":
            case "ATTRIBUTE5":
            case "ATTRIBUTE6":
            case "ATTRIBUTE7":
            case "ATTRIBUTE8":
            case "ATTRIBUTE9":
            case "ATTRIBUTE10":
            case "ATTRIBUTE11":
            case "ATTRIBUTE12":
            case "ATTRIBUTE13":
            case "ATTRIBUTE14":
            case "ATTRIBUTE15":
                return COLUMN_TYPE_HAP_EXTEND;
            case "OBJECT_VERSION_NUMBER":
            case "REQUEST_ID":
            case "PROGRAM_ID":
                return COLUMN_TYPE_HAP_OTHER;
            case "CREATED_BY":
            case "CREATION_DATE":
            case "LAST_UPDATED_BY":
            case "LAST_UPDATE_DATE":
            case "LAST_UPDATE_LOGIN":
                return COLUMN_TYPE_HAP_WHO;
            default:
                return COLUMN_TYPE_NORMAL;
        }
    }

    /**
     * 判断是否HAP标准字段
     * @param column
     * @return
     */
    public static boolean isStandardHapColumn(Column column) {

        if(column == null || column.getColumnName() == null)
            return false;

        String columnCategory = calculateFieldCategory(column.getColumnName());

        if(COLUMN_TYPE_HAP_WHO.equals(columnCategory) || COLUMN_TYPE_HAP_OTHER.equals(columnCategory))
            return true;

        return false;
    }

}
