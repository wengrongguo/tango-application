<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="roleGrid" title="角色列表" data-options="toolbar:'#roleGridToolbar'"></table>
<div id="roleGridToolbar" class="datagrid-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="roleView.handler.detail(false);">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
       onclick="roleView.handler.detail(true);">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
       onclick="roleView.handler.del();">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
       onclick="roleView.handler.openRolePrivilegeDialog();">角色权限</a>
</div>
<div id="roleEditDialog" title="编辑角色" class="easyui-dialog"
     data-options="buttons:'#dlg-buttons',closed:true,collapsible: false,minimizable: false,maximizable: false,modal:true"
     style="width:400px;height:300px;padding:5px;">
    <form class="add_edit" id="roleEditForm" method="post" name="roleEditForm">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <input type="hidden" id="roleId" name="id">
                    <label>名称:</label>
                </td>
                <td>
                    <input type="text" name="name" id="_ureName" placeholder="角色名" maxlength="49"
                           class="easyui-validatebox" data-options="required:true,prompt:'请输入角色名.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">排序：</label></td>
                <td>
                    <input name="orderNum" type="text" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入排序编号.'"/>
                </td>
            </tr>
            <tr>
                <td><label>状态:</label></td>
                <td>
                    <select name="stated" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">启用</option>
                        <option value="0">停用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="_description">描　　述:</label></td>
                <td><textarea type="text" name="remark" maxlength="500" placeholder="角色描述" id="_description"
                              class="easyui-validatebox"></textarea></td>
            </tr>
        </table>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="roleView.handler.saveOrUpdate();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$(roleView.id.roleEditDialog).dialog('close');">取消</a>
    </div>
</div>

<div id="rolePrivilegeDialog" title="角色权限" class="easyui-dialog" style="width:600px;height:400px;padding:5px;"
     data-options="closed:true,modal:'true',buttons: '#rolePrivilegeDialog-buttons'">
    <table id="rolePrivilegeGrid" title="权限列表"></table>
    <div id="rolePrivilegeDialog-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="roleView.handler.grantPrivilege();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#rolePrivilegeDialog').dialog('close');">取消</a>
    </div>
</div>
<script>
    var roleView = {
        id: {
            roleGrid: '#roleGrid',
            roleSearchForm: '#roleSearchForm',
            roleEditForm: '#roleEditForm',
            roleEditDialog: '#roleEditDialog',
            rolePrivilegeDialog: '#rolePrivilegeDialog',
            rolePrivilegeGrid: '#rolePrivilegeGrid'
        },
        init: function () {
            var roleDataGrid = $(roleView.id.roleGrid);
            roleDataGrid.datagrid({
                rownumbers: true,
                fitColumns: true,
                singleSelect: false,
                fit: true,
                pagination: true,
                autoRowHeight: false,
                columns: [
                    [
                        {field: 'ck', title: '选择', checkbox: true, width: 80, align: 'center'},
                        //{field: 'id', title: 'id', width: 100, align: 'center', hidden: false},
                        {field: 'name', title: '名称', width: 100, align: 'center'},
                        {field: 'remark', title: '描述', width: 200, align: 'center'},
                        {field: 'stated', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
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
            var pager = roleDataGrid.datagrid('getPager');
            if (pager) {
                $(pager).pagination({
                    onSelectPage: roleView.load
                });
            }
            //
            var rolePrivilegeGrid = $(roleView.id.rolePrivilegeGrid);
            rolePrivilegeGrid.datagrid({
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
            var rolePrivilegeGridPager = rolePrivilegeGrid.datagrid('getPager');
            if (rolePrivilegeGridPager) {
                $(rolePrivilegeGridPager).pagination({
                    onSelectPage: roleView.loadPrivilege
                });
            }
            this.load();
        },
        load: function (pageNo, pageSize) {
            pageNo = pageNo || 1;
            pageSize = pageSize || 10;
            $.request('role/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
                var roleDataGrid = $(roleView.id.roleGrid);
                roleDataGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
                roleDataGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
            });
        },
        loadPrivilege: function (pageNo, pageSize) {
            $.request('privilege/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
                var rolePrivilegeGrid = $(roleView.id.rolePrivilegeGrid);
                rolePrivilegeGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
                rolePrivilegeGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});

                (function () {
                    var roles = $(roleView.id.roleGrid).datagrid("getSelections");
                    rolePrivilegeGrid.datagrid('clearChecked');
                    rolePrivilegeGrid.datagrid('clearSelections');
                    if (roles.length == 1) {
                        var roleGrid = $(roleView.id.roleGrid),
                                roleAll = rolePrivilegeGrid.datagrid("getData");
                        $.request('role/privilege.action', {uid: roles.filter('id').toString(), pris: roleAll.rows.filter('id').toString()}, function (data) {
                            console.log(data);
                            for (var i = 0; i < data.length; i++) {
                                rolePrivilegeGrid.datagrid('selectRecord', data[i]);
                            }
                        });
                    }
                })();
            });
        },
        handler: {
            search: function () {
                $.submit(roleView.id.roleSearchForm, 'role/list.action', function (data) {
                    $(roleView.id.roleGrid).datagrid('loadData', data);
                });
            },
            detail: function (isUpdate) {
                //select record
                if (isUpdate) {
                    var roleSelections = $(roleView.id.roleGrid).datagrid("getSelections");
                    switch (roleSelections.length) {
                        case 1:
                            $(roleView.id.roleEditForm).form('load', roleSelections[0]);
                            $(roleView.id.roleEditDialog).dialog('open');
                            break;
                        case 0:
                            $.messager.alert("提示信息", '请选择角色!', "info");
                            break;
                        default :
                            $.messager.alert("提示信息", '对不起,请选择一条数据进行修改!', "info");
                            break;
                    }
                } else {
                    roleView.handler.reset();
                    $(roleView.id.roleEditDialog).dialog('open');
                }
            },
            saveOrUpdate: function () {
                $.submit(roleView.id.roleEditForm, 'role/sou.action', function (data) {
                    roleView.load();
                    $(roleView.id.roleEditDialog).dialog('close');
                });
            },
            del: function () {
                var roleSelections = $(roleView.id.roleGrid).datagrid("getSelections");
                $.messager.confirm('删除提示', '您确定要删除' + roleSelections.length + '条记录?', function (r) {
                    if (r) {
                        $.request('role/deletes.action', {ids: roleSelections.filter('id').toString()}, function (data) {
                            roleView.load();
                        });
                    }
                });
            },
            reset: function () {
                $('#roleId').val('');
                $(roleView.id.roleEditForm).form('reset');
            },
            openRolePrivilegeDialog: function () {
                var roleGrid = $(roleView.id.roleGrid),
                        roles = roleGrid.datagrid("getSelections");
                if (roles.length > 0) {
                    $(roleView.id.rolePrivilegeDialog).dialog('open');
                    roleView.loadPrivilege(1, 10);
                } else {
                    $.messager.alert("提示信息", '请选择角色!', "info");
                }
            },
            grantPrivilege: function () {
                var roles = $(roleView.id.roleGrid).datagrid("getSelections");
                var grants = $(roleView.id.rolePrivilegeGrid).datagrid("getSelections");
                var privilegeAll = $(roleView.id.rolePrivilegeGrid).datagrid("getData");
                $.request('role/grant.action', {ids: roles.filter('id').toString(), pris: privilegeAll.rows.filter('id').toString(), grants: grants.filter('id').toString()}, function (data) {
                    $(roleView.id.rolePrivilegeDialog).dialog('close');
                });
            }
        }
    };
    roleView.init();
</script>