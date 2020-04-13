function formatterGrade(grade) {
    if(grade==0){
        return "一级菜单";
    }
    if(grade==1){
        return "二级菜单";
    }
    if(grade==3){
        return "三级菜单";
    }
}

/**
 * 操作按钮
 * @param value
 * @param rowData
 */
function formatterOp(value,rowData) {
    var title=rowData.moduleName+"_二级菜单";
    var href='javascript:openSecondModule("'+title+'",'+rowData.id+')';
    return "<a href='"+href+"'>二级菜单</a>";
}

function openSecondModule(title,mid) {
    console.log(mid);
    window.parent.openTab(title,ctx+"/module/index/2?mid="+mid);
}
/**
 * 搜索
 */
function searchModules(){
    $("#dg").datagrid("load",{
        moduleName:$("#s_moduleName").val(),
        code:$("#s_code").val()
    })
}
