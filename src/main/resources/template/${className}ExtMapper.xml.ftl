<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<#--Mapper XML 模板--> 
<mapper namespace="${Table.basePackage}.mapper.${Table.className}Mapper">
    
    <#if !Table.stdHapTable||Table.objectType=="view" >
    <select id="selectByCondition" resultMap="DefResultMap" parameterType="${Table.basePackage}.dto.${Table.className}" >
        select <include refid="DefColumnList" />
          from ${Table.catalog}.${Table.tableName} t
         <if test="_parameter != null" >
           <include refid="DefWhereClause" />
         </if>
         <include refid="DefOrderClause" />
    </select>
    </#if>

</mapper>