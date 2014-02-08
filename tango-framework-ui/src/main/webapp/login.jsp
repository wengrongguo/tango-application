<%--
  User: tango
  Date: 13-6-5
  Time: 下午6:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <link href="${stp}/login/login.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" charset="UTF-8" src="${scriptPath}/jquery/jquery-1.8.0.min.js"></script>
    <script src="${scp}/login/login.js" type="text/javascript"></script>
</head>
<body>
<div style="width: 525px; margin: auto; position: relative;">
    <div class="dl"></div>
    <div style="width: 329px; height: 157px; top: 211px; left: 99px; position: absolute;">
        <input type="text" id="account" class="yhm" value="admin"/>
        <input type="password" id="password" class="mm" value="123456"/>
        <input type="submit" class="anniu" onclick="login();" value="登录"/>

        <div class="loading" id="loading" style="display: none; float: left; padding-top: 10px; width: 150px; height: 30px;">
            <img src="${stp}/login/img/loading.gif" alt="正在验证登录"/>
            正在验证登录...
        </div>
    </div>
</div>
</body>
</html>