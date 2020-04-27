$(function () {
    // 获取验证码图片
    getVerifyCode('#verify-code-img');

    getErrMsg();

    // 检查手机号格式
    $('#phone').blur(function () {
        checkPhone($('#phone').val(), $('.login-form>.notice-box'));
    });

    // 检查密码格式
    $('#password').blur(function () {
        checkPassword();
    });

    // 检查登录的所有参数的格式
    $('#login-in').click(function () {
        return checkAll();
    });

    // 获取验证码图片
    $('#verify-code-img').click(function () {
        getVerifyCode('#verify-code-img');
    });

    // 获取验证码图片
    // $('.verify-code-img').click(function () {
    //     getVerifyCode('.verify-code-img');
    // });

    // 忘记密码
    $('.forget-password').click(function () {
        $('#forget-password').modal();
        let time = $.cookie('time');
        if (time) {
            setTime(time);
        }
        // getVerifyCode('.verify-code-img');
    });

    // 获取短信验证码
    $('.send-sms-code').click(function () {
        smsCode($('.input-phone').val(), $('#forget-password .notice-box'));
    });

    // 验证用户身份
    $('.sure-modify-password').click(function () {
        modifyPassword();
    });
});

/**
 * 获取错误信息
 */
function getErrMsg() {
    let noticeBox = $('.login-error>.notice-box');
    let errMsg = $.cookie('err');
    if (errMsg) {
        showNoticeBox(noticeBox, errMsg);
    } else {
        showNoticeBox(noticeBox, "手机号不存在或密码错误!");
    }
    $.removeCookie('err');
    let username = $.cookie('username');
    let password = $.cookie('password');
    $('#phone').attr('value', username);
    $('#password').attr('value', password);
    $.removeCookie(username);
}

/**
 * 检查手机号格式
 *
 * @param phone
 * @param noticeBox
 */
function checkPhone(phone, noticeBox) {
    if (phone) {
        let regExp = /^1[3-9]\d{9}$/;
        if (!regExp.test(phone)) {
            showNoticeBox(noticeBox, "手机号格式错误！");
            return false;
        }
        return true;
    } else {
        showNoticeBox(noticeBox, "手机号不能为空！");
        return false;
    }
}

/**
 * 检查密码格式
 *
 * @returns {boolean}
 */
function checkPassword() {
    let noticeBox = $('.login-form>.notice-box');
    let password = $('#password').val();
    if (!password) {
        showNoticeBox(noticeBox, "密码不能为空！");
        return false;
    }
    return true;
}

/**
 * 表单提交检查所有
 *
 * @returns {boolean}
 */
function checkAll() {
    if (checkPhone($('#phone').val(), $('.login-form>.notice-box'))
        && checkPassword() && checkVerifyCode()) {
        return true;
    }
    // 重新获取验证图片
    getVerifyCode('#verify-code-img');
    return false;
}

/**
 * 检查验证码是否输入正确
 */
function checkVerifyCode() {
    let noticeBox = $('.login-form .notice-box');
    let code = $('.verify-code').val();
    if (!code) {
        showNoticeBox(noticeBox, '验证码不能为空！');
        return false;
    }
    return true;
}

/**
 * 获取短信验证码
 */
function smsCode(phone, noticeBox) {
    if (checkPhone(phone, noticeBox)) {
        $.ajax({
            url: '/verify/smsCode',
            type: 'GET',
            data: {
                phone: phone
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    showNoticeBox(noticeBox, '短信发送成功');
                } else {
                    showNoticeBox(noticeBox, data.msg);
                }
            },
            error: function () {
                showNoticeBox(noticeBox, '短信发送失败');
            }
        });
        setTime();
    }
}

/**
 * 60s倒计时
 */
function setTime(number = 60) {
    let btn = $(".send-sms-code");
    let countdown = function () {
        if (number === 0) {
            btn.attr("disabled", false);
            btn.html("发送验证码");
            number = 60;
            $.removeCookie('time');
            return;
        } else {
            btn.attr("disabled", true);
            btn.html(number + "秒 重新发送");
            number--;
        }
        $.cookie('time', number);
        setTimeout(countdown, 1000);
    };
    setTimeout(countdown, 1000);
}

/**
 * 修改密码
 */
function modifyPassword() {
    let phone = $('.input-phone').val();
    let code = $('.input-sms-code').val();
    let pwd = $('.input-password').val();
    let rePwd = $('.input-re-password').val();
    let noticeBox = $('#forget-password .notice-box');
    if (!checkPhone(phone, noticeBox)) {
        return false;
    } else if (!code){
        showNoticeBox(noticeBox, "验证码不能为空！");
        return false;
    }else if (!pwd) {
        showNoticeBox(noticeBox, "密码不能为空！");
        return false;
    } else if (!rePwd) {
        showNoticeBox(noticeBox, "确认密码不能为空！");
        return false;
    } else if (pwd !== rePwd) {
        showNoticeBox(noticeBox, "两次密码不一致！");
        return false;
    } else {
        let dataObj = {
            code: code,
            phone: phone,
            password: pwd
        };

        $.ajax({
            url: '/user/modifyPassword',
            type: 'POST',
            data: JSON.stringify(dataObj),
            dataType: 'JSON',
            contentType: "application/json;charsetset=UTF-8",
            success: function (data) {
                if (data.success) {
                    $('input').val('');
                    $('#forget-password').modal('hide');
                    $('#input-password').modal('hide');
                    showNoticeBox($('.login-form>.notice-box'), '密码修改成功');
                } else {
                    showNoticeBox(noticeBox, data.msg);
                }
            },
            error: function () {
                showNoticeBox(noticeBox, '网络异常，请稍后重试');
            }
        });
    }
}