var noticeBox = $('.notice-box');

// 增加访问量
getVisitorNum();

/**
 * 填充友链内容
 */
function putInFriendLinkInfo() {
    $.ajax({
        url: '/friendlink/friendLink',
        type: 'GET',
        data: "",
        dataType: "JSON",
        // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                data = data.data;
                let cardCategory = $('.card-category>ul');
                $.each(data, function (ind, obj) {
                    let lis = $('<li>' +
                        '<a href="' + obj.url + '" target="_blank">' +
                        '<span><img class="head-img" src="' + obj.headImg + '"/>' + obj.blogger + '</span>' +
                        '<span class="card-right">'+obj.introduction+'</span>' +
                        '</a>' +
                        '</li>');
                    cardCategory.append(lis);
                });
            } else {
                showNoticeBox(noticeBox, "找不到友链呀，可能已经翻船了吧");
            }
        },
        error: function () {
            showNoticeBox(noticeBox, "找不到友链呀，可能已经翻船了吧");
        }
    });
}

putInFriendLinkInfo();

