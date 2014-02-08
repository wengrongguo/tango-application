<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" style="margin: 5px;" data-options="fit:true">
    <div style="height:5px;"></div>
    <div data-options="region:'west',split:true,collapsible:false" title="系统管理" style="width:200px;">
        <ul id="sub_system_tree"></ul>
    </div>
    <div data-options="region:'center'">
        <div style="padding: 3px 2px; margin-bottom: 10px; border-bottom: 1px solid #ccc">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="subSystemView.handler.add();">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="subSystemView.handler.del();">删除</a>
        </div>
        <div style="margin: 10px">
            <form id="subsystem_form" method="post">
                <!-- 隐藏域 -->
                <s:hidden id="ssmUUID" name="id" cssClass="easyui-validatebox"></s:hidden>
                <table>
                    <tr>
                        <td><label>系统名称</label></td>
                        <td>
                            <input class="easyui-validatebox" type="text" name="ssmName" data-options="prompt:'输入系统名称.',required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>菜单管理路径</label></td>
                        <td>
                            <input class="easyui-validatebox" type="text" name="ssmMenuapi" data-options="prompt:'输入系统菜单管理路径.'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>数据API</label></td>
                        <td>
                            <input class="easyui-validatebox" type="text" name="ssmDataapi" data-options="prompt:'输入数据API.'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>元素API</label></td>
                        <td>
                            <input class="easyui-validatebox" type="text" name="ssmElementapi" data-options="prompt:'输入元素API.'"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>状态</label></td>
                        <td>
                            <select id="ssmState" name="ssmState" class="easyui-combobox" style="width: 150px;" data-options="editable:false,panelHeight:100,panelWidth:150">
                                <option value="1" selected="selected">正常</option>
                                <option value="-1">删除</option>
                                <option value="0">注销</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok',toggle:true" onclick="subSystemView.handler.saveOrUpdate();">保存</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',toggle:true" onclick="subSystemView.handler.cancel();">取消</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var subSystemView = {
        id: {
            subSystemFormId: '#subsystem_form',
            subSystemTree: '#sub_system_tree'
        },
        init: function () {
            $(subSystemView.id.subSystemTree).tree({
                animate: true,
                onClick: function (node) {
                    //detail for sub system
                    subSystemView.handler.detail(node);
                }
            });
            //
            $.initValidateBox();
            this.load();
        },
        load: function () {
            //query date[action address]
            $.request('subsystem/tree.action', {dt: new Date()}, function (data) {
                $(subSystemView.id.subSystemTree).tree('loadData', data.result);
            });
        },
        handler: {
            saveOrUpdate: function () {
                //saveOrUpdate of sub system
                $.submit(subSystemView.id.subSystemFormId, 'subsystem/sou.action', function () {
                    subSystemView.load();
                    subSystemView.handler.reset();
                });
            },
            detail: function (node) {
                if (node) {
                    $.request('subsystem/detail.action', {uuid: node.id}, function (data) {
                        if (data) {
                            data.id = node.id;
                            $(subSystemView.id.subSystemFormId).form('load', data);
                        } else {
                            throw new Error();
                        }
                    });
                }
            },
            add: function () {
                //$(subSystemView.id.subSystemTree).tree('unselect');
                subSystemView.handler.reset();
            },
            del: function () {
                var node = $(subSystemView.id.subSystemTree).tree('getSelected');
                if (node) {
                    $.request('subsystem/delete.action', {uuid: node.id}, function (data) {
                        if (!data) {
                            throw new Error();
                        }
                    });
                    subSystemView.handler.reset();
                    //reload tree
                    subSystemView.load();
                } else {
                    $.messager.alert("提示信息", '请选择系统!', "info");
                }
            },
            reset: function () {
                $('#ssmUUID').val('');
                $(subSystemView.id.subSystemFormId).form('reset');
            },
            cancel: function () {
                var node = $(subSystemView.id.subSystemTree).tree('getSelected');
                $(subSystemView.id.subSystemFormId).form('load', node.attributes);
            }
        }
    };
    subSystemView.init();
</script>
