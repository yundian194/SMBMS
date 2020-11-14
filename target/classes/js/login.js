
var userCode = $("#userCode");
var userPassword = $("#userPassword");

$(document).ready(function () {
    $.ajax({
        type:"GET",
        url:"/loginServlet",
        data:{method:"loginVerify",userCode:userCode.val(),userPassword:userPassword.val()},
        dataType:"json",
        success:function (data) {
            if(data.result=="success"){
                $("#result").css("color","green");
                $("#login").attr("disabled",false);
            }else{
                $("#result").css("color","red");
                $("#login").attr("disabled",true);
            }
            $("#result").text(data.result);
        }
    });
    userCode.focus(function () {
        userCode.select();//获取焦点时 全选文本内容
    });
    userCode.keydown(function () {
        userPassword.val("");//当输入新的账号时  清空密码
    });
    userPassword.blur(function () {
            $.ajax({
                type:"GET",
                url:"/loginServlet",
                data:{method:"loginVerify",userCode:userCode.val(),userPassword:userPassword.val()},
                dataType:"json",
                success:function (data) {
                    if(data.result=="success"){
                        $("#result").css("color","green");
                        $("#login").attr("disabled",false);
                    }else{
                        $("#result").css("color","red");
                        $("#login").attr("disabled",true);
                    }
                    $("#result").text(data.result);
                }
            });
    });
});