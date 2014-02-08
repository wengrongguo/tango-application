<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" data-options="fit:true" style="margin: 5px;">
<div data-options="region:'west',split:true,collapsible:false" title="用户组" style="width:200px;">
    <ul id="usergroup_tree"></ul>
    <div id="userGroupMM" class="easyui-menu">
        <div onclick="userGroupView.handler.addUserGroup()" data-options="iconCls:'icon-add'">添加用户组</div>
        <div onclick="userGroupView.handler.del()" data-options="iconCls:'icon-remove'">删除用户组</div>
        <div onclick="userGroupView.handler.detail($(userGroupView.id.userGroupTree).tree('getSelected'))" data-options="iconCls:'icon-edit'">编辑用户组
        </div>
    </div>
    <div id="usergroup_window" class="easyui-window" title="新建用户组"
         data-options="closed:true,iconCls:'icon-save',resizable:false,minimizable:false,maximizable:false,modal:true,
             onBeforeClose:function(){$(userGroupView.id.userGroupFormId).form('reset');$('#ugpUUID').val('');}"
         style="width: 300px;height: 220px;">
        <div class="demo-info" style="margin-left: 10%;margin-right: 10%;margin-top: 10px;">
            <form id="usergroup_form" method="post">
                <s:hidden id="ugpUUID" name="id"></s:hidden>
                <table>
                    <tr>
                        <td><label>用户组名称</label></td>
                        <td>
                            <input type="text" name="ugpName" class="easyui-validatebox" data-options="prompt:'请输入用户组名称',required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>用户组描述</label></td>
                        <td><s:textfield name="ugpRemarks"></s:textfield></td>
                    </tr>
                    <tr>
                        <td><label>父节点</label></td>
                        <td>
                            <input id="pid" class="easyui-combotree" name="ugpParentguid" data-options="valueField:'id',textField:'text'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>角色</label></td>
                        <td>
                            <input id="userGroupRoles" class="easyui-combobox" name="roleGroupMap.roleId" data-options="valueField:'id',editable:false,textField:'ureName'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>状态</label></td>
                        <td>
                            <s:radio id="defo" name="ugpState" list="#{'1':'启用','2':'禁用'}" value="1"></s:radio>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="userGroupView.handler.saveOrUpdate()">保存</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="userGroupView.handler.cancel('#usergroup_form','#usergroup_window')">取消</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
    <!-- 修改用户组 -->
    <div id="usergroupuser_window" class="easyui-window" title="修改用户组" style="width: 300px;height: 160px;"
         data-options="closed:true,iconCls:'icon-save',resizable:false,minimizable:false,maximizable:false,modal:true,
             onBeforeClose:function(){$(userGroupView.id.userGroupUserForm).form('reset');}">
        <div class="demo-info" style="margin-left: 10%;margin-right: 10%;margin-top: 10px;">
            <form id="usergroupuser_form" method="post">
                <table>
                    <tr>
                        <td><label>用户</label></td>
                        <td>
                            <input type="text" id="editUSER" name="userIDs" class="easyui-combobox"
                                   data-options="prompt:'请输入用户组名称',required:true, valueField:'id', textField:'usrAccount', multiple:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>用户组</label></td>
                        <td>
                            <input type="text" id="editUGP" name="userGroupIDs" class="easyui-combotree"
                                   data-options="prompt:'请选择用户组名称',required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"
                               onclick="userGroupView.handler.updateUserGroupUser()">保存</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel"
                               onclick="userGroupView.handler.cancel('#usergroupuser_form','#usergroupuser_window')">取消</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <!-- 修改用户组END -->
