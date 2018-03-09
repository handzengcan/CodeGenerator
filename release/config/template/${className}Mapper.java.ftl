<#include "author_copyright.include">
package ${Table.basePackage}.mapper;
import java.util.List;
import com.hand.hap.mybatis.common.Mapper;
import ${Table.basePackage}.dto.${Table.className};

public interface ${Table.className}Mapper <#if Table.objectType=="table">extends Mapper<${Table.className}></#if>{
    <#if !Table.stdHapTable||Table.objectType=="view" >
    List<${Table.className}> selectByCondition(${Table.className} condition);
    </#if>
}
