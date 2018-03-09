<#--DTO 模板-->
<#include "author_copyright.include">
<#assign className = Table.className>   
<#assign classNameLower = Table.classNameFirstLowwer>  
<#assign basepackage = Table.basePackage> 
package ${basepackage}.controllers;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import ${basepackage}.dto.${className};
import ${basepackage}.service.I${className}Service;

@Controller
@RequestMapping("<#if Table.childSysName??>/${Table.childSysName}</#if>/${classNameLower}")
public class ${className}Controller  extends BaseController{
    
    @Autowired
    private I${className}Service ${classNameLower}Service;
    
    <#if Table.objectType=="table">
    @RequestMapping("/query")
    @ResponseBody
    public ResponseData select${className}s(${className} condition, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(${classNameLower}Service.select(iRequest, condition, page, pagesize));
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public ResponseData selectAll${className}s(${className} condition, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(${classNameLower}Service.select(iRequest, condition));
    }
    </#if>
    
    <#if !Table.stdHapTable||Table.objectType=="view" >
    @RequestMapping("/queryConditionPage")
    @ResponseBody
    public ResponseData selectList(${className} condition, HttpServletRequest request, 
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        List<${className}> datas = ${classNameLower}Service.selectByConditionPage(iRequest, condition, page ,pagesize);
        return new ResponseData(datas);
    }
    </#if>
    
    <#if Table.objectType=="table">    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit${className}s(@RequestBody List<${className}> ${classNameLower}s, BindingResult result, HttpServletRequest request)
            throws BaseException {
        getValidator().validate(${classNameLower}s, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            IRequest requestCtx = createRequestContext(request);
            ${classNameLower}Service.batchUpdate(requestCtx, ${classNameLower}s);
            return new ResponseData(${classNameLower}s);
        }
    }
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseData remove${className}s(@RequestBody List<${className}> ${classNameLower}s,BindingResult result,HttpServletRequest request) throws BaseException {                
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            ${classNameLower}Service.batchDelete(${classNameLower}s);
            return new ResponseData(${classNameLower}s);
        }
    }
    </#if>

}
