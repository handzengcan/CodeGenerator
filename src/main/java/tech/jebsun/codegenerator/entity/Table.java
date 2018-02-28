package tech.jebsun.codegenerator.entity;

import java.util.List;

/**
 * 数据库表实体类
 * Created by JebSun on 2018/2/28.
 */
public class Table {

    /**
     * 数据库表所属schema
     */
    private String schema;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 驼峰名
     */
    private String className;

    /**
     * 驼峰名，首字母小写
     */
    private String classNameFirstLowwer;

    /**
     * 对象类型 "table" or "view"
     */
    private String objectType;

    /**
     * 主键策略
     */
    private String generatedValue;

    /**
     * 是否禁用扩展字段
     */
    private String disableExtension;

    /**
     * 生成代码 基包名称
     */
    private String basePackage;

    /**
     * HAP子工程名
     */
    private String childSysName;

    /**
     * 输出路径
     */
    private String outPath;

    /**
     * 是否包含全部标准Who字段
     */
    private Boolean includeWho;

    /**
     * 是否包含全部扩展字段
     */
    private Boolean includeExtend;

    /**
     * 是否包含字段REQUEST_ID、PROGRAM_ID、OBJECT_VERSION_NUMBER
     */
    private Boolean includeOther;

    /**
     * 是否标准Hap表
     */
    private Boolean stdHapTable;

    /**
     * 强制生成非标准表
     */
    private String forceGenerate;

    /**
     * 主键列
     */
    private Column primaryKeyColunm;

    /**
     * 包含的列，key的列的名称，value是列的具体信息
     */
    private List<Column> columnList;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameFirstLowwer() {
        return classNameFirstLowwer;
    }

    public void setClassNameFirstLowwer(String classNameFirstLowwer) {
        this.classNameFirstLowwer = classNameFirstLowwer;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getGeneratedValue() {
        return generatedValue;
    }

    public void setGeneratedValue(String generatedValue) {
        this.generatedValue = generatedValue;
    }

    public String getDisableExtension() {
        return disableExtension;
    }

    public void setDisableExtension(String disableExtension) {
        this.disableExtension = disableExtension;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getChildSysName() {
        return childSysName;
    }

    public void setChildSysName(String childSysName) {
        this.childSysName = childSysName;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public Boolean getIncludeWho() {
        return includeWho;
    }

    public void setIncludeWho(Boolean includeWho) {
        this.includeWho = includeWho;
    }

    public Boolean getIncludeExtend() {
        return includeExtend;
    }

    public void setIncludeExtend(Boolean includeExtend) {
        this.includeExtend = includeExtend;
    }

    public Boolean getIncludeOther() {
        return includeOther;
    }

    public void setIncludeOther(Boolean includeOther) {
        this.includeOther = includeOther;
    }

    public Boolean getStdHapTable() {
        return stdHapTable;
    }

    public void setStdHapTable(Boolean stdHapTable) {
        this.stdHapTable = stdHapTable;
    }

    public String getForceGenerate() {
        return forceGenerate;
    }

    public void setForceGenerate(String forceGenerate) {
        this.forceGenerate = forceGenerate;
    }

    public Column getPrimaryKeyColunm() {
        return primaryKeyColunm;
    }

    public void setPrimaryKeyColunm(Column primaryKeyColunm) {
        this.primaryKeyColunm = primaryKeyColunm;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }
}
