<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="userGrid" title="用户信息列表" data-options="toolbar:'#userToolbar'"></table>
<div id="userToolbar" class="datagrid-toolbar">
    <form id="userSearchForm" method="post">
        <div>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
               onclick="userView.handler.detail(false);">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
               onclick="userView.handler.detail(true);">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
               onclick="userView.handler.del();">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
               onclick="userView.handler.rePass();">设置密码</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
               onclick="userView.handler.openUserRoleDialog();">用户角色</a>
        </div>
        <div>
            姓名:
            <input name="name" type="text" class="easyui-validatebox" style="width: 80px;"/>
            帐号:
            <input name="account" type="text" class="easyui-validatebox" style="width: 80px;"/>
            手机:
            <input name="phone" type="text" class="easyui-validatebox" style="width: 80px;"/>
            登陆时间:
            <input class="easyui-datebox" id="loginBeginTime" style="width:120px" data-options="validType:'date'"
                   name="loginBeginTime">
            -
            <input class="easyui-datebox" id="loginEndTime" style="width:120px" data-options="validType:'date'"
                   name="loginEndTime">
            性别:
            <select class="easyui-combobox" panelHeight="auto" style="width:70px" name="gender" data-
                    options="editable:false,panelWidth:70">
                <option value=""></option>
                <option value="1">男</option>
                <option value="0">女</option>
            </select>
            用户状态:
            <select class="easyui-combobox" panelHeight="auto" style="width:70px" name="stated"
                    data-options="editable:false,panelWidth:70">
                <option value=""></option>
                <option value="1">启用</option>
                <option value="0">停用</option>
            </select>
            <a href="javascript:void(0);" style="margin-left: 10px;" class="easyui-linkbutton" iconCls="icon-search"
               onclick="userView.handler.search()">搜索</a>
        </div>
    </form>
</div>

<div id="userEditDialog" title="编辑用户" class="easyui-dialog" style="width:450px;height:300px;padding:5px;"
     data-options="closed:true,modal:'true',buttons: '#dlg-buttons'">
    <form id="userEditForm" method="post">
        <s:hidden id="userUUID" name="id" cssClass="easyui-validatebox"></s:hidden>
        <table>
            <tr>
                <td><label class="lbInfo">姓名：</label></td>
                <td>
                    <input name="name" type="text" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入姓名.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">账号：</label></td>
                <td>
                    <input name="account" type="text" class="easyui-validatebox" validType="account"
                           data-options="required:true,prompt:'请输入帐号.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">性别：</label></td>
                <td>
                    <select name="gender" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">男</option>
                        <option value="0">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">手机号：</label></td>
                <td>
                    <input name="phone" type="text" class="easyui-validatebox" data-options="prompt:'请输入手机号.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">QQ：</label></td>
                <td>
                    <input name="qq" type="text" class="easyui-validatebox" data-options="prompt:'请输入QQ号.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">Email：</label></td>
                <td>
                    <input name="email" type="text" class="easyui-validatebox" data-options="prompt:'请输入Email地址.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">生日：</label></td>
                <td>
                    <input name="hireDate" type="text" class="easyui-datebox" data-options="prompt:'请输入生日.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">用户状态：</label></td>
                <td>
                    <select name="stated" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">启用</option>
                        <option value="0">停用</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.saveOrUpdate();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#userEditDialog').dialog('close');">取消</a>
    </div>

    <div id="repassDialog" title="重置密码" class="easyui-dialog" style="width:300px;height:200px;padding:5px;"
         data-options="closed:true,modal:true,buttons: '#pwd-dlg-buttons'">
        <form id="repassForm" method="post">
            <s:hidden id="repassDialogUserId" name="id"></s:hidden>
            <table>
                <tr>
                    <td><label class="lbInfo">新密码：</label></td>
                    <td>
                        <input id="resetPWD" name="password" type="password" name="usrPassword"
                               class="easyui-validatebox" validType="safepass" data-options="required:true"
                               data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">确认新密码：</label></td>
                    <td>
                        <input id="resetPWDRE" name="rids" type="password" class="easyui-validatebox"
                               required="required" validType="equalTo['#resetPWD']"
                               data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
            </table>
        </form>
        <div id="pwd-dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.repassSubmit();">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="javascript:$('#repassDialog').dialog('close');$('#repassForm').form('reset')">取消</a>
        </div>
    </div>