</div>
<!-- 用户 -->
<div data-options="region:'center'" style="border: 0px;">
    <table id="userGrid" title="用户信息列表" data-options="toolbar:'#userToolbar'"></table>

    <div id="userToolbar" class="datagrid-toolbar">
        <form id="userSearchForm" method="post">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="userView.handler.detail(false);">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="userView.handler.detail(true);">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="userView.handler.del();">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="userView.handler.rePass();">重置密码</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="userGroupView.handler.editUserGroup();">用户组</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="userView.handler.detailRole();">角色</a>
            <br/>
            帐号:
            <input name="usrAccount" type="text" class="easyui-validatebox" style="width: 80px;"/>
            登陆时间:
            <input class="easyui-datebox" id="loginBeginTime" style="width:120px" data-options="validType:'date'" name="loginBeginTime">
            -
            <input class="easyui-datebox" id="loginEndTime" style="width:120px" data-options="validType:'date'" name="loginEndTime">
            是否过期:
            <select class="easyui-combobox" style="width:70px" name="usrIsautoexpire" data-options="editable:false,panelWidth:70,panelHeight:65">
                <option value="">　</option>
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
            是否绑定IP:
            <select class="easyui-combobox" style="width:70px" name="usrIsbindip" data-options="editable:false,panelWidth:70,panelHeight:65">
                <option value="">　</option>
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
            用户状态:
            <select class="easyui-combobox" style="width:70px" name="usrState" data-options="editable:false,panelWidth:70,panelHeight:85">
                <option value="">　</option>
                <option value="1">正常</option>
                <option value="0">审核</option>
                <option value="-1">删除</option>
            </select>
            <a href="javascript:void(0);" style="margin-left: 10px;" class="easyui-linkbutton" iconCls="icon-search" onclick="userView.handler.search()">搜索</a>
        </form>
    </div>

    <div id="userEditDialog" title="编辑用户" class="easyui-dialog" style="width:400px;height:300px;padding:5px;" data-options="closed:true,modal:true,buttons: '#dlg-buttons'">
        <form id="userEditForm" method="post">
            <s:hidden id="userUUID" name="id" cssClass="easyui-validatebox"></s:hidden>
            <table>
                <tr>
                    <td><label class="lbInfo">账号：</label></td>
                    <td>
                        <input name="usrAccount" id="usrAccountI" type="text" class="easyui-validatebox" validType="account" data-options="required:true,prompt:'请输入帐号.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">用户名称：</label></td>
                    <td>
                        <input name="usrName" id="usrName" type="text" class="easyui-validatebox" data-options="prompt:'请输入名称.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">新密码：</label></td>
                    <td>
                        <input id="pwd" type="password" name="usrPassword" class="easyui-validatebox" validType="safepass" data-options="required:true" data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">确认新密码：</label></td>
                    <td>
                        <input id="rpwd" type="password" name="rePass" class="easyui-validatebox" required="required" validType="equalTo['#pwd']" data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">用户IP：</label></td>
                    <td>
                        <input name="usrIp" id="Ip" type="text" class="easyui-validatebox" data-options="prompt:'用户IP地址.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">用户MAC：</label></td>
                    <td><input name="usrMac" id="Mac" type="text" class="easyui-validatebox" data-options="prompt:'用户Mac地址.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">用户状态：</label></td>
                    <td>
                        <select name="usrState" class="easyui-combobox" style="width: 150px;" data-options="editable:false,panelHeight:100,panelWidth:150">
                            <option value="1">正常</option>
                            <option value="0">审核</option>
                            <option value="-1">删除</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">是否过期:</label></td>
                    <td>
                        <select name="usrIsautoexpire" class="easyui-combobox" style="width: 150px;" data-options="editable:false,panelHeight:100,panelWidth:150">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.saveOrUpdate();">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#userEditDialog').dialog('close');">取消</a>
        </div>
    </div>

    <div id="userRoleDialog" title="设置角色" class="easyui-dialog" style="width:300px;height:200px;padding:5px;" data-options="closed:true,modal:true,buttons: '#role-dlg-buttons'">
        <form id="userRoleForm" method="post">
            <s:hidden id="role_dialog_user" name="ids" cssClass="easyui-validatebox"></s:hidden>
            <table>
                <tr>
                    <td><label class="lbInfo">账号：</label></td>
                    <td>
                        <input id="role_dialog_account" name="usrAccount" type="text" class="easyui-validatebox" readonly="true" data-options="required:true,prompt:'请输入帐号.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">角色名称：</label></td>
                    <td>
                        <input class="easyui-combobox" name="rids" id="role-combobox"
                               data-options="valueField:'id',textField:'ureName',multiple: true,url:'user/role.action',editable:false,panelHeight:100,panelWidth:150,required:true,prompt:'请选择角色.'"/>
                    </td>
                </tr>
            </table>
        </form>
        <div id="role-dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.editUserRole();">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#userRoleDialog').dialog('close');">取消</a>
        </div>
    </div>

    <div id="userPWDDialog" title="重置密码" class="easyui-dialog" style="width:300px;height:200px;padding:5px;" data-options="closed:true,modal:true,buttons: '#pwd-dlg-buttons'">
        <form id="userPWDForm" method="post">
            <s:hidden id="pwd_dialog_user" name="id"></s:hidden>
            <table>
                <tr>
                    <td><label class="lbInfo">新密码：</label></td>
                    <td>
                        <input id="resetPWD" name="usrPassword" type="password" name="usrPassword"
                               class="easyui-validatebox" validType="safepass" data-options="required:true" data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="lbInfo">确认新密码：</label></td>
                    <td>
                        <input id="resetPWDRE" name="rids" type="password" class="easyui-validatebox"
                               required="required" validType="equalTo['#resetPWD']" data-options="prompt:'新密码不能为空.'"/>
                    </td>
                </tr>
            </table>
        </form>
        <div id="pwd-dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userView.handler.resetPWD();">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#userPWDDialog').dialog('close');$('#userPWDForm').form('reset')">取消</a>
        </div>
    </div>
