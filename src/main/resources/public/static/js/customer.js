function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#name").val(),
        cusNo:$("#khno").val(),
        myd:$("#myd").combobox("getValue"),
        level:$("#level").combobox("getValue")
    })
}

function openCustomerAddDialog(){
    openDialog("dlg","客户添加");
}

function closeCustomerDialog(){
    closeDialog("dlg")
}

function clearFormData(){
    $("input[name='name']").val("");
    $("#area").val("");
    $("#cusManager").val("");
    $("#level").val("");
    $("#xyd").val("");
    $("#address").val("");
    $("#postCode").val("");
    $("#phone").val("");
    $("#fax").val("");
    $("#webSite").val("");
    $("#fr").val("");
    $("#zczj").val("");
    $("#nyye").val("");
    $("#khyh").val("");
    $("#khzh").val("");
    $("#gsdjh").val("");
    $("#dsdjh").val("");
    $("input[name='id']").val("");
}

function saveOrUpdateCustomer(){
    saveOrUpdateRecode(ctx+"/customer/save",ctx+"/customer/update","dlg",searchCustomersByParams,clearFormData);
}

function openCustomerModifyDialog(){
    openModifyDialog("dg","fm","dlg","客户修改");
}

function deleteCustomer(){
    deleteRecode("dg",ctx+"/customer/delete",searchCustomersByParams);
}

function openShowOrderTab(){

    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待查看的客户记录","warning");
    }

    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量查看客户记录","warning");
    }

    window.parent.openTab(rows[0].name+"_订单展示",ctx+"/customer/order_info?cid="+rows[0].id);
}
