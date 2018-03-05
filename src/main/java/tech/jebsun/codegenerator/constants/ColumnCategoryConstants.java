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

}
