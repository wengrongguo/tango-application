/// <reference path="../intellisense/jquery-1.2.6-vsdoc-cn.js" />
/****************************************
data:[{
id:1, //ID只能包含英文数字下划线中划线
text:"node 1",
value:"1",
showcheck:false,
checkstate:0,         //0,1,2
hasChildren:true,
isexpand:false,
complete:false, 是否已加载子节点
ChildNodes:[] // child nodes
},
..........
]
author:xuanye.wan@gmail.com
***************************************/
(function ($) {
    $.fn.swapClass = function (c1, c2) {
        return this.removeClass(c1).addClass(c2);
    };
    $.fn.switchClass = function (c1, c2) {
        if (this.hasClass(c1)) {
            return this.swapClass(c1, c2);
        }
        else {
            return this.swapClass(c2, c1);
        }
    };
    $.fn.treeview = function (settings) {
        var dfop =
            {
                method: "POST",
                datatype: "json",
                url: false,
                cbiconpath: "/Images/icons/",
                icons: ["checkbox_0.gif", "checkbox_1.gif", "checkbox_2.gif"],
                emptyiconpath: "/Images/s.gif",
                showcheck: false, //是否显示选择            
                oncheckboxclick: false, //当checkstate状态变化时所触发的事件，但是不会触发因级联选择而引起的变化
                onnodeclick: false,
                cascadecheck: true,
                showecicon: true, //是否显示 图标
                data: null,
                clicktoggle: true, //点击节点展开和收缩子节点
                theme: "bbit-tree-arrows" //bbit-tree-lines ,bbit-tree-no-lines,bbit-tree-arrows
            };

        $.extend(dfop, settings);
        var treenodes = dfop.data;
        var me = $(this);
        var id = me.attr("id");
        if (id == null || id == "") {
            id = "bbtree" + new Date().getTime();
            me.attr("id", id);
        }

        var html = [];
        buildtree(dfop.data, html);
        me.addClass("bbit-tree").html(html.join(""));
        InitEvent(me);
        html = null;
        //预加载图片
        if (dfop.showcheck) {
            for (var i = 0; i < 3; i++) {
                var im = new Image();
                im.src = dfop.cbiconpath + dfop.icons[i];
            }
        }

        //region 
        function buildtree(data, ht) {
            ht.push("<div class='bbit-tree-bwrap'>"); // Wrap ;
            ht.push("<div class='bbit-tree-body'>"); // body ;
            ht.push("<ul class='bbit-tree-root ", dfop.theme, "'>"); //root
            if (data && data.length > 0) {
                var l = data.length;
                for (var i = 0; i < l; i++) {
                    buildnode(data[i], ht, 0, i, i == l - 1);
                }
            }
            else {
                asnyloadc(null, false, function (data) {
                    if (data && data.length > 0) {
                        treenodes = data;
                        dfop.data = data;
                        var l = data.length;
                        for (var i = 0; i < l; i++) {
                            buildnode(data[i], ht, 0, i, i == l - 1);
                        }
                    }
                });
            }
            ht.push("</ul>"); // root and;
            ht.push("</div>"); // body end;
            ht.push("</div>"); // Wrap end;
        }
        //endregion
        function buildnode(nd, ht, deep, path, isend) {
            var nid = nd.id.replace(/[^\w]/gi, "_");
            ht.push("<li class='bbit-tree-node'>");
            ht.push("<div id='", id, "_", nid, "' tpath='", path, "' unselectable='on' title='", nd.text, "'");
            var cs = [];
            cs.push("bbit-tree-node-el");
            if (nd.hasChildren) {
                cs.push(nd.isexpand ? "bbit-tree-node-expanded" : "bbit-tree-node-collapsed"); //todo:史晓军 2010-8-16
            }
            else {
                cs.push("bbit-tree-node-leaf"); //todo:史晓军 2010-8-16
            }
            if (nd.classes) { cs.push(nd.classes); }

            ht.push(" class='", cs.join(" "), "'>");
            //span indent
            ht.push("<span class='bbit-tree-node-indent'>");
            if (deep == 1) {
                ht.push("<img class='bbit-tree-icon' src='", dfop.emptyiconpath, "'/>");
            }
            else if (deep > 1) {
                ht.push("<img class='bbit-tree-icon' src='", dfop.emptyiconpath, "'/>");
                for (var j = 1; j < deep; j++) {
                    ht.push("<img class='bbit-tree-elbow-line' src='", dfop.emptyiconpath, "'/>");
                }
            }
            ht.push("</span>");
            //img
            cs.length = 0;
            if (nd.hasChildren) {
                if (nd.isexpand) {
                    cs.push(isend ? "bbit-tree-elbow-end-minus" : "bbit-tree-elbow-minus");
                }
                else {
                    cs.push(isend ? "bbit-tree-elbow-end-plus" : "bbit-tree-elbow-plus");
                }
            }
            else {
                cs.push(isend ? "bbit-tree-elbow-end" : "bbit-tree-elbow");
            }
            if (dfop.showecicon)
                ht.push("<img class='bbit-tree-ec-icon ", cs.join(" "), "' src='", dfop.emptyiconpath, "'/>");
            ht.push("<img class='bbit-tree-node-icon' src='", dfop.emptyiconpath, "'/>");
            //checkbox
            /*wwg
            if (dfop.showcheck && nd.showcheck) {
            if (nd.checkstate == null || nd.checkstate == undefined) {
            nd.checkstate = 0;
            }
            ht.push("<img  id='", id, "_", nid, "_cb' class='bbit-tree-node-cb' src='", dfop.cbiconpath, dfop.icons[nd.checkstate], "'/>");
            }*/
            if (nd.checkstate == null || nd.checkstate == undefined) {
                nd.checkstate = 0;
            }
            ht.push("<img  id='", id, "_", nid, "_cb' class='bbit-tree-node-cb'", (dfop.showcheck && nd.showcheck) ? "" : " style='display:none'", " src='", dfop.cbiconpath, dfop.icons[nd.checkstate], "'/>");
            //a
            ht.push("<a hideFocus class='bbit-tree-node-anchor' tabIndex=1 href='javascript:void(0);'>");
            ht.push("<span unselectable='on' id='tree_node_n_"+nd.id+"'>", nd.text, "</span>");
            ht.push("</a>");
            ht.push("</div>");
            //Child
            if (nd.hasChildren) {
                if (nd.isexpand) {
                    ht.push("<ul  class='bbit-tree-node-ct'  style='z-index: 0; position: static; visibility: visible; top: auto; left: auto;'>");
                    if (nd.ChildNodes) {
                        var l = nd.ChildNodes.length;
                        for (var k = 0; k < l; k++) {
                            nd.ChildNodes[k].parent = nd;
                            buildnode(nd.ChildNodes[k], ht, deep + 1, path + "." + k, k == l - 1);
                        }
                    }
                    ht.push("</ul>");
                }
                else {
                    ht.push("<ul style='display:none;'></ul>");
                }
            }
            ht.push("</li>");
            nd.render = true;
        }
        function getItem(path) {
            var ap = path.split(".");
            var t = treenodes;
            for (var i = 0; i < ap.length; i++) {
                if (i == 0) {
                    t = t[ap[i]];
                }
                else {
                    t = t.ChildNodes[ap[i]];
                }
            }
            return t;
        }
        function check(item, state, type) {
            var pstate = item.checkstate;
            if (type == 1) {
                item.checkstate = state;
            }
            else {// 上溯
                var cs = item.ChildNodes;
                var l = cs.length;
                var ch = true;
                for (var i = 0; i < l; i++) {
                    if ((state == 1 && cs[i].checkstate != 1) || state == 0 && cs[i].checkstate != 0) {
                        ch = false;
                        break;
                    }
                }
                if (ch) {
                    item.checkstate = state;
                }
                else {
                    item.checkstate = 2;
                }
            }
            //change show
            if (item.render && pstate != item.checkstate) {
                var nid = item.id.replace(/[^\w]/gi, "_");
                var et = $("#" + id + "_" + nid + "_cb");
                if (et.length == 1) {
                    et.attr("src", dfop.cbiconpath + dfop.icons[item.checkstate]);
                }
            }
        }
        //遍历子节点
        function cascade(fn, item, args) {
            if (fn(item, args, 1) != false) {
                if (item.ChildNodes != null && item.ChildNodes.length > 0) {
                    var cs = item.ChildNodes;
                    for (var i = 0, len = cs.length; i < len; i++) {
                        cascade(fn, cs[i], args);
                    }
                }
            }
        }
        //冒泡的祖先
        function bubble(fn, item, args) {
            var p = item.parent;
            while (p) {
                if (fn(p, args, 0) === false) {
                    break;
                }
                p = p.parent;
            }
        }
        function nodeclick(e) {
            var path = $(this).attr("tpath");
            var et = e.target || e.srcElement;
            var item = getItem(path);
            if (et.tagName == "IMG") {
                // +号需要展开
                if ($(et).hasClass("bbit-tree-elbow-plus") || $(et).hasClass("bbit-tree-elbow-end-plus")) {
                    var ul = $(this).next(); //"bbit-tree-node-ct"
                    if (ul.hasClass("bbit-tree-node-ct")) {
                        ul.show();
                    }
                    else {
                        var deep = path.split(".").length;
                        if (item.complete) {
                            item.ChildNodes != null && asnybuild(item.ChildNodes, deep, path, ul, item);
                        }
                        else {
                            $(this).addClass("bbit-tree-node-loading");
                            asnyloadc(item, true, function (data) {
                                item.complete = true;
                                item.ChildNodes = data;
                                asnybuild(data, deep, path, ul, item);
                            });
                        }
                    }
                    if ($(et).hasClass("bbit-tree-elbow-plus")) {
                        $(et).swapClass("bbit-tree-elbow-plus", "bbit-tree-elbow-minus");
                    }
                    else {
                        $(et).swapClass("bbit-tree-elbow-end-plus", "bbit-tree-elbow-end-minus");
                    }
                    $(this).swapClass("bbit-tree-node-collapsed", "bbit-tree-node-expanded");
                    //todo:史晓军 2010-8-16 $(this).swapClass("bbit-tree-node-collapsed", "bbit-tree-node-expanded");
                }
                else if ($(et).hasClass("bbit-tree-elbow-minus") || $(et).hasClass("bbit-tree-elbow-end-minus")) {  //- 号需要收缩                    
                    $(this).next().hide();
                    if ($(et).hasClass("bbit-tree-elbow-minus")) {
                        $(et).swapClass("bbit-tree-elbow-minus", "bbit-tree-elbow-plus");
                    }
                    else {
                        $(et).swapClass("bbit-tree-elbow-end-minus", "bbit-tree-elbow-end-plus");
                    }
                    $(this).swapClass("bbit-tree-node-expanded", "bbit-tree-node-collapsed");
                    //todo:史晓军 2010-8-16  $(this).swapClass("bbit-tree-node-expanded", "bbit-tree-node-collapsed");
                }
                else if ($(et).hasClass("bbit-tree-node-cb")) // 点击了Checkbox
                {
                    var s = item.checkstate != 1 ? 1 : 0;
                    var r = true;
                    if (r != false) {
                        if (dfop.cascadecheck) {
                            //遍历
                            cascade(check, item, s);
                            //上溯
                            bubble(check, item, s);
                        }
                        else {
                            check(item, s, 1);
                        }
                    }
                    if (dfop.oncheckboxclick) {
                        r = dfop.oncheckboxclick.call(et, item, s);
                    }
                }
            }
            else {
                if (dfop.citem) {
                    var nid = dfop.citem.id.replace(/[^\w]/gi, "_");
                    $("#" + id + "_" + nid).removeClass("bbit-tree-selected");
                }
                dfop.citem = item;
                $(this).addClass("bbit-tree-selected");
                if (dfop.onnodeclick) {
                    if (!item.expand) {
                        item.expand = function () { expandnode.call(item); };
                    }
                    dfop.onnodeclick.call(this, item,e);
                }
            }
        }
        function expandnode() {
            var item = this;
            var nid = item.id.replace(/[^\w]/gi, "_");
            var img = $("#" + id + "_" + nid + " img.bbit-tree-ec-icon");
            if (img.length > 0) {
                img.click();
            }
        }
        function asnybuild(nodes, deep, path, ul, pnode) {
            var l = nodes.length;
            if (l > 0) {
                var ht = [];
                for (var i = 0; i < l; i++) {
                    nodes[i].parent = pnode;
                    buildnode(nodes[i], ht, deep, path + "." + i, i == l - 1);
                }
                ul.html(ht.join(""));
                ht = null;
                InitEvent(ul);
            }
            ul.addClass("bbit-tree-node-ct").css({ "z-index": 0, position: "static", visibility: "visible", top: "auto", left: "auto", display: "" });
            ul.prev().removeClass("bbit-tree-node-loading");
        }
        function asnyloadc(pnode, isAsync, callback) {
            if (dfop.url) {
                if (pnode && pnode != null)
                    var param = builparam(pnode);
                $.ajax({
                    type: dfop.method,
                    url: dfop.url,
                    data: param,
                    async: isAsync,
                    dataType: dfop.datatype,
                    success: callback,
                    error: function (e) { alert("error occur!"); }
                });
            }
        }
        function builparam(node) {
            var p = [{ name: "id", value: encodeURIComponent(node.id) }
                    , { name: "text", value: encodeURIComponent(node.text) }
                    , { name: "value", value: encodeURIComponent(node.value) }
                    , { name: "checkstate", value: node.checkstate}];
            return p;
        }
        function bindevent() {
            $(this).hover(function () {
                $(this).addClass("bbit-tree-node-over");
            }, function () {
                $(this).removeClass("bbit-tree-node-over");
            }).click(nodeclick)
             .find("img.bbit-tree-ec-icon").each(function (e) {
                 if (!$(this).hasClass("bbit-tree-elbow")) {
                     $(this).hover(function () {
                         $(this).parent().addClass("bbit-tree-ec-over");
                     }, function () {
                         $(this).parent().removeClass("bbit-tree-ec-over");
                     });
                 }
             });
        }
        function InitEvent(parent) {
            var nodes = $("li.bbit-tree-node>div", parent);
            nodes.each(bindevent);
        }
        function reflash(itemId) {
            var nid = itemId.replace(/[^\w-]/gi, "_");
            var node = $("#" + id + "_" + nid);
            if (node.length > 0) {
                node.addClass("bbit-tree-node-loading");
                var isend = node.hasClass("bbit-tree-elbow-end") || node.hasClass("bbit-tree-elbow-end-plus") || node.hasClass("bbit-tree-elbow-end-minus");
                var path = node.attr("tpath");
                var deep = path.split(".").length;
                var item = getItem(path);
                if (item) {
                    asnyloadc(item, true, function (data) {
                        item.complete = true;
                        item.ChildNodes = data;
                        item.isexpand = true;
                        if (data && data.length > 0) {
                            item.hasChildren = true;
                        }
                        else {
                            item.hasChildren = false;
                        }
                        var ht = [];
                        buildnode(item, ht, deep - 1, path, isend);
                        ht.shift();
                        ht.pop();
                        var li = node.parent();
                        li.html(ht.join(""));
                        ht = null;
                        InitEvent(li);
                        bindevent.call(li.find(">div"));
                    });
                }
            }
            else {
                alert("该节点还没有生成");
            }
        }
        function getck(items, c, fn) {
            for (var i = 0, l = items.length; i < l; i++) {
                (items[i].showcheck == true && items[i].checkstate == 1) && c.push(fn(items[i]));
                if (items[i].ChildNodes != null && items[i].ChildNodes.length > 0) {
                    getck(items[i].ChildNodes, c, fn);
                }
            }
        }
        function getCkAndHalfCk(items, c, fn) {
            for (var i = 0, l = items.length; i < l; i++) {
                (items[i].showcheck == true && (items[i].checkstate == 1 || items[i].checkstate == 2)) && c.push(fn(items[i]));
                if (items[i].ChildNodes != null && items[i].ChildNodes.length > 0) {
                    getCkAndHalfCk(items[i].ChildNodes, c, fn);
                }
            }
        }
        ///wwg 2010-12-30 是否显示 复选框
        function setshowcheck(items, showcheck) {
            for (var item in items) {
                item = items[item];
                item.showcheck = showcheck;
                var nid = item.id.replace(/[^\w-]/gi, "_");
                var _cb = $("#" + id + "_" + nid + "_cb");
                showcheck == true ? _cb.show() : _cb.hide();
                if (item.ChildNodes) {
                    setshowcheck(item.ChildNodes, showcheck);
                }
            }
        }
        //wwg 2010-12-30 点击复选框事件处理
        function checkboxclick(item) {
            var nid = item.id.replace(/[^\w-]/gi, "_");
            var _cb = $("#" + id + "_" + nid + "_cb");
            _cb.click();
        }
        ///wwg 复选框选中状态设置
        function checkboxchecked(nid, checked) {
            var nid = nid.replace(/[^\w-]/gi, "_");
            var _cb = $("#" + id + "_" + nid);
            var path = $("#" + id + "_" + nid).attr("tpath");
            if (!path || path == "") return false;
            var item = getItem(path);

            var s = item.checkstate != 1 ? 1 : 0;
            if (typeof (checked) == "boolean") { s = (checked ? 1 : 0); }
            var r = true;
            if (dfop.cascadecheck) {
                //遍历
                cascade(check, item, s);
                //上溯
                bubble(check, item, s);
            }
            else {
                check(item, s, 1);
            }
            return true;
        }
        ///wwg 设置所有checkbox
        function checkboxallchecked(checked, nodes) {
            if (typeof (checked) != "boolean") return false;
            if (!nodes) nodes = treenodes;
            for (var itme in nodes) {
                if (nodes[itme].ChildNodes.length > 0) {
                    checkboxallchecked(checked, nodes[itme].ChildNodes);
                }
                checkboxchecked(nodes[itme].id, checked);
            }
            return true;
        }
        me[0].t = {
            getSelectedNodes: function (gethalfchecknode) {
                var s = [];
                if (gethalfchecknode) {
                    getCkAndHalfCk(treenodes, s, function (item) { return item; });
                }
                else {
                    getck(treenodes, s, function (item) { return item; });
                }
                return s;
            },
            getSelectedValues: function () {
                var s = [];
                getck(treenodes, s, function (item) { return item.value; });
                return s;
            },
            getCurrentItem: function () {
                return dfop.citem;
            },
            reflash: function (itemOrItemId) {
                var id;
                if (typeof (itemOrItemId) == "string") {
                    id = itemOrItemId;
                }
                else {
                    id = itemOrItemId.id;
                }
                reflash(id);
            },
            ///wwg 2010-12-30 是否显示 复选框
            ShowCheckBox: function (showcheck) {
                dfop.showcheck = showcheck;
                setshowcheck(dfop.data, showcheck);
            },
            ///王万刚 获取节点
            getNodes: function () {
                if (dfop.data.length > 0)
                    return dfop.data;
                else
                    return null;
            },
            ///王万刚 获取节点
            getDfop: function () {
                return dfop;
            },
            //wwg 2010-12-30 点击复选框事件处理
            CheckBoxClick: function (item) {
                checkboxclick(item);
            },
            CheckBoxChecked: function (id, checked) {
                return checkboxchecked(id, checked);
            },
            CheckBoxAllChecked: function (checked) {
                return checkboxallchecked(checked);
            }
        };
    };
    //获取所有选中的节点的Value数组
    $.fn.getTSVs = function () {
        if (this[0] && this[0].t) {
            return this[0].t.getSelectedValues();
        }
        return null;
    };
    //获取所有选中的节点的Item数组
    $.fn.getTSNs = function (gethalfchecknode) {
        if (this[0] && this[0].t) {
            return this[0].t.getSelectedNodes(gethalfchecknode);
        }
        return null;
    };
    $.fn.getTCT = function () {
        if (this[0] && this[0].t) {
            return this[0].t.getCurrentItem();
        }
        return null;
    };
    $.fn.reflash = function (ItemOrItemId) {
        if (this[0] && this[0].t) {
            return this[0].t.reflash(ItemOrItemId);
        }
    };
    ///wwg 2010-12-30 是否显示 复选框
    $.fn.ShowCheckBox = function (showcheck) {
        if (this[0] && this[0].t) {
            return this[0].t.ShowCheckBox(showcheck);
        }
    }
    ///王万刚 获取节点
    $.fn.getNodes = function () {
        if (this[0] && this[0].t) {
            return this[0].t.getNodes();
        }
    };
    $.fn.getDfop = function () {
        if (this[0] && this[0].t) {
            return this[0].t.getDfop();
        }
    };
    ///王万刚 执行 checkbox点击事件
    $.fn.CheckBoxClick = function (item) {
        if (this[0] && this[0].t) {
            this[0].t.CheckBoxClick(item);
        }
    };
    ///王万刚 设置  复选框状态
    $.fn.CheckBoxChecked = function (id, checked) {
        if (this[0] && this[0].t) {
            return this[0].t.CheckBoxChecked(id, checked);
        }
    };
    ///王万刚 设置 所有已经加载的复选框状态
    $.fn.CheckBoxAllChecked = function (checked) {
        if (this[0] && this[0].t) {
            return this[0].t.CheckBoxAllChecked(checked);
        }
    }
})(jQuery);