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
    $("#name").val("");
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