$(function () {
    $(document).on("keydown", function (event) {
        if (event.keyCode === 13) {
            if (!$.trim($("#account").val())) {
                $("#account").focus();
                return false;
            }
            else if (!$.trim($("#password").val())) {
                $("#password").focus();
                return false;
            }
            login();
            return false;
        }
    });
    $("#account").focus();
    if ($.trim($("#account").val())) {
        $("#password").focus();
    }
});

function login() {
    var validate = function (account, password) {
        account.val($.trim(account.val()));
        //
        var av = account.val(), pv = password.val();
        if (!av) {
            alert('请填写用户名！');
            account.focus();
            return false;
        }
        if (!pv) {
            alert('请填写密码！');
            password.focus();
            return false;
        }
        return {account: av, password: pv}
    }

    var post = function (loginInfo) {
        $("#loading").show();
        try {
            $.ajax({
                type: "POST",
                url: "system/login.action",
                data: 'account=' + loginInfo.account + '&password=' + loginInfo.password,
                cache: false,
                success: function (data) {
                    if (data === 'success') {
                        window.location.href = "system/index.action";
                    } else{
                        alert(data.message);
                    }
                },
                error: function (a) {
                    alert(a.responseText);
                }
            });
        } catch (igone) {
        }
        $("#loading").hide();

    }

    var result = validate($("#account"), $("#password"));
    if (result) {
        post(result);
    }
};

function logout(href) {
    $.request('logout.action', {dt: new Date()}, function (data) {
        window.location.href = (href || '../login.jsp');
    });
};