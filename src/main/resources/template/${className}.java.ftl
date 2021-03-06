<#--DTO 模板-->
<#include "author_copyright.include">

package ${Table.basePackage}.dto;

import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;

<#list Table.packageImports as import>
import ${import};
</#list>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

<#if Table.objectType=="table">
@Table(name = "${Table.tableName}")
</#if>
<#--是否禁用扩展字段-->
<#if Table.disableExtension=="Y">
@ExtensionAttribute(disable = true)
</#if>
public class ${Table.className} <#if Table.objectType=="table"&&Table.stdHapTable>extends BaseDTO</#if> {

<#list Table.columnList as column>
<#--主键字段-->
<#if column.primaryKey>
    @Id//主键
        <#if Table.generatedValue=="UUID">
    @GeneratedValue(generator = "UUID")
        <#else>
    @GeneratedValue(generator = "IDENTITY")
        </#if>
    private ${column.fullJavaTypeName} ${column.javaProperty};
<#--扩展字段 标准Who字段 系统字段(继承BaseDto不生成)-->
<#elseif Table.stdHapTable&&(column.columnCategory=="Hap-Extend"||column.columnCategory=="Hap-Who"||column.columnCategory=="Hap-Other")>
    <#--nothing-->
<#else>

<#if Table.objectType=="table">
    <#if column.nullable>@NotNull</#if><#rt>
    <#if column.javaTypeName == "String">${"\n"}    @Length(max = ${column.columnSize})</#if>
</#if>
    private ${column.javaTypeName} ${column.javaProperty}; <#if column.columnComment!="">//${column.columnComment}</#if>

</#if>
</#list>

<#if !Table.stdHapTable||Table.objectType=="view" >
    /**
     * 排序字段.
     */
    @JsonInclude(Include.NON_NULL)
    @Transient
    private String sortname;

    /**
     * 排序方式.
     */
    @JsonInclude(Include.NON_NULL)
    @Transient
    private String sortorder;

</#if>
    <#--get set方法-->
    <@generateJavaColumns/>

<#if !Table.stdHapTable||Table.objectType=="view">
    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }
</#if>
}

<#macro generateJavaColumns>
<#list Table.columnList as column>
<#--扩展字段-->
    <#if Table.stdHapTable&&(column.columnCategory=="Hap-Extend"||column.columnCategory=="Hap-Who"||column.columnCategory=="Hap-Other")>
    <#--nothing-->
    <#else>
    <#if column.columnComment!="">
    /**
     *  get方法
     *  ${column.javaPropertyFirstUpper}  ${column.columnName}
     *  ${column.columnComment}
     */
    </#if>
    public void set${column.javaPropertyFirstUpper}(${column.javaTypeName} value) {
        this.${column.javaProperty} = value;    
    }
    
    <#if column.columnComment!="">
    /**
     *  set方法
     *  ${column.javaPropertyFirstUpper}  ${column.columnName}
     *  ${column.columnComment}
     */
    </#if>
    public ${column.fullJavaTypeName} get${column.javaPropertyFirstUpper}() {
        return this.${column.javaProperty};
    }
</#if>
</#list>
</#macro>