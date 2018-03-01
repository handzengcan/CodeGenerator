package tech.jebsun.codegenerator.entity;


/**
 * 数据库表字段实体类
 * Created by JebSun on 2018/2/28.
 */
public class Column {

    /**
     * 数据库字段名称
     */
    private String columnName;

    /**
     * 数据库字段类型
     */
    private int columnDataBaseType;

    /**
     * 数据库字段类型名称
     */
    private String columnDataBaseTypeName;

    /**
     * 数据库字段长度
     */
    private int columnSize;

    /**
     * 数据库字段备注
     */
    private String columnComment;

    /**
     * 数据库字段默认值
     */
    private String  defaultValue;

    /**
     * 是否主键
     */
    private boolean primaryKey;

    /**
     * 是否可空
     */
    private boolean nullable;

    /**
     * 是否唯一
     */
    private boolean unique;


    /**
     * 数据库字段名称转驼峰形式
     * 作为Java实体类属性名称
     */
    private String javaProperty;

    /**
     * Java实体类属性名名称首字母大写
     */
    private String javaPropertyFirstUpper;

    /**
     * 字段对应Java类型名称
     */
    private String javaTypeName;

    /**
     * 字段对应Java类型全称 如java.lang.String
     */
    private String fullJavaTypeName;

    /**
     * 字段类型 判断字段是否who字段/HAP标准字段等
     */
    private String columnCategory;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnDataBaseType() {
        return columnDataBaseType;
    }

    public void setColumnDataBaseType(int columnDataBaseType) {
        this.columnDataBaseType = columnDataBaseType;
    }

    public String getColumnDataBaseTypeName() {
        return columnDataBaseTypeName;
    }

    public void setColumnDataBaseTypeName(String columnDataBaseTypeName) {
        this.columnDataBaseTypeName = columnDataBaseTypeName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getJavaProperty() {
        return javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public String getJavaPropertyFirstUpper() {
        return javaPropertyFirstUpper;
    }

    public void setJavaPropertyFirstUpper(String javaPropertyFirstUpper) {
        this.javaPropertyFirstUpper = javaPropertyFirstUpper;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public String getFullJavaTypeName() {
        return fullJavaTypeName;
    }

    public void setFullJavaTypeName(String fullJavaTypeName) {
        this.fullJavaTypeName = fullJavaTypeName;
    }

    public String getColumnCategory() {
        return columnCategory;
    }

    public void setColumnCategory(String columnCategory) {
        this.columnCategory = columnCategory;
    }
}
