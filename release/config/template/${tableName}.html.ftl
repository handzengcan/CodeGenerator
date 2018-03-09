${'<#include "../include/header.html">'}
<script type="text/javascript">
    var viewModel = Hap.createGridViewModel("#grid");
</script>
<div class="page-content">
    <div class="pull-left" id="toolbar-btn" style="padding-bottom:10px;">
        <span class="btn btn-sm btn-primary k-grid-add" style="float:left;margin-right:5px;" data-bind="click:create">${'<@spring.message "hap.new"/>'}</span>
        <span class="btn btn-sm btn-success k-grid-save-changes" data-bind="click:save" style="float:left;margin-right:5px;">${'<@spring.message "hap.save"/>'}</span>
        <span data-bind="click:remove" class="btn btn-sm btn-danger" style="float:left;">${'<@spring.message "hap.delete"/>'}</span>
    </div>
    <script>kendo.bind($('#toolbar-btn'), viewModel);</script>
    <div class="pull-right" id="query-form" style="padding-bottom:10px;">
    <#list Table.columnList as column>
        <#if column.javaProperty?ends_with("Code")||column.javaProperty?ends_with("Name")||column.javaProperty?ends_with("Type")>
            <input type="text" data-role="maskedtextbox" style="float:left;width:150px;margin-right:5px;" placeholder='${'<@spring.message '}"${Table.className?lower_case}.${column.javaProperty?lower_case}"/>'
                   data-bind="value:model.${column.javaProperty}" class="k-textbox">
        </#if>
    </#list>
        <span class="btn btn-sm btn-primary" style="float:left;width:70px" data-bind="click:query" type="submit">${'<@spring.message "hap.query"/>'}</span>
        <div style="clear:both"></div>
    </div>
    <script>kendo.bind($('#query-form'), viewModel);</script>
    <div style="clear:both">
        <div id="grid"></div>
    </div>
</div>

<script type="text/javascript">
    Hap.initEnterQuery('#query-form', viewModel.query);
    var BaseUrl = _basePath;
    dataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: BaseUrl + "<#if Table.childSysName??>/${Table.childSysName}</#if>/${Table.classNameFirstLowwer}/query",
                type: "POST",
                dataType: "json"
            },
            update: {
                url: BaseUrl + "<#if Table.childSysName??>/${Table.childSysName}</#if>/${Table.classNameFirstLowwer}/submit",
                type: "POST",
                contentType: "application/json"
            },
            destroy: {
                url: BaseUrl + "<#if Table.childSysName??>/${Table.childSysName}</#if>/${Table.classNameFirstLowwer}/remove",
                type: "POST",
                contentType: "application/json"
            },
            create: {
                url: BaseUrl + "<#if Table.childSysName??>/${Table.childSysName}</#if>/${Table.classNameFirstLowwer}/submit",
                type: "POST",
                contentType: "application/json"
            },
            parameterMap: function (options, type) {
                if (type !== "read" && options.models) {
                    var datas = Hap.prepareSubmitParameter(options, type)
                    return kendo.stringify(datas);
                } else if (type === "read") {
                    return Hap.prepareQueryParameter(viewModel.model.toJSON(), options)
                }
            }
        },
        batch: true,
        serverPaging: true,
        pageSize: 10,
        schema: {
            data: 'rows',
            total: 'total',
            model: {
                id: "${Table.columnList[0].javaProperty}",
                fields: {
                 <#list Table.columnList as column>
                     <#if Table.objectType=="table"&&column.columnCategory!="Hap-Who"&&column.columnCategory!="Hap-Extend"&&column.columnCategory!="Hap-Other"&&!column.primaryKey&&!column.nullable>
                     ${column.javaProperty} : {
                         validation: {
                             required: true
                         }
                     },
                     </#if>
                 </#list>
                }
            }
        }
    });

    $("#grid").kendoGrid({
        dataSource: dataSource,
        resizable: true,
        scrollable: true,
        navigatable: false,
        selectable: 'multiple, rowbox',
        dataBound: function () {
            if (parent.autoResizeIframe) {
                parent.autoResizeIframe('${'$'}{RequestParameters.functionCode!}')
            }
        },
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: true,
            buttonCount: 5
        },
        columns: [
        <#list Table.columnList as column>
            <#if !Table.stdHapTable||(column.columnCategory!="Hap-Who"&&column.columnCategory!="Hap-Extend"&&column.columnCategory!="Hap-Other")>
            {
                field: "${column.javaProperty}",
                title: '${'<@spring.message'} "${Table.className?lower_case}.${column.javaProperty?lower_case}"/>',
                width: 120,
                <#if column.primaryKey>
                hidden: true
                </#if>
            },
            </#if>
        </#list>
        ]<#if Table.objectType=="table">,
        editable: true
        </#if>
    });

</script>
${'</body>'}
${'</html>'}