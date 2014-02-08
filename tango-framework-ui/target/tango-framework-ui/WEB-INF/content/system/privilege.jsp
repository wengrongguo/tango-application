<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="privilegeGrid" title="权限列表" data-options="toolbar:'#privilegeGridToolbar'"></table>
<div id="privilegeGridToolbar" class="datagrid-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="privilegeView.handler.detail(false);">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
       onclick="privilegeView.handler.detail(true);">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
       onclick="privilegeView.handler.del();">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
       onclick="privilegeView.handler.openPrivilegeMenuDialog();">权限菜单</a>
</div>
<div id="privilegeEditDialog" title="编辑权限" class="easyui-dialog"
     data-options="buttons:'#dlg-buttons',closed:true,collapsible: false,minimizable: false,maximizable: false,modal:true"
     style="width:400px;height:300px;padding:5px;">
    <form class="add_edit" id="privilegeEditForm" method="post" name="privilegeEditForm">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <input type="hidden" id="privilegeId" name="id">
                    <label>名称:</label>
                </td>
                <td>
                    <input type="text" name="name" placeholder="权限名称" maxlength="49" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入权限名称.'"/>
                </td>
            </tr>
            <tr>
                <td><label>上级权限:</label></td>
                <td>
                    <select id="parentComboTree" name="parent" class="easyui-combotree easyui-validatebox"
                            style="width:150px;"
                            data-options="prompt:'请选择上级权限.'">
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>删除权限:</label></td>
                <td>
                    <select name="remove" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">排序:</label></td>
                <td>
                    <input name="orderNum" type="text" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入排序编号.'"/>
                </td>
            </tr>
            <tr>
                <td><label for="stated">状态:</label></td>
                <td>
                    <select id="stated" name="stated" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">启用</option>
                        <option value="0">停用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="_description">描述:</label></td>
                <td><textarea type="text" name="remark" maxlength="500" placeholder="权限描述" id="_description"
                              class="easyui-validatebox"></textarea></td>
            </tr>
        </table>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="privilegeView.handler.saveOrUpdate();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$(privilegeView.id.privilegeEditDialog).dialog('close');">取消</a>
    </div>
</div>

<div id="privilegeMenuDialog" title="权限菜单" class="easyui-dialog" style="width:600px;height:400px;padding:5px;"
     data-options="closed:true,modal:'true',buttons: '#privilegeMenuDialog-buttons'">
    <table id="privilegeMenuGrid" title="菜单列表"></table>
    <div id="privilegeMenuDialog-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="privilegeView.handler.grantMenu();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#privilegeMenuDialog').dialog('close');">取消</a>
    </div>
</div>
<script>
    var privilegeView = {
        id: {
            privilegeGrid: '#privilegeGrid',
            parentComboTree: '#parentComboTree',
            privilegeSearchForm: '#privilegeSearchForm',
            privilegeEditForm: '#privilegeEditForm',
            privilegeEditDialog: '#privilegeEditDialog',
            privilegeMenuDialog: '#privilegeMenuDialog',
            privilegeMenuGrid: '#privilegeMenuGrid'
        },
        init: function () {
            var privilegeDataGrid = $(privilegeView.id.privilegeGrid);
            privilegeDataGrid.treegrid({
                rownumbers: true,
                fitColumns: true,
                url: 'privilege/tree.action',
                singleSelect: false,
                pagination: false,
                autoRowHeight: false,
                idField: 'id',
                fit: true,
                treeField: 'name',
                columns: [
                    [
                        {field: 'ck', title: '选择', checkbox: true, width: 80, align: 'center'},
                        //{field: 'id', title: 'id', width: 100, align: 'center', hidden: false},
                        {field: 'name', title: '名称', width: 100, align: 'left'},
                        {field: 'remove', title: '删除权限', width: 200, align: 'center', formatter: function (value, row, index) {
                            if (value == "1") {
                                return '是';
                            } else if (value == "0") {
                                return '否';
                            }
                            return '';
                        }},
                        {field: 'stated', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
                            if (value === 1) {
                                return '启用';
                            } else if (value === 0) {
                                return '停用';
                            }
                            return '';
                        }}
                    ]
                ],
                onBeforeExpand: function (row) {
                    var url = 'privilege/tree.action?uid=' + row.id;
                    $(privilegeView.id.privilegeGrid).treegrid("options").url = url;
                    return true;
                }
            });
            var privilegeMenuGrid = $(privilegeView.id.privilegeMenuGrid);
            privilegeMenuGrid.datagrid({
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
                        {field: 'link', title: '链接', width: 200, align: 'center'}
                    ]
                ]
            });
            var privilegeMenuGridPager = privilegeMenuGrid.datagrid('getPager');
            if (privilegeMenuGridPager) {
                $(privilegeMenuGridPager).pagination({
                    onSelectPage: privilegeView.loadMenu
                });
            }
            this.loadComboTree();
        },
        loadComboTree: function () {
            $.request('privilege/combotree.action', {uid: 0}, function (data) {
                if (data) {
                    var reData = [
                        {id: 0, text: '顶级权限', children: data}
                    ];
                    $(privilegeView.id.parentComboTree).combotree('loadData', reData);
                } else {
                    throw new Error();
                }
            });
        },
        load: function () {
            var privilegeDataGrid = $(privilegeView.id.privilegeGrid);
            var roots = privilegeDataGrid.treegrid("getRoots");
            for (var i = 0; i < roots.length; i++) {
                var o = roots[i];
                privilegeDataGrid.treegrid("remove", o.id);
            }
            $.request('privilege/tree.action', {uid: 0}, function (data) {
                if (data) {
                    privilegeDataGrid.treegrid("loadData", data);
                } else {
                    throw new Error();
                }
            });
        },
        loadMenu: function (pageNo, pageSize, fn) {
            $.request('menu/page.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
                var privilegeMenuGrid = $(privilegeView.id.privilegeMenuGrid);
                privilegeMenuGrid.datagrid('loadData', {total: data.rows, rows: data.dataList});
                privilegeMenuGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
                (function () {
                    var privilegeGrid = $(privilegeView.id.privilegeGrid);
                    var privileges = privilegeGrid.datagrid("getSelections");
                    privilegeMenuGrid.datagrid('clearChecked');
                    privilegeMenuGrid.datagrid('clearSelections');
                    if (privileges.length == 1) {
                        var menuAll = privilegeMenuGrid.datagrid("getData");
                        $.request('privilege/menu.action', {uid: privileges.filter('id').toString(), mes: menuAll.rows.filter('id').toString()}, function (data) {
                            for (var i = 0; i < data.length; i++) {
                                privilegeMenuGrid.datagrid('selectRecord', data[i]);
                            }
                        });
                    }
                })();
            });
        },
        handler: {
            search: function () {
                $.submit(privilegeView.id.privilegeSearchForm, 'privilege/list.action', function (data) {
                    $(privilegeView.id.privilegeGrid).treegrid('loadData', data);
                });
            },
            detail: function (isUpdate) {
                //select record
                if (isUpdate) {
                    var privilegeSelections = $(privilegeView.id.privilegeGrid).datagrid("getSelections");
                    console.log(privilegeSelections);
                    switch (privilegeSelections.length) {
                        case 1:
                            $(privilegeView.id.privilegeEditForm).form('load', privilegeSelections[0]);
                            $(privilegeView.id.privilegeEditDialog).dialog('open');
                            break;
                        case 0:
                            $.messager.alert("提示信息", '请选择权限!', "info");
                            break;
                        default :
                            $.messager.alert("提示信息", '对不起,请选择一条数据进行修改!', "info");
                            break;
                    }
                } else {
                    privilegeView.handler.reset();
                    $(privilegeView.id.privilegeEditDialog).dialog('open');
                }
            },
            saveOrUpdate: function () {
                $.submit(privilegeView.id.privilegeEditForm, 'privilege/sou.action', function (data) {
                    privilegeView.load();
                    privilegeView.loadComboTree();
                    $(privilegeView.id.privilegeEditDialog).dialog('close');
                });
            },
            del: function () {
                var privilegeSelections = $(privilegeView.id.privilegeGrid).datagrid("getSelections");
                $.messager.confirm('删除提示', '您确定要删除' + privilegeSelections.length + '条记录?', function (r) {
                    if (r) {
                        $.request('privilege/deletes.action', {ids: privilegeSelections.filter('id').toString()}, function (data) {
                            privilegeView.load();
                            privilegeView.loadComboTree();
                        });
                    }
                });
            },
            reset: function () {
                $('#privilegeId').val('');
                $(privilegeView.id.privilegeEditForm).form('reset');
            },
            openPrivilegeMenuDialog: function () {
                var privilegeGrid = $(privilegeView.id.privilegeGrid),
                        privileges = privilegeGrid.datagrid("getSelections");
                if (privileges.length > 0) {
                    $(privilegeView.id.privilegeMenuDialog).dialog('open');
                    privilegeView.loadMenu(1, 10);
                } else {
                    $.messager.alert("提示信息", '请选择权限!', "info");
                }
            },
            grantMenu: function () {
                var privileges = $(privilegeView.id.privilegeGrid).datagrid("getSelections");
                var grants = $(privilegeView.id.privilegeMenuGrid).datagrid("getSelections");
                var menuAll = $(privilegeView.id.privilegeMenuGrid).datagrid("getData");
                $.request('privilege/grant.action', {ids: privileges.filter('id').toString(), mes: menuAll.rows.filter('id').toString(), grants: grants.filter('id').toString()}, function (data) {
                    $(privilegeView.id.privilegeMenuDialog).dialog('close');
                });
            }
        }
    };
    privilegeView.init();
</script>

