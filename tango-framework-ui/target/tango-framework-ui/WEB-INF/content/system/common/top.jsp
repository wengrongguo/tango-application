<%--
  User: tango
  Date: 14-1-29
  Time: 上午2:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" charset="UTF-8" src="${scriptPath}/jquery/jquery-1.8.0.min.js"></script>
    <link href="${stp}/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        a:hover {
            text-decoration: none;
        }

        .nav ul li {
            display: table;
            float: right;
            height: 45px;
            margin-right: 2px;
            line-height: 45px;
            color: #ffffff;
            padding: 0 10px;
        }

        .nav ul li a {
            color: #ffffff;
        }
    </style>
</head>
<body>
<div class="navbar-header" style="width:100%;background: #438eb9">
    <a href="#" class="navbar-brand" style="color:#ffffff;font-size:24px;">
        <small>
            <i class="icon-leaf"></i>
            tAngo后台管理
        </small>
    </a><!-- /.brand -->
    <div style="float: right;margin-right:50px;" class="nav">
        <ul style="list-style: none;float:left;">
            <li style="background: #555">
                <script>
                    function logout() {
                        $.ajax({
                            type: "POST",
                            url: '${ctx}/system/logout.action',
                            data: {dt: new Date()},
                            dataType: "JSON",
                            cache: false,
                            success: function (data) {
                                if (data === 'success') {
                                    window.parent.location.href = '${ctx}';
                                }
                            }
                        });
                    }
                </script>
                <a href="javascript:void(0);" onclick="logout();">
                    <i class="icon-off"></i>
                    退出
                </a>
            </li>
            <li style="background: #892e65">
                <a href="#">
                    <i class="icon-cog"></i>
                    设置
                </a>
            </li>
            <li style="background: #62a8d1">
                <span>
                     <small>欢迎光临,</small>
                </span>
                <a href="#" title="个人资料">
                    <i class="icon-user">
                        ${users.user.account}
                    </i>
                </a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
