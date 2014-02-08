<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="loginLogGrid" title="登陆/登出信息列表" data-options="toolbar:'#loginLogToolbar'"></table>
<div id="loginLogToolbar" class="datagrid-toolbar">
    <form id="loginLogSearchForm" method="post">
        <div>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="loginLogView.handler.exp();">导出</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="loginLogView.handler.del();">删除</a>
        </div>
        <div>
            帐号:
            <input name="account" type="text" class="easyui-validatebox" style="width: 80px;"/>
            IP:
            <input name="ip" type="text" class="easyui-validatebox" style="width: 80px;"/>
            登陆时间:
            <input class="easyui-datebox" id="loginBeginTime" style="width:120px" data-options="validType:'date'" name="loginBeginTime">
            -
            <input class="easyui-datebox" id="loginEndTime" style="width:120px" data-options="validType:'date'" name="loginEndTime">
            类型:
            <select class="easyui-combobox" panelHeight="auto" style="width:70px" name="type" data-
                    options="editable:false,panelWidth:70">
                <option value=""></option>
                <option value="1">登陆</option>
                <option value="0">登出</option>
            </select>
            <a href="javascript:void(0);" style="margin-left: 10px;" class="easyui-linkbutton" iconCls="icon-search" onclick="loginLogView.handler.search()">搜索</a>
        </div>
    </form>
</div>

<script type="text/javascript">
    var loginLogView = {
        id: {
            loginLogGrid: '#loginLogGrid',
            loginLogSearchForm: '#loginLogSearchForm'
        },
        init: function () {
            var loginLogGrid = $(loginLogView.id.loginLogGrid);
            loginLogGrid.datagrid({
                rownumbers: true,
                fitColumns: true,
                fit: true,
                singleSelect: false,
                pagination: true,
                autoRowHeight: false,
                columns: [
                    [
                        {field: 'ck', title: '选择', checkbox: true, width: 80, align: 'center'},
                        //{field: 'id', title: 'id', width: 100, align: 'center', hidden: false},
                        {field: 'userId', title: '用户ID', width: 100, align: 'center'},
                        {field: 'account', title: '登陆账号', width: 100, align: 'center'},
                        {field: 'type', title: '类型', width: 100, align: 'center', formatter: function (value, row, index) {
                            if (value === 1) {
                                return '登陆';
                            } else if (value === 2) {
                                return '登出';
                            }
                            return '';
                        }},
                        {field: 'ip', title: 'IP', width: 100, align: 'center'},
                        {field: 'date', title: '时间', width: 100, align: 'center'},
                        {field: 'stated', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
                            if (value === 1) {
                                return '成功';
                            } else if (value === 2) {
                                return '失败';
                            }
                            return '';
                        }}
                    ]
                ]
            });
            var pager = loginLogGrid.datagrid('getPager');
            if (pager) {
                $(pager).pagination({
                    onSelectPage: loginLogView.load
                });
            }
            this.load();
        },
        load: function (pageNo, pageSize) {
            pageNo = pageNo || 1;
            pageSize = pageSize || 50;
            $.request('loginlog/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
                var loginLogGrid = $(loginLogView.id.loginLogGrid);
                loginLogGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
                loginLogGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
            });
        },
        handler: {
            search: function () {
                $.submit(loginLogView.id.loginLogSearchForm, 'loginlog/list.action', function (data) {
                    $(loginLogView.id.loginLogGrid).datagrid('loadData', data);
                });
            },
            del: function () {
                var loginLogSelections = $(loginLogView.id.loginLogGrid).datagrid("getSelections");
                $.messager.confirm('删除提示', '您确定要删除' + loginLogSelections.length + '条记录?', function (r) {
                    if (r) {
                        $.request('loginlog/deletes.action', {ids: loginLogSelections.filter('id').toString()}, function (data) {
                            loginLogView.load();
                        });
                    }
                });
            },
            exp: function () {
                var loginLogSearchForm = $(loginLogView.id.loginLogSearchForm)[0];
                loginLogSearchForm.action = 'loginlog/export.action';
                loginLogSearchForm.submit();
            }
        }
    };
    loginLogView.init();
</script>
</html>