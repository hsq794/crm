/**
 * tab 选项卡
 * @param text
 * @param url
 * @param iconCls
 */

function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/**
 * 安全退出账户
 */
function logout(){
    $.messager.confirm("来自crm","确定退出系统?",function(r){
        if(r){
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.messager.alert("来自crm","系统将在三秒后自动退出...","info");
            setTimeout(function (){
                window.location.href=ctx+"/index";
            },3000)
        }
    })
}

/**
 * 打开修改密码弹出框
 */
function openPasswordModifyDialog(){
    $("#dlg").dialog("open").dialog("setTitle","密码修改");

}

/**
 * 修改密码,保存按钮
 */

function modifyPassword(){
   $("#fm").form("sumbit",{
       url:ctx+"/user/updatePassword",
       onSubmit:function () {
           return $("#fm").form("validate");
       },
       success:function (data) {
           data=JSON.parse(data);
           if(data.code==200){
               $.messager.alert("来自crm","密码修改成功，系统将在5秒后执行退出操作","info");
               $.removeCookie("userIdStr");
               $.removeCookie("userName");
               $.removeCookie("trueName");
               setTimeout(function () {
                   window.location.href=ctx+"/index";
               },5000)
           }else{
               $.messager.alert("来自crm",data.msg,"error");
           }

       }
   })
}