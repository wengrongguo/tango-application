<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>后台管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta name="keywords" content="framework,tango,admin"/>
    <meta name="description" content="framework of tango"/>
    <link rel="stylesheet" type="text/css" href="${scriptPath}/jquery-easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${scriptPath}/jquery-easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${stp}/basic.css"/>
    <link rel="stylesheet" type="text/css" href="${stp}/system/admin/default.css"/>
    <script type="text/javascript" charset="UTF-8" src="${scriptPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${scriptPath}/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${scriptPath}/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${scriptPath}/jquery-easyui/jquery.easyui.ext.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${scriptPath}/prototype.js"></script>
    <script src="${scp}/login/login.js" type="text/javascript"></script>

    <script>
        function logout() {
            $.ajax({
                type: "POST",
                url: '${ctx}/system/logout.action',
                data: {dt: new Date()},
                dataType: "JSON",
                cache: false,
                success: function (data) {
                    if (data === '1') {
                        window.location.href = '${ctx}';
                    }
                }
            });
        }
    </script>
    <style type="text/css">
        .panel  {
            border: none;
            border-radius :0;
        }
        .panel-header{
            background: #f5f5f5;
            border: none;
            border-bottom: 1px solid #e5e5e5;
            border-left: 1px solid #e5e5e5;
        }

        .panel-title{
            font-size: 13px;
            color: #333;
            height: 22px;
            line-height: 22px;
            font-weight: bold;
        }
        .layout-expand{
            background: #f5f5f5;
            border-right: 1px solid #e5e5e5;
        }
        .panel-body{
            border: none;
            padding: 0;
        }
        .tabs-header, .tabs-tool{
            background: #f5f5f5;
        }
        .tabs, .tabs-selected{
            height: 30px;
            border-color: #e5e5e5;
        }
        .tabs li a.tabs-inner{
            height: 30px;
            line-height: 30px;
        }

        .tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner {
            border-color: #e5e5e5;
        }

        .tabs, .tabs-selected {
            height: 30px;
        }

        .tabs li.tabs-selected a.tabs-inner{
            background : #f0f4ff;

        }

        .tabs li a.tabs-inner{
            background :#ffffff;

        }
        .accordion-body{
            background: #f5f5f5;
        }
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="padding: 0px;border: 0px">
    <iframe src="top.action" style="border: 0;width: 100%;height:45px;"></iframe>
</div>
<div data-options="region:'west',split:false" title=" " style="width:180px;">
    <div class="easyui-accordion" fit="true" border="false">
        <div title="首页" style="overflow:auto;">
            <ul id="sys_menu_tree" class="easyui-tree" style=""></ul>
        </div>
    </div>
</div>
<div region="center">
    <div id="center_tabs" class="easyui-tabs" fit="true" border="false"></div>
</div>
<script type="text/javascript">
    var indexView = {
        init: function () {
            $('#sys_menu_tree').tree({
                url: 'menu.action',
                animate: true,
                fit: true,
                onClick: function (node) {
                    var mainTab = $('#center_tabs');
                    if (node.attributes.type != 3) {
                        return;
                    }
                    if (!mainTab.tabs('exists', node.text)) {
                        if (node.attributes && node.attributes.link) {
                            mainTab.tabs('add', {
                                title: node.text,
                                href: '${ctx}/' + node.attributes.link,
                                closable: true
                            });
                        }
                    } else {
                        mainTab.tabs('select', node.text);
                    }
                }
            });
        }
    }
    indexView.init();
</script>
</body>
