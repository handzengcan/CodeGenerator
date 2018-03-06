package tech.jebsun.codegenerator.enums;

/**
 * Java类型枚举
 * Created by JebSun on 2018/3/1.
 */
public enum  JavaTypeEnum {

    TYPE_BYTE("Byte", "java.lang.Byte", true),
    TYPE_SHORT("Short","java.lang.Short", true),
    TYPE_INTEGER("Integer","java.lang.Integer", true),
    TYPE_LONG("Long","java.lang.Long", true),
    TYPE_FLOAT("Float","java.lang.Float", true),
    TYPE_DOUBLE("Double","java.lang.Double", true),
    TYPE_STRING("String","java.lang.String", true),
    TYPE_BOOLEAN("Boolean","java.lang.Boolean", true),
    TYPE_OBJECT("Object","java.lang.Object", true),
    TYPE_DATE("Date","java.util.Date", false),
    TYPE_BYTE_ARRAY("byte[]","byte[]", true),
    TYPE_BIGDECIMAL("BigDecimal","java.math.BigDecimal", false);

    private String typeName;

    private String fullTypeName;

    private Boolean javaLang;

    JavaTypeEnum (String typeName, String fullTypeName, Boolean javaLang) {
        this.typeName = typeName;
        this.fullTypeName = fullTypeName;
        this.javaLang = javaLang;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFullTypeName() {
        return fullTypeName;
    }

    public void setFullTypeName(String fullTypeName) {
        this.fullTypeName = fullTypeName;
    }

    public Boolean getJavaLang() {
        return javaLang;
    }

    public void setJavaLang(Boolean javaLang) {
        this.javaLang = javaLang;
    }

    /**
     * 通过类型简称获取类型枚举
     * @param javaTypeName
     * @return
     */
    public static JavaTypeEnum getJavaTypeByShortName(String javaTypeName) {
        switch (javaTypeName) {
            case "Byte" : return JavaTypeEnum.TYPE_BYTE;
            case "Short" : return JavaTypeEnum.TYPE_SHORT;
            case "Integer" : return  JavaTypeEnum.TYPE_INTEGER;
            case "Float" : return JavaTypeEnum.TYPE_FLOAT;
            case "Long" : return JavaTypeEnum.TYPE_LONG;
            case "Double" : return JavaTypeEnum.TYPE_DOUBLE;
            case "String" : return JavaTypeEnum.TYPE_STRING;
            case "Boolean" : return JavaTypeEnum.TYPE_BOOLEAN;
            case "Object" : return JavaTypeEnum.TYPE_OBJECT;
            case "Date" : return JavaTypeEnum.TYPE_DATE;
            case "byte[]" : return JavaTypeEnum.TYPE_BYTE_ARRAY;
            case "BigDecimal" : return JavaTypeEnum.TYPE_BIGDECIMAL;
            default: return JavaTypeEnum.TYPE_OBJECT;
        }
    }
}
