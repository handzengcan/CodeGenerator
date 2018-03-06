/**
* 根据字段描述生成sysPrompt,将本文见内容拷贝至liquibase EXCEL
* @author Jeb Sun
* @version 1.0
*/

*PROMPT_ID	#PROMPT_CODE	#LANG	DESCRIPTION
<#list Table.columnList as column>
<#if !Table.stdHapTable||(column.columnCategory!="Hap-Who"&&column.columnCategory!="Hap-Extend"&&column.columnCategory!="Hap-Other")>
*	${Table.className?lower_case}.${column.javaProperty?lower_case}	zh_CN	${column.columnComment}
*	${Table.className?lower_case}.${column.javaProperty?lower_case}	en_GB	${column.javaProperty}
</#if>
</#list>