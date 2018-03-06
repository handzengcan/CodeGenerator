<#--Service Impl 模板-->
<#include "author_copyright.include">  
package ${Table.basePackage}.service.impl;

import java.util.List;

import ${Table.basePackage}.dto.${Table.className};
import ${Table.basePackage}.mapper.${Table.className}Mapper;
import ${Table.basePackage}.service.I${Table.className}Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class ${Table.className}ServiceImpl <#if Table.objectType=="table" && Table.stdHapTable>extends BaseServiceImpl<${Table.className}></#if> 
    implements I${Table.className}Service{
    
    @Autowired
    private ${Table.className}Mapper mapper;
        
    <#if !Table.stdHapTable||Table.objectType=="view" >
    //条件查询，获取全部
    @Override
    public List<${Table.className}> selectByCondition(IRequest iRequest,
            ${Table.className} condition) {
        return mapper.selectByCondition(condition);
    }
    //条件查询，分页
    @Override
    public List<${Table.className}> selectByConditionPage(IRequest iRequest,
            ${Table.className} condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectByCondition(condition);
    }
    </#if>
    
    <#if Table.objectType=="table" && Table.stdHapTable >
    /**查询符合条件的所有记录，不分页*/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS) 
    public List<${Table.className}> select(IRequest iRequest, ${Table.className} condition) {
        return mapper.select(condition);
    }
    
    /**根据是否有主键来更新或插入单条记录*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${Table.className} insertOrUpdateByPrimaryKey(IRequest iRequest, ${Table.className} dto) {        
        if(dto == null){
            return dto;
        }
        if(dto.get${Table.primaryKeyColunm.javaProperty}() == null ){
            self().insertSelective(iRequest, dto);
        }else{
            self().updateByPrimaryKeySelective(iRequest, dto);
        }
        return dto;
    }
    
    /**根据是否有主键来更新或插入*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<${Table.className}> batchUpdateByPrimaryKey(IRequest iRequest, List<${Table.className}> list) {
        //获取自身代理self()
        for (${Table.className} l : list) {
            self().insertOrUpdateByPrimaryKey(iRequest, l);
        }
        return list;
    }
    </#if>
}
