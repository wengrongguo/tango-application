/**
 * Jquery 扩展
 * User: tango
 * Date: 13-6-18
 * Time: 上午10:13
 */
jQuery.extend({
    //ajax
    request: function (url, param, successFun, errorFun) {
        $.messager.progress();
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            dataType: "JSON",
            cache: false,
            beforeSend: function () {
                //$.messager.loading.show(null, "正在加载", 200);
            },
            success: function (data) {
                //$.messager.loading.hide();
                $.messager.progress('close');
                try {
                    if (data && data.result && data.result === 'error') {
                        throw new Error(data.message);
                    }
                    if (successFun) {
                        successFun(data)
                    }
                } catch (e) {
                    $.messager.alert("错误信息", e.message, "info");
                    if (errorFun) {
                        errorFun(e.message);
                    }
                }
            },
            error: function (err) {
                //$.messager.loading.hide();
                $.messager.progress('close');
                $.messager.alert("错误信息", err.responseText || err.message, "error", errorFun);
            }
        });
    },
//form submit
    submit: function (formId, url, successFn) {
        $.messager.progress();
        $(formId).form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                }
                return isValid;
            },
            success: function (data) {
                data = eval('(' + data + ')');
                $.messager.progress('close');
                try {
                    if (data.result === 'error') {
                        throw new Error(data.message);
                    }
                    if (successFn) {
                        successFn(data)
                    }
                } catch (e) {
                    $.messager.alert("错误信息", e.message, "info");
                }
            }
        });
    },
//init validate for easyui input
    initValidateBox: function () {
        $('input.easyui-validatebox').validatebox({
            tipOptions: {	// the options to create tooltip
                showEvent: 'mouseenter',
                hideEvent: 'mouseleave',
                showDelay: 0,
                hideDelay: 0,
                zIndex: '',
                onShow: function () {
                    if (!$(this).hasClass('validatebox-invalid')) {
                        if ($(this).tooltip('options').prompt) {
                            $(this).tooltip('update', $(this).tooltip('options').prompt);
                        } else {
                            $(this).tooltip('tip').hide();
                        }
                    } else {
                        $(this).tooltip('tip').css({
                            color: '#000',
                            borderColor: '#CC9933',
                            backgroundColor: '#FFFFCC'
                        });
                    }
                },
                onHide: function () {
                    if (!$(this).tooltip('options').prompt) {
                        $(this).tooltip('destroy');
                    }
                }
            }
        }).tooltip({
                position: 'right',
                content: function () {
                    var opts = $(this).validatebox('options');
                    return opts.prompt;
                },
                onShow: function () {
                    $(this).tooltip('tip').css({
                        color: '#000',
                        borderColor: '#CC9933',
                        backgroundColor: '#FFFFCC'
                    });
                }
            });
    }
})
;
/**
 *
 * easyui 时间验证
 * param date
 * @return {string}
 */
$.fn.datebox.defaults.formatter = function (date) {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
};