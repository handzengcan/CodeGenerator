<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<#--Mapper XML 模板--> 
<mapper namespace="${Table.basePackage}.mapper.${Table.className}Mapper">
    <resultMap id="DefResultMap" type="${Table.basePackage}.dto.${Table.className}" >
<#list Table.columnList as column>
    <#if column.primaryKey>
      <id column="${column.columnName}" property="${column.javaProperty}" />
    <#else>
      <result column="${column.columnName}" property="${column.javaProperty}" />
    </#if>
</#list>
    </resultMap>
    
    <sql id="DefColumnList" >
       <trim suffixOverrides=",">
<#list Table.columnList as column>
              t.${column.columnName},
</#list>
       </trim>
    </sql>
    
    <sql id="DefWhereClause" >
       <trim prefix="WHERE" prefixOverrides="AND | OR">
<#list Table.columnList as column>
         <if test="${column.javaProperty} != null" >
            AND  ${column.columnName} = ${"#{"} ${column.javaProperty} }
         </if>
</#list>
       </trim>    
    </sql>
    
    <#if !Table.stdHapTable||Table.objectType=="view" >
    <sql id="DefOrderClause">
<#list Table.columnList as column>
      <if test="sortname == '${column.javaProperty}' " >
        <choose>
             <when test="sortorder != null">
                  ORDER BY ${column.columnName} ${"$"}{sortorder}
             </when>
             <otherwise>
                  ORDER BY ${column.columnName}
             </otherwise>            
         </choose>
      </if>
</#list>
    </sql>
    </#if>
    

</mapper>