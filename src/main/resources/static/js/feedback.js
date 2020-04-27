/**
 * 检查input是否为空
 */
function checkInput(selector) {
    return $(selector).filter(function () {
        return $.trim($(this).val()).length == 0
    }).length != 0;
}

/**
 * 检查邮箱格式
 * @param selector
 */
function checkEmail(selector) {
    // let regExp = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    // let regExp = /\w+(\.\w)*@\w+(\.\w{2,3}){1,3}/;
    // let regExp = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
    let regExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return !regExp.test($(selector).val().trim());
}

$(function () {
    let noticeBox = $('.notice-box');
    $('#sure-btn').click(function () {
        if (checkInput('#name')) {
            showNoticeBox(noticeBox, "昵称不能为空！");
        } else if (checkInput('#email')) {
            showNoticeBox(noticeBox, "邮箱不能为空！");
        } else if (checkInput('#content')) {
            showNoticeBox(noticeBox, "反馈内容不能为空！");
        } else if (checkEmail("#email")) {
            showNoticeBox(noticeBox, "邮箱格式不正确！");
        } else {

            let dataObj = {
                feedbackContent: $('#content').val(),
                contactInfo: $('#email').val(),
                feedbackName: $('#name').val()
            };

            $.ajax({
                url: '/feedback/addFeedback',
                type: 'POST',
                data: JSON.stringify(dataObj),
                dataType: 'JSON',
                contentType: "application/json;charsetset=UTF-8",
                success: function (data) {
                    $('#content').val("");
                    $('#email').val("");
                    $('#name').val("");
                    showNoticeBox(noticeBox, data.msg);
                },
                error: function () {
                    showNoticeBox(noticeBox, '提交反馈失败，请确认网络是否正常！');
                }
            })
        }
    });
});

$('#content').limiter(500, $('.feedback-num'));