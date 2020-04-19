function searchCustomerServeByParams(){
    $("#dg").datagrid("load",{
        customer:$("#s_customer").val(),
        type:$("#s_serveType").combobox("getValue")
    })
}

function openAssignDialog(){
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待分配的数据","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量分配!","warning");
        return;
    }
    $("#fm").form("load",rows[0]);
    openDialog("dlg","服务分配");
}

function addAssign() {
    $.ajax({
        type:"post",
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        data:{
            assigner:$("#s_assigner").combobox("getValue"),
            state:"fw_002",
            id:$("input[name='id']").val()
        },
        dataType:"json",
        success:function(data){
            if(data.code==200){
                closeDialog("dlg");
                searchCustomerServeByParams();
            }
        }
    })
}