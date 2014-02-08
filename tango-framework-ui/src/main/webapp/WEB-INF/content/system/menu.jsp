<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="menuGrid" class="easyui-treegrid" title="菜单信息列表" data-options="toolbar:'#menuGridToolbar'"></table>
<div id="menuGridToolbar" class="datagrid-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="menuView.handler.add();">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true"
       onclick="menuView.handler.edit();">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
       onclick="menuView.handler.del();">删除</a>
</div>

<div id="menuEditDialog" title="编辑菜单" class="easyui-dialog" style="width:400px;height:300px;padding:5px;"
     data-options="closed:true,modal:'true',buttons: '#dlg-buttons'">
    <form id="menuEditForm" method="post">
        <s:hidden id="menuId" name="id" cssClass="easyui-validatebox"></s:hidden>
        <table>
            <tr>
                <td><label class="lbInfo">名称：</label></td>
                <td>
                    <input name="name" type="text" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入菜单名称.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">链接：</label></td>
                <td>
                    <input name="link" type="text" class="easyui-validatebox" data-options="prompt:'请输入菜单链接.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">上级菜单：</label></td>
                <td>
                    <select id="parentMenuComboTree" name="parentMenu" class="easyui-combotree easyui-validatebox"
                            style="width:150px;"
                            data-options="required:true,prompt:'请选择上级菜单.'">
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">菜单类型：</label></td>
                <td>
                    <select name="type" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">菜单</option>
                        <option value="2">Tab</option>
                        <option value="3">链接</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">系统菜单：</label></td>
                <td>
                    <select name="system" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">排序编号：</label></td>
                <td>
                    <input name="orderNum" type="text" class="easyui-validatebox"
                           data-options="required:true,prompt:'请输入排序编号.'"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">图标：</label></td>
                <td>
                    <input name="icon" type="link" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td><label class="lbInfo">状态：</label></td>
                <td>
                    <select name="stated" class="easyui-combobox" style="width: 150px;"
                            data-options="editable:false,panelHeight:100,panelWidth:150">
                        <option value="1">正常</option>
                        <option value="0">审核</option>
                        <option value="-1">删除</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td><label class="lbInfo">描述：</label></td>
                <td>
                    <textarea name="description" style="width: 150px;height: 50px;"/>
                </td>
            </tr>
        </table>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="menuView.handler.save();">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$(menuView.id.menuEditDialog).dialog('close');">取消</a>
    </div>
</div>

<script type="text/javascript">
    var menuView = {
        id: {
            menuGrid: '#menuGrid',
            parentMenuComboTree: '#parentMenuComboTree',
            menuEditDialog: '#menuEditDialog',
            menuEditForm: '#menuEditForm'
        },
        init: function () {
            var menuGrid = $(menuView.id.menuGrid);
            menuGrid.treegrid({
                rownumbers: true,
                fitColumns: true,
                singleSelect: false,
                url: 'menu/tree.action',
                autoRowHeight: false,
                idField: 'id',
                treeField: 'name',
                columns: [
                    [
                        {field: 'id', title: '选择', checkbox: true, width: 80, align: 'center'},
                        {field: 'name', title: '名称', width: 300, align: 'left'},
                        {field: 'link', title: '链接', width: 300, align: 'left'},
                        {field: 'icon', title: '图标', width: 100, align: 'center'},
                        {field: 'lastUpdateTime', title: '最后修改时间', width: 100, align: 'center'},
                        {field: 'stated', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
                            switch (value) {
                                case 1:
                                    return '启用';
                                case 0:
                                    return '禁用';
                            }
                        }}
                    ]
                ],
                onBeforeExpand: function (row) {
                    var url = 'menu/tree.action?uid=' + row.id;
                    $(menuView.id.menuGrid).treegrid("options").url = url;
                    return true;
                }
            });
            $(menuView.id.parentMenuComboTree).combotree({
                onBeforeExpand: function (row) {
                    $(menuView.id.parentMenuComboTree).combotree("tree").tree("options").url = 'menu/tree.action?uid=' + row.id;
                    return true;
                }
            });
            this.handler.loadComboTree();
        },
        handler: {
            loadComboTree: function () {
                $.request('menu/tree.action', {uid: 0}, function (data) {
                    if (data) {
                        var reData = [
                            {id: 0, text: '顶级菜单', children: data }
                        ];
                        $(menuView.id.parentMenuComboTree).combotree('loadData', reData);
                    } else {
                        throw new Error();
                    }
                });
            },
            load: function () {
                var menuTreeGrid = $(menuView.id.menuGrid);
                var roots = menuTreeGrid.treegrid("getRoots");
                for (var i = 0; i < roots.length; i++) {
                    var o = roots[i];
                    menuTreeGrid.treegrid("remove", o.id);
                }
                $.request('menu/tree.action', {uid: 0}, function (data) {
                    if (data) {
                        menuTreeGrid.treegrid("loadData", data);
                    } else {
                        throw new Error();
                    }
                });
            },
            add: function () {
                $(menuView.id.menuEditDialog).dialog('open');
                $(menuView.id.menuEditForm).form('reset');
                $('#menuId').val('');
                var selectMenu = $(menuView.id.menuGrid).treegrid("getSelected");
                var comboTree = $(menuView.id.parentMenuComboTree);
                if (selectMenu && selectMenu.id) {
                    comboTree.combotree('setValue', selectMenu.id);
                } else {
                    comboTree.combotree('setValue', 0);
                }
            },
            edit: function () {
                var menuComboTree = $(menuView.id.parentMenuComboTree),
                        menuGrid = $(menuView.id.menuGrid),
                        selectMenu = menuGrid.treegrid("getSelected");
                if (selectMenu) {
                    menuComboTree.combotree('reload');
                    $(menuView.id.menuEditDialog).dialog('open');
                    $.request('menu/detail.action', {uid: selectMenu.id}, function (data) {
                        if (data) {
                            data.id = selectMenu.id;
                            $(menuView.id.menuEditForm).form('load', data);
                            if (data.parentMenu == 0) {
                                menuComboTree.combotree('setValue', 0);
                            }
                        } else {
                            throw new Error();
                        }
                    });
                }
            },
            save: function () {
                $.submit(menuView.id.menuEditForm, 'menu/sou.action', function () {
                    menuView.handler.load();
                    menuView.handler.loadComboTree();
                    $(menuView.id.menuEditDialog).dialog('close');
                });
            },
            del: function () {
                var selects = $(menuView.id.menuGrid).treegrid("getSelections");
                if (selects.length > 0) {
                    var selectIds = [];
                    for (var i = 0; i < selects.length; i++) {
                        selectIds.push(selects[i].id);
                    }
                    $.request('menu/deletes.action', {ids: selectIds.toString()}, function (data) {
                        if (!data) {
                            throw new Error();
                        }
                        menuView.handler.loadComboTree();
                        menuView.handler.load();
                    });
                } else {
                    $.messager.alert("提示信息", '请选择菜单!', "info");
                }
            }
        }
    };
    menuView.init();
</script>
</html>