</div>
<!-- 用户 END -->
</div>


<script type="text/javascript">
var userGroupView = {
    id: {
        userGroupTree: '#usergroup_tree',
        userGroupFormId: '#usergroup_form',
        userGroupWindow: "#usergroup_window",
        usersTableId: '#users_table',
        userGroupUserWindow: "#usergroupuser_window",
        userGroupUserForm: "#usergroupuser_form"
    },
    optNode: {},
    init: function () {
        $(userGroupView.id.userGroupTree).tree({
            url: 'usergroup/tree.action',
            animate: true,
            onContextMenu: function (e, node) {
                e.preventDefault();
                $(this).tree('select', node.target);
                $("#userGroupMM").menu('show', {left: e.pageX, top: e.pageY});
            }
        });
    },
    load: function () {
    },
    handler: {
        saveOrUpdate: function () {
            var ugpGUID = $("#ugpUUID").val();
            var selected = $("#pid").combotree("getValue");
            if (ugpGUID == selected) {
                $.messager.alert('错误信息', '不能选择自身作为父节点!', 'error');
            } else {
                $.submit(userGroupView.id.userGroupFormId, 'usergroup/sou.action', function (data) {
                    $(userGroupView.id.userGroupFormId).form('reset');
                    userGroupView.handler.optWindow(userGroupView.id.userGroupWindow, "", "close")
                    userGroupView.handler.refreshTreeNodes();
                    userGroupView.optNode = null;
                });
            }
        },
        detail: function (node) {
            userGroupView.optNode = $(userGroupView.id.userGroupTree).tree('getParent', node.target);
            if (userGroupView.optNode == null) {
                userGroupView.optNode = node;
            }
            if (node) {
                var roleGroupMap;
                $.request('usergroup/detail.action', {uuid: node.id}, function (data) {
                    $("#usergroup_usergroup_window").window({
                        title: '编辑用户组'
                    });

                    if (data) {
                        data.id = node.id;
                        var userGroup = data.userGroup;
                        roleGroupMap = data.roleGroupMap;
                        $(userGroupView.id.userGroupFormId).form('load', userGroup);

                        userGroupView.handler.loadUserGroupRoles(roleGroupMap);

                        userGroupView.handler.optWindow(userGroupView.id.userGroupWindow, "编辑用户组", "open")
                    } else {
                        $.messager.alert("提示信息", '未知错误!', "info");
                    }
                });
                userGroupView.handler.loadUserGroupTree(userGroupView.optNode.id, userGroupView.optNode.text, node.id, node.text);
            }
        },
        del: function () {
            var node = $(userGroupView.id.userGroupTree).tree('getSelected');
            if (node.attributes.child > 0) {
                $.messager.alert('错误信息', '"' + node.text + '"用户组存在子用户组!', 'error');
            } else {
                $.messager.confirm('提示信息', '是否删除"' + node.text + '"用户组？', function (r) {
                    if (r) {
                        userGroupView.optNode = $(userGroupView.id.userGroupTree).tree("getParent", node.target);
                        if (node) {
                            $.request('usergroup/delete.action', {uuid: node.id}, function (data) {
                                if (!data) {
                                    $.messager.alert("提示信息", '未知错误!', "info");
                                } else {
                                    $(userGroupView.id.userGroupTree).tree("remove", node.target);
                                }
                            });
                        } else {
                            $.messager.alert("提示信息", '请选择系统!', "info");
                        }
                    }
                });
            }
        },
        reset: function () {
            var selected = $(userGroupView.id.userGroupTree).tree('getSelected');
            this.detail(selected);
        },
        cancel: function (formID, windowID) {
            $("#ugpUUID").val('');
            $(formID).form("reset");
            $(windowID).window('close');
        },
        addUserGroup: function () {
            var selected = $(userGroupView.id.userGroupTree).tree('getSelected');
            var selected_text = selected.text;
            this.loadUserGroupTree(selected.id, selected.text, null, null);
            userGroupView.optNode = selected;
            userGroupView.handler.optWindow(userGroupView.id.userGroupWindow, "新建用户组", "open");
            userGroupView.handler.loadUserGroupRoles(null);
        },
        loadUserGroupTree: function (pid, ptext, id, text) {
            $("#pid").combotree({
                url: "usergroup/tree.action",
                onLoadSuccess: function () {
                    if (pid == id) {
                        $("#pid").combotree("setValue", 0);
                        $("#pid").combotree("setText", "新建组");
                    } else {
                        $("#pid").combotree("setValue", pid);
                        $("#pid").combotree("setText", ptext);
                    }
                    var newGroup = $(this).tree("find", 0);
                    if (newGroup == null) {
                        $(this).tree("append", {
                            parent: null,
                            data: [
                                {id: 0, text: '新建组'}
                            ]
                        });
                    }
                },
                onSelect: function (node) {
                    if (node.id == id) {
                    } else {
                        userGroupView.optNode = node;
                    }
                }
            });
        },
        loadUserGroupRoles: function (roleGroupMap) {
            $.request('role/list.action', {}, function (data) {
                if (!data) {
                    $.messager.alert("提示信息", '未知错误!', "info");
                } else {
                    var list = data.rows
                    $("#userGroupRoles").combobox({
                        required: true,
                        multiple: false,
                        data: list,
                        onLoadSuccess: function () {
                            if (roleGroupMap) {
                                for (var i = 0; i < roleGroupMap.length; i++) {
                                    $("#userGroupRoles").combobox("select", roleGroupMap[i].roleId);
                                }
                            }
                        }
                    });
                }
            });
        },
        refresh: function () {
            if (userGroupView.optNode != null) {
                if (userGroupView.optNode.attributes.ugpParentguid == "0") {
                    $(userGroupView.id.userGroupTree).tree("reload", null);
                } else {
                    $(userGroupView.id.userGroupTree).tree("reload", userGroupView.optNode.target);
                }
                if (userGroupView.optNode.attributes.child == 0) {
                    var node = $(userGroupView.id.userGroupTree).tree("getParent", userGroupView.optNode.target);
                    $(userGroupView.id.userGroupTree).tree("reload", node.target);
                    $(userGroupView.id.userGroupTree).tree("expandTo", node.target);
                }
                userGroupView.optNode = null;
            } else {
                this.refreshTree(userGroupView.optNode);
            }
        },
        refreshTreeNodes: function () {
            $(userGroupView.id.userGroupTree).tree("reload", null);
        },
        formatState: function (val, row) {
            if (val == -1) {
                return '删除';
            } else if (val == 0) {
                return '审核';
            } else if (val == 1) {
                return '正常';
            }
        },
        optWindow: function (id, title, opt) {
            if (title) {
                $(id).window({
                    title: title
                });
            }
            $(id).window(opt);
        },
        updateUserGroupUser: function () {
            $.submit(userGroupView.id.userGroupUserForm, 'usergroup/updateUserGroupUser.action', function (data) {
                $(userGroupView.id.userGroupUserForm).form('reset');
                $(userView.id.roleGrid).datagrid("clearSelections");
                userGroupView.handler.optWindow(userGroupView.id.userGroupUserWindow, "", "close");
                $.messager.alert('提示信息', '用户组修改成功!', 'info');
            });
        },
        editUserGroup: function () {
            var selectedUsers = $(userView.id.roleGrid).datagrid("getSelections");
            if (selectedUsers.length > 0) {
                var usersData = $(userView.id.roleGrid).datagrid("getData");
                $("#editUSER").combobox("loadData", usersData.rows);
                for (var i = 0; i < selectedUsers.length; i++) {
                    $("#editUSER").combobox("select", selectedUsers[i].id);
                }
                $("#editUGP").combotree({
                    url: 'usergroup/tree.action?dt=' + new Date(),
                    animate: true,
                    multiple: true
                });
                if (selectedUsers.length == 1) {
                    $.request('usergroup/getUGU.action', {userIDs: selectedUsers.filter('id').toString()}, function (data) {
                        var values = new Array();
                        for (var i = 0; i < data.result.length; i++) {
                            var tempID = data.result[i].groupId;
                            values.push(tempID);
                        }
                        $("#editUGP").combotree("setValues", values);
                    });
                }
                userGroupView.handler.optWindow(userGroupView.id.userGroupUserWindow, null, "open");
            } else {
                $.messager.alert('错误信息', '请选择用户！', 'error');
            }
        }
    }
};

