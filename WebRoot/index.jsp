﻿<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="images/ico.png">
<link rel="shortcut icon" href="images/ico.png">
<title>SMMS体育赛事综合管理系统</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<link rel="stylesheet" type="text/css" href="css/subcss.css"/>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/zDialog.js"></script>
<script type="text/javascript" src="js/zDrag.js"></script>
<script language="javascript" >
<!--
//
function check() {
			var uname = $('#user').val();
			if (uname.length == 0) {
				Dialog.alert("用户名不能为空！！");
				//alert("用户名不能为空！！");
				return true;
			}
			//
			var password = $('#pwd').val();
			if (password.length == 0) {
				Dialog.alert("密码不能为空！！");
				//alert("密码不能为空！！");
				return false;
			}
			//
			var valiCode = $('#chknumber').val();
			if (valiCode.length == 0) {
				Dialog.alert("验证码不能为空！！");
				//alert("验证码不能为空！！");
				return false;
			}
			//
			var resu;
			$.ajax( {
				url : "servlet/LoginServlet",
				async : false,
				type : 'post',
				data : 'user=' + $('#user').val() + '&pwd=' + $('#pwd').val() + '&chknumber=' + $('#chknumber').val(),
				success : function(mm) {
					resu = mm;
				}
			});
			if (resu == "captchaerr") {
				Dialog.alert("验证码输入错误！！");
				//alert("无此用户，请核对输入值!!");
				return false;
			}else if(resu == "loginerr"){
				Dialog.alert("用户名或密码错误，请重新输入！！");
				return false;
			}
			//
			//document.login.action = "${pageContext.request.contextPath }/servlet/GotoMainWebServlet";
			document.login.submit();
			return true;
		}
//-->
</script>
</head>
<body>
<div id="top"> </div>
<form id="login" name="login" action="servlet/LoginServlet" method="post">
  <div id="center">
    <div id="center_left"></div>
    <div id="center_middle">
      <div class="user">
        <label>用户名：
        <input type="text" name="user" id="user" class="logininput" />
        </label>
      </div>
      <div class="user">
        <label>密　码：
        <input type="password" name="pwd" id="pwd" class="logininput" />
        </label>
      </div>
      <div class="chknumber">
        <label>验证码：
        <input name="chknumber" type="text" id="chknumber" maxlength="4" class="chknumber_input" />
        </label>
        <img src="patchca.png" id="safecode" width="50" height="20" align="absmiddle"/>
      </div>
    </div>
    <div id="center_middle_right"></div>
    <div id="center_submit">
      <div class="button"> <img src="images/dl.gif" width="57" height="20" onclick="check()" > </div>
      <div class="button"> <img src="images/cz.gif" width="57" height="20" onclick="form_reset()"> </div>
    </div>
    <div id="center_right"></div>
  </div>
</form>
<div id="footer"></div>
</body>
</html>
