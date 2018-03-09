<#--Service 模板-->
<#include "author_copyright.include">  
package ${Table.basePackage}.service;

import com.hand.hap.system.service.IBaseService;
import com.hand.hap.core.ProxySelf;
import java.util.List;
import com.hand.hap.core.IRequest;
import ${Table.basePackage}.dto.${Table.className};

public interface I${Table.className}Service <#if Table.objectType=="table" && Table.stdHapTable >extends IBaseService<${Table.className}>
    , ProxySelf<I${Table.className}Service><#elseif !Table.stdHapTable>extends ProxySelf<I${Table.className}Service></#if>{
    
<#if !Table.stdHapTable||Table.objectType=="view" >
    List<${Table.className}> selectByCondition(IRequest iRequest, ${Table.className} condition);

    List<${Table.className}> selectByConditionPage(IRequest iRequest, ${Table.className} condition, int pageNum, int pageSize);
</#if>
<#if Table.objectType=="table" && Table.stdHapTable >
    
    /** 根据是否有主键来更新或插入单条记录 */
    ${Table.className} insertOrUpdateByPrimaryKey(IRequest iRequest, ${Table.className} dto);

    /** 根据是否有主键来更新或插入 */
    List<${Table.className}> batchUpdateByPrimaryKey(IRequest iRequest, List<${Table.className}> list);
    
    /** 根据条件查询符合条件的全部记录，不分页 */
    List<${Table.className}> select(IRequest iRequest, ${Table.className} condition);
</#if>

}