</script>
<!-- 用户 -->
<script type="text/javascript">
    var userView = {
        id: {
            roleGrid: '#userGrid',
            roleEditForm: '#userEditForm',
            userPasswordForm: '#userPasswordForm',
            roleSearchForm: '#userSearchForm',
            userRoleForm: '#userRoleForm',
            userPWDForm: "#userPWDForm",
            roleEditDialog: '#userEditDialog',
            userRoleDialog: '#userRoleDialog',
            userPWDDialog: "#userPWDDialog"
        },
        init: function () {
            var userDataGrid = $(userView.id.roleGrid);
            userDataGrid.datagrid({
                rownumbers: true,
                fit: true,
                fitColumns: true,
                autoRowHeight: false,
                singleSelect: false,
                pagination: true,
                columns: [
                    [
                        {field: 'ck', title: '选择', checkbox: true, width: 80, align: 'center'},
                        //{field: 'id', title: 'id', width: 100, align: 'center', hidden: false},
                        {field: 'usrAccount', title: '账号', width: 100, align: 'center'},
                        {field: 'usrName', title: '用户名称', width: 200, align: 'center'},
                        {field: 'usrIsbindip', title: 'IP', width: 200, align: 'center', formatter: function (value, row, index) {
                            return value === '1' ? '是' : '否'
                        }},
                        {field: 'usrIsbindmac', title: 'MAC地址', width: 200, align: 'center', formatter: function (value, row, index) {
                            return value === '1' ? '是' : '否'
                        }},
                        {field: 'usrState', title: '用户状态', width: 200, align: 'center', formatter: function (value, row, index) {
                            if (value == "1") {
                                return "正常";
                            } else if (value == "0") {
                                return "审核";
                            } else if (value == "-1") {
                                return "删除";
                            }
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
            this.load();
        },
        load: function (pageNo, pageSize) {
            pageNo = pageNo || 1;
            pageSize = pageSize || 10;
            $.request('user/list.action', {dt: new Date(), page: pageNo, rows: pageSize}, function (data) {
                var userDataGrid = $(userView.id.roleGrid);
                userDataGrid.datagrid('loadData', data);
                userDataGrid.datagrid('getPager').pagination('refresh', {pageNumber: pageNo});
            });
        },
        handler: {
            search: function () {
                $.submit(userView.id.roleSearchForm, 'user/list.action', function (data) {
                    $(userView.id.roleGrid).datagrid('loadData', data);
                });
            },
            detail: function (isUpdate) {
                //select record
                if (isUpdate) {
                    var userSelections = $(userView.id.roleGrid).datagrid("getSelections");
                    switch (userSelections.length) {
                        case 1:
                            userSelections[0].rePass = userSelections[0].usrPassword;
                            $(userView.id.roleEditForm).form('load', userSelections[0]);
                            $(userView.id.roleEditDialog).dialog('open');
                            $("#pwd").attr('disabled', true);
                            $("#rpwd").attr('disabled', true);
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
                    userView.load(1, 10);
                    $(userView.id.roleEditDialog).dialog('close');
                });
            },
            del: function () {
                var userSelections = $(userView.id.roleGrid).datagrid("getSelections");
                if (userSelections.length == 0) return;
                $.messager.confirm('删除提示', '您确定要删除' + userSelections.length + '条记录?', function (r) {
                    if (r) {
                        $.request('user/del.action', {ids: userSelections.filter('id').toString()}, function (data) {
                            userView.load();
                        });
                    }
                });
            },
            rePass: function () {
                var userSelections = $(userView.id.roleGrid).datagrid("getSelections");
                switch (userSelections.length) {
                    case 1:
                        $("#pwd_dialog_user").val(userSelections[0].id);
                        $(userView.id.userPWDDialog).dialog('open');
                        break;
                    case 0:
                        $.messager.alert("提示信息", '请选择用户!', "info");
                        break;
                    default :
                        $.messager.alert("提示信息", '对不起,请选择一条数据进行修改!', "info");
                        break;
                }
            },
            reset: function () {
                $('#userUUID').val('');
                $(userView.id.roleEditForm).form('reset');
            },
            detailRole: function () {
                var selectedUsers = $(userView.id.roleGrid).datagrid("getSelections");
                var bindAccountAndRole = function (arr) {
                    if (arr.length > 0) {
                        var roleDialogAccount = $('#role_dialog_account'),
                                roleDialogUser = $('#role_dialog_user');
                        roleDialogAccount.val(selectedUsers[0].usrAccount);
                        roleDialogUser.val(selectedUsers[0].id);
                        for (var i = 1; i < selectedUsers.length; i++) {
                            roleDialogUser.val(roleDialogUser.val() + ',' + selectedUsers[i].id);
                            roleDialogAccount.val(roleDialogAccount.val() + ',' + selectedUsers[i].usrAccount);
                        }
                    }
                };
                switch (selectedUsers.length) {
                    case 0:
                        $.messager.alert('错误信息', '请选择用户！', 'error');
                        return;
                    case 1:
                        bindAccountAndRole(selectedUsers);
                        $.request('user/userrole.action', {id: selectedUsers[0].id}, function (data) {
                            $('#role-combobox').combobox('setValues', data);
                        });
                        break;
                    default:
                        bindAccountAndRole(selectedUsers);
                        $('#role-combobox').combobox('setValues', []);
                        break;
                }
                $(userView.id.userRoleDialog).dialog('open');
            },
            editUserRole: function () {
                $.submit(userView.id.userRoleForm, 'user/grantrole.action', function (result) {
                    $(userView.id.userRoleDialog).dialog('close');
                    if (result) {
                        $(userView.id.roleGrid).datagrid("clearSelections");
                        $.messager.alert('提示信息', '授予角色成功!', 'info');
                    }
                });
            },
            resetPWD: function () {
                $.submit(userView.id.userPWDForm, 'user/sou.action', function (result) {
                    $(userView.id.userPWDForm).form("reset");
                    $(userView.id.userPWDDialog).dialog('close');
                    if (result) {
                        $(userView.id.roleGrid).datagrid("clearSelections");
                        $.messager.alert('提示信息', '重置密码成功!', 'info');
                    }
                });
            }
        }
    };
    userGroupView.init();
    userView.init();

</script>
<!-- 用户 END -->