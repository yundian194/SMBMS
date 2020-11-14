<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市订单管理系统</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />

</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>超市订单管理系统</h1>
        </header>
        <section class="loginCont">
	        <form class="loginForm" action="/loginServlet"  name="actionForm" id="actionForm"  method="post" >
				<div class="info" id="result" style="color: red">
                </div>
                <input name="method" type="hidden" value="loginPro"><%--这个method只是一个属性的名字--%>
				<div class="inputbox">
                    <label for="userCode">用户名：</label>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" required/>
				</div>
				<div class="inputbox">
                    <label for="userPassword">密码：</label>
                    <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" required/>
                </div>	
				<div class="subBtn">
                    <input id = "login" type="submit" value="登录"/>
                    <input type="reset" value="重置"/>
                </div>
			</form>
        </section>
    </section>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/login.js"></script>
</body>
</html>