</div>

<div id="userRoleDialog" title="用户角色" class="easyui-dialog" style="width:600px;height:400px;padding:5px;"
     data-options="closed:true,modal:'true',buttons: '#userRoleDialog-buttons'">
    <table id="userRoleGrid" title="角色列表"></table>
    <div id="userRoleDialog-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.grantRole();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#userRoleDialog').dialog('close');">取消</a>
    </div>
</div>

<script type="text/javascript">
var userView = {
    id: {
        userGrid: '#userGrid',
        roleEditForm: '#userEditForm',
        userPasswordForm: '#userPasswordForm',
        roleSearchForm: '#userSearchForm',
        roleEditDialog: '#userEditDialog',
        userRoleGrid: '#userRoleGrid'
    },
    init: function () {
        var userDataGrid = $(userView.id.userGrid);
        userDataGrid.datagrid({
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
                    {field: 'name', title: '姓名', width: 200, align: 'center'},
                    {field: 'account', title: '账号', width: 100, align: 'center'},
                    {field: 'gender', title: '性别', width: 100, align: 'center', formatter: function (value, row, index) {
                        if (value === 1) {
                            return '男';
                        } else if (value === 0) {
                            return '女';
                        }
                        return '';
                    }},
                    {field: 'phone', title: '手机', width: 100, align: 'center'},
                    {field: 'qq', title: 'QQ', width: 100, align: 'center'},
                    {field: 'email', title: 'Email', width: 200, align: 'center'},
                    {field: 'loginCount', title: '登陆次数', width: 100, align: 'center'},
                    {field: 'lastTime', title: '最后登陆', width: 100, align: 'center'},
                    {field: 'stated', title: '用户状态', width: 100, align: 'center', formatter: function (value, row, index) {
                        if (value == "1") {
                            return "启用";
                        } else if (value == "0") {
                            return "停用";
                        }
                        return '';
                    }}
                ]
            ]
        });
        var pager = userDataGrid.datagrid('getPager');
        if (pager) {
            $(pager).pagination({
                onSelectPage: userView.load
            });
        }
        //
        var userRoleGrid = $(userView.id.userRoleGrid);
        userRoleGrid.datagrid({
            rownumbers: true,
            fitColumns: true,
            fit: true,
            idField: 'id',
            singleSelect: false,
            pagination: true,
            autoRowHeight: false,
            columns: [
                [
                    {field: 'ck', title: '选择', checkbox: true, width: 80, align: 'center'},
                    //{field: 'id', title: 'id', width: 100, align: 'center', hidden: false},
                    {field: 'name', title: '名称', width: 100, align: 'center'},
                    {field: 'remark', title: '描述', width: 200, align: 'center'}
                ]
            ]
        });
        var userRoleGridPager = userRoleGrid.datagrid('getPager');
        if (userRoleGridPager) {
            $(userRoleGridPager).pagination({
                onSelectPage: userView.loadRole
            });
        }
        this.load();
    },
    loadRole: function (pageNo, pageSize, fn) {
        $.request('role/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
            var roleDataGrid = $(userView.id.userRoleGrid);
            roleDataGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
            roleDataGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
            fn.call(roleDataGrid);
        });
    },
    load: function (pageNo, pageSize) {
        pageNo = pageNo || 1;
        pageSize = pageSize || 10;
        $.request('user/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
            var userDataGrid = $(userView.id.userGrid);
            userDataGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
            userDataGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
        });
    },
    handler: {
        search: function () {
            $.submit(userView.id.roleSearchForm, 'user/list.action', function (data) {
                $(userView.id.userGrid).datagrid('loadData', data);
            });
        },
        detail: function (isUpdate) {
            //select record
            if (isUpdate) {
                var userSelections = $(userView.id.userGrid).datagrid("getSelections");
                switch (userSelections.length) {
                    case 1:
                        $(userView.id.roleEditForm).form('load', userSelections[0]);
                        $(userView.id.roleEditDialog).dialog('open');
                        $("#pwd").attr('disabled', true);
                        $('#rpwd').val($("#pwd").val()).attr('disabled', true);
                        break;
                    case 0:
                        $.messager.alert("提示信息", '请选择用户!', "info");
                        break;
                    default :
                        $.messager.alert("提示信息", '对不起,请选择一条数据进行修改!', "info");
                        break;
                }
            } else {
                userView.handler.reset();
                $("#pwd").attr('disabled', false);
                $("#rpwd").attr('disabled', false);
                $(userView.id.roleEditDialog).dialog('open');
            }
        },
        saveOrUpdate: function () {
            $.submit(userView.id.roleEditForm, 'user/sou.action', function (data) {
                userView.load();
                $(userView.id.roleEditDialog).dialog('close');
            });
        },
        del: function () {
            var userSelections = $(userView.id.userGrid).datagrid("getSelections");
            $.messager.confirm('删除提示', '您确定要删除' + userSelections.length + '条记录?', function (r) {
                if (r) {
                    $.request('user/deletes.action', {ids: userSelections.filter('id').toString()}, function (data) {
                        userView.load();
                    });
                }
            });
        },
        rePass: function () {
            var userSelections = $(userView.id.userGrid).datagrid("getSelections");
            switch (userSelections.length) {
                case 1:
                    $("#repassDialogUserId").val(userSelections[0].id);
                    $('#repassDialog').dialog('open');
                    break;
                case 0:
                    $.messager.alert("提示信息", '请选择用户!', "info");
                    break;
                default :
                    $.messager.alert("提示信息", '对不起,请选择一条数据进行修改!', "info");
                    break;
            }
        },
        repassSubmit: function () {
            $.submit('#repassForm', 'user/repass.action', function (data) {
                if (data) {
                    $.messager.alert("提示信息", '修改成功!', "info");
                } else {
                    $.messager.alert("提示信息", '修改失败!', "info");
                }
                $('#repassDialog').dialog('close');
            });
        },
        reset: function () {
            $('#userUUID').val('');
            $(userView.id.roleEditForm).form('reset');
        },
        openUserRoleDialog: function () {
            var userGrid = $(userView.id.userGrid),
                    users = userGrid.datagrid("getSelections");
            if (users.length > 0) {
                $('#userRoleDialog').dialog('open');
                userView.loadRole(1, 10, function () {
                    var userGrid = $(userView.id.userGrid),
                            userRoleGrid = $(userView.id.userRoleGrid),
                            roleAll = userRoleGrid.datagrid("getData");
                    if (users.length == 1) {
                        $.request('user/role.action', {uid: users.filter('id').toString(), ros: roleAll.rows.filter('id').toString()}, function (data) {
                            for (var i = 0; i < data.length; i++) {
                                userRoleGrid.datagrid('selectRecord', data[i]);
                            }
                        });
                    }
                });
            } else {
                $.messager.alert("提示信息", '请选择用户!', "info");
            }
        },
        grantRole: function () {
            var users = $(userView.id.userGrid).datagrid("getSelections");
            var grants = $(userView.id.userRoleGrid).datagrid("getSelections");
            var roleAll = $(userView.id.userRoleGrid).datagrid("getData");
            $.request('user/grant.action', {ids: users.filter('id').toString(), ros: roleAll.rows.filter('id').toString(), grants: grants.filter('id').toString()}, function (data) {
                $('#userRoleDialog').dialog('close');
            });
        }
    }
};
userView.init();
</script>
</html>