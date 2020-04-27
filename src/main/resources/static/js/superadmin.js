$('.navbar').removeClass("bottom-shadow");
let noticeBox = $('.notice-box');
let deleteArticleId = "";

$(function () {
    // 仪表盘
    $('#dash-board').click(function () {
        $('[toggle=dash-board]').siblings('[toggle]').hide();
        $('[toggle=dash-board]').show('fast');
        getDiscussNum();
    });

    // 账号信息
    $('#account').click(function () {
        $('[toggle=account]').siblings('[toggle]').hide();
        $('[toggle=account]').show('fast');
        getAccountManagement();
        dateformatTool('#birthday');
    });

    // 修改用户信息
    $('.modify-user-info').click(function () {
        modifyUserInfo();
    });


    // 文章管理
    $('#article-manage').click(function () {
        $('[toggle=article-manage]').siblings('[toggle]').hide();
        $('[toggle=article-manage]').show('fast');
        getArticleManagement(1);
    });

    // 分类管理
    $('#category-manage').click(function () {
        $('[toggle=category-manage]').siblings('[toggle]').hide();
        $('[toggle=category-manage]').show('fast');
        getCategoryManagement();
    });

    // 友链管理
    $('#friend-link-manage').click(function () {
        $('[toggle=friend-link-manage]').siblings('[toggle]').hide();
        $('[toggle=friend-link-manage]').show('fast');
        getFriendLinkManagement(1);
    });

    // 反馈管理
    $('#feedback').click(function () {
        $('[toggle=feedback]').siblings('[toggle]').hide();
        $('[toggle=feedback]').show('fast');
        getFeedbackManagement(1);
    });

    $('#dash-board').trigger("click");
});

/**
 * 获取总访问量、今日访问、文章数
 */
function getDiscussNum() {
    $.ajax({
        url: '/visitor/discussNum',
        type: 'GET',
        data: "",
        dataType: 'JSON',
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                data = data.data;
                $('#total-visitor').html(data.totalVisitor).numScroll();
                $('#now-visitor').html(data.nowVisitor).numScroll();
                $('#article-count').html(data.articleCount).numScroll();
            }
        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，请稍后重试");
        }
    });
}

/**
 * 账号管理
 */
function getAccountManagement() {
    $.ajax({
        url: '/super/user',
        type: 'GET',
        data: {},
        dataType: 'JSON',
        success: function (data) {
            if (data.success) {
                data = data.data;
                $('#avatar-img-url').attr('src', data.avatarImgUrl);
                $('#nickname').val(data.nickname);
                let phone = data.username;
                $('#phone').html(phone.substring(0, 3) + '****' + phone.substring(7));
                let gender = data.gender;
                if (1 === gender) {
                    $('#male').attr("checked", "checked");
                } else {
                    $('#female').attr("checked", "checked");
                }
                $('#email').val(data.email);
                $('#birthday').val(data.birthday);
            } else {
                showNoticeBox(noticeBox, data.msg);
            }
        },
        error: function () {
            showNoticeBox(noticeBox, '网络异常，请稍后重试');
        }
    });
}

function imgChange(e) {
    if (e.target.files[0].size > 102400) {
        showNoticeBox(noticeBox, '图片大小不可超过100KB哦');
        return;
    }
    let reader = new FileReader();
    reader.onload = (function (file) {
        return function (e) {
            showNoticeBox(noticeBox, '头像正在上传中');
            $.ajax({
                url: '/super/uploadHead',
                type: 'POST',
                data: {
                    img: this.result
                },
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        let url = data.data;
                        $('#avatar-img-url').attr('src', url);
                        showNoticeBox(noticeBox, '头像上传成功');
                    } else {
                        showNoticeBox(noticeBox, '头像上传失败');
                    }
                },
                error: function () {
                    showNoticeBox(noticeBox, '网络异常，请稍后重试');
                }
            })
        }
    })(e.target.files[0]);
    reader.readAsDataURL(e.target.files[0]);
}

/**
 * 修改用户信息
 */
function modifyUserInfo() {
    let nickname = $('#nickname').val().trim();
    let gender = $('input[name=gender]');
    let genderVal = '';
    if (gender[0].checked) {
        genderVal = '1';
    } else {
        genderVal = '0';
    }
    let email = $('#email').val().trim();
    let birthday = $('#birthday').val().trim();
    if (!nickname) {
        showNoticeBox(noticeBox, '昵称不能为空');
        return false;
    } else {
        let dataObj = {
            nickname: nickname,
            gender: genderVal,
            email: email,
            birthday: birthday
        };
        $.ajax({
            url: '/super/updateUserInfo',
            type: 'POST',
            data: dataObj,
            dataType: 'JSON',
            // contentType: "application/json;charsetset=UTF-8",
            success: function (data) {
                if (data.success) {
                    showNoticeBox(noticeBox, '修改成功');
                } else {
                    showNoticeBox(noticeBox, '修改失败，请稍后重试');
                }
            },
            error: function() {
                showNoticeBox(noticeBox, '网络异常，请稍后重试');
            }
        });
    }

}

function dateformatTool(selector) {
    let inputs = $(selector);
    let dateStr = "____-__-__";
    inputs.each(function (index, elem) {
        let input = $(this);
        input.on("keydown", function (event) {
            let that = this;   //当前触发事件的输入框。
            let key = event.keyCode;
            let cursorIndex = getCursor(that);

            //输入数字
            if (key >= 48 && key <= 57) {
                //光标在日期末尾或光标的下一个字符是"-",返回false,阻止字符显示。
                if (cursorIndex == dateStr.length || that.value.charAt(cursorIndex) === "-") {
                    return false;
                }
                //字符串中无下划线时，返回false
                if (that.value.search(/_/) === -1) {
                    return false;
                }

                let fron = that.value.substring(0, cursorIndex); //光标之前的文本
                let reg = /(\d)_/;
                setTimeout(function () { //setTimeout后字符已经输入到文本中
                    //光标之后的文本
                    let end = that.value.substring(cursorIndex, that.value.length);
                    //去掉新插入数字后面的下划线_
                    that.value = fron + end.replace(reg, "$1");
                    //寻找合适的位置插入光标。
                    resetCursor(that);
                }, 1);
                return true;
                //"Backspace" 删除键
            } else if (key == 8) {

                //光标在最前面时不能删除。  但是考虑全部文本被选中下的删除情况
                if (!cursorIndex && !getSelection(that).length) {
                    return false;
                }
                //删除时遇到中划线的处理
                if (that.value.charAt(cursorIndex - 1) == "-") {
                    let ar = that.value.split("");
                    ar.splice(cursorIndex - 2, 1, "_");
                    that.value = ar.join("");
                    resetCursor(that);
                    return false;
                }
                setTimeout(function () {
                    //值为空时重置
                    if (that.value === "") {
                        that.value = "____-__-__";
                        resetCursor(that);
                    }
                    //删除的位置加上下划线
                    let cursor = getCursor(that);
                    let ar = that.value.split("");
                    ar.splice(cursor, 0, "_");
                    that.value = ar.join("");
                    resetCursor(that);
                }, 1);

                return true;
            }
            return false;

        });
        input.on("focus", function () {
            if (!this.value) {
                this.value = "____-__-__";
            }
            resetCursor(this);
        });
        input.on("blur", function () {
            if (this.value === "____-__-__") {
                this.value = "";
            }
        });
    });

    //设置光标到正确的位置
    function resetCursor(elem) {
        let value = elem.value;
        let index = value.length;
        //当用户通过选中部分文字并删除时，此时只能将内容置为初始格式洛。

        if (elem.value.length !== dateStr.length) {
            elem.value = dateStr;
        }
        let temp = value.search(/_/);
        index = temp > -1 ? temp : index;
        setCursor(elem, index);
        //把光标放到第一个_下划线的前面
        //没找到下划线就放到末尾
    }

    function getCursor(elem) {
        //IE 9 ，10，其他浏览器
        if (elem.selectionStart !== undefined) {
            return elem.selectionStart;
        } else { //IE 6,7,8
            let range = document.selection.createRange();
            range.moveStart("character", -elem.value.length);
            let len = range.text.length;
            return len;
        }
    }

    function setCursor(elem, index) {
        //IE 9 ，10，其他浏览器
        if (elem.selectionStart !== undefined) {
            elem.selectionStart = index;
            elem.selectionEnd = index;
        } else {//IE 6,7,8
            let range = elem.createTextRange();
            range.moveStart("character", -elem.value.length); //左边界移动到起点
            range.move("character", index); //光标放到index位置
            range.select();
        }
    }

    function getSelection(elem) {
        //IE 9 ，10，其他浏览器
        if (elem.selectionStart !== undefined) {
            return elem.value.substring(elem.selectionStart, elem.selectionEnd);
        } else { //IE 6,7,8
            let range = document.selection.createRange();
            return range.text;
        }
    }

    function setSelection(elem, leftIndex, rightIndex) {
        if (elem.selectionStart !== undefined) { //IE 9 ，10，其他浏览器
            elem.selectionStart = leftIndex;
            elem.selectionEnd = rightIndex;
        } else {//IE 6,7,8
            let range = elem.createTextRange();
            range.move("character", -elem.value.length);  //光标移到0位置。
            //这里一定是先moveEnd再moveStart
            //因为如果设置了左边界大于了右边界，那么浏览器会自动让右边界等于左边界。
            range.moveEnd("character", rightIndex);
            range.moveStart("character", leftIndex);
            range.select();
        }
    }
}

/**
 * 文章管理
 *
 * @param pageNum
 */
let articleTableResponsive = $('.article-manage-content .table-responsive');

function getArticleManagement(pageNum) {
    let dataObj = {
        pageNum: pageNum,
        rows: 5
    };

    $.ajax({
        type: 'GET',
        url: '/super/articleManagement',
        data: dataObj,
        dataType: 'JSON',
        // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                scrollTo(0, 0);
                data = data.data;
                articleTableResponsive.empty();
                let table = $('<table class="table table-bordered table-hover">' +
                    '<thead>' +
                    '<tr>' +
                    '<th>文章标题</th><th>发布时间</th><th>文章分类</th><th>浏览量</th><th>操作</th>' +
                    '</tr>' +
                    '</thead>' +
                    '</table>');
                let tables = $('<tbody class="table table-hover"></tbody>');
                $.each(data.list, function (ind, obj) {
                    tables.append('<tr id="a' + obj.id + '">' +
                        '<td><a href="/article/' + obj.articleId + '">' + obj.articleTitle + '</a></td>' +
                        '<td>' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '</td>' +
                        '<td>' + obj.articleCategories + '</td>' +
                        '<td>' + obj.visitorNum + '</td>' +
                        '<td><div class="article-button">' +
                        '<span class="">' +
                        '<a href="#" class="btn btn-primary article-management-btn" style="margin-right: 10px">修改</a>' +
                        '</span>' +
                        '<span class="">' +
                        '<a href="#" class="btn btn-danger article-delete-btn">删除</a>' +
                        '</span>' +
                        '</div></td>' +
                        '</tr>');
                });
                table.append(tables);
                articleTableResponsive.append(table);
                $(".article-manage-content .pagination-article").paging({
                    rows: data.pageSize,
                    pageNum: data.pageNum,
                    pages: data.pages,
                    total: data.total,
                    callback: function (current) {
                        getArticleManagement(current);
                    }
                });
                $('.article-manage-content .pagination').addClass('page');

                $('.article-management-btn').click(function () {
                    let id = $(this).parent().parent().parent().parent().attr("id").substring(1);
                    window.location.replace("/editor?id=" + id);
                });
                $('.article-delete-btn').click(function () {
                    deleteArticleId = $(this).parent().parent().parent().parent().attr("id").substring(1);
                    $('#sub-article').modal();
                })
            } else {
                showNoticeBox(noticeBox, "网络异常，请稍后重试！");
            }
        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，请稍后重试！");
        }
    });
}

//删除文章
$('.sure-delete-article-btn').click(function () {
    $.ajax({
        type: 'POST',
        url: '/super/deleteArticle',
        data: {
            "id": deleteArticleId
        },
        dataType: 'json',
        success: function (data) {
            $('#sub-article').modal('hide');
            if (data.success) {
                showNoticeBox(noticeBox, "删除成功");
            } else {
                showNoticeBox(noticeBox, data.msg);
            }
            getArticleManagement(1);
        },
        error: function () {
            $('#sub-article').modal('hide');
            showNoticeBox(noticeBox, "删除文章失败");
        }
    });
});

/**
 * 分类管理
 */
function getCategoryManagement() {
    $.ajax({
        url: "/super/categories",
        type: "GET",
        data: {},
        dataType: 'JSON',
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                data = data.data;

                let categoryContent = $('.category-content');
                categoryContent.empty();
                categoryContent.append($('<div class="content-top">' +
                    '目前分类数：<span class="category-num">' + data.length + '</span>' +
                    '<div class="update-category">' +
                    '<a class="add-category"><i class="iconfont icon-tianjia"></i> 添加分类</a> / ' +
                    '<a class="sub-category"><i class="iconfont icon-shanchu"></i> 删除分类</a>' +
                    '</div>'));

                let categories = $('<div class="categories"></div>');
                $.each(data, function (ind, obj) {
                    categories.append($('<span id="p' + obj.id + '" class="category">' + obj.categoryName + '</span>'));
                });

                categoryContent.append(categories);

                $('.add-category').click(function () {
                    $('#add-category').modal();
                    setTimeout("$('#add-category .input-category').focus()", 500);

                    $('.sure-add-category-btn').click(function () {
                        let val = $('#add-category .input-category').val();
                        if (null == val || '' === val) {
                            $('#add-category').modal('hide');
                            showNoticeBox(noticeBox, "分类名不能为空！");
                        } else {
                            updateCategory(val, 1);
                        }
                    });
                });

                $('.sub-category').click(function () {
                    $('#sub-category').modal();
                    setTimeout("$('#sub-category .input-category').focus()", 500);

                    $('.sure-sub-category-btn').click(function () {
                        let val = $('#sub-category .input-category').val();
                        if (null == val || '' === val) {
                            $('#sub-category').modal('hide');

                            showNoticeBox(noticeBox, "分类名不能为空！");
                        } else {
                            updateCategory(val, 2);
                        }
                    });
                });
            }
        }
    })
}

/**
 * 更新分类
 *
 * @param categoryName  分类名
 * @param type          1、新增 2、删除
 */
function updateCategory(categoryName, type) {
    $.ajax({
        url: '/super/updateCategory',
        type: 'POST',
        data: {
            categoryName: categoryName,
            type: type
        },
        // async: false,
        dataType: "JSON",
        success: function (data) {
            $('#add-category').modal('hide');
            $('#sub-category').modal('hide');
            showNoticeBox(noticeBox, data.msg);
            getCategoryManagement();
            $('#add-category .input-category').val('');
            $('#sub-category .input-category').val('');
        },
        error: function () {
            showNoticeBox(noticeBox, '操作失败，请检查网络是否正常！');
        }
    })
}

/**
 * 友链管理
 * @param pageNum
 */
let friendTableResponsive = $('.friend-link-content .table-responsive');

function getFriendLinkManagement(pageNum) {
    let dataObj = {
        pageNum: pageNum,
        rows: 10
    };

    $.ajax({
        url: '/super/friendLink',
        type: 'GET',
        data: dataObj,
        dataType: 'JSON',
        // // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                scrollTo(0, 0);
                data = data.data;
                friendTableResponsive.empty();
                friendTableResponsive.append($('<div class="content-top">' +
                    '<span class="">&nbsp;</span>' +
                    '<div class="update-category">' +
                    '<a class="add-friend-link"><i class="iconfont icon-tianjia"></i> 添加友链</a> ' +
                    '</div></div>'));

                let table = $('<table class="table table-bordered table-hover">' +
                    '<thead>' +
                    '<tr>' +
                    '<th>头像</th><th>博主</th><th>博客地址</th><th>简介</th><th>操作</th>' +
                    '</tr>' +
                    '</thead>' +
                    '</table>');
                let tables = $('<tbody class="table table-hover"></tbody>');
                $.each(data.list, function (ind, obj) {
                    tables.append('<tr id="f' + obj.id + '">' +
                        '<td class="blogger-head-img"><img class="head-img" src="' + obj.headImg + '"/></td>' +
                        '<td class="blogger">' + obj.blogger + '</td>' +
                        '<td class="blogger-url">' + obj.url + '</td>' +
                        '<td class="blogger-introduction">' + obj.introduction + '</td>' +
                        '<td><div>' +
                        '<span class="">' +
                        '<a href="#" class="btn btn-primary friend-link-management-btn" style="margin-right: 10px">修改</a>' +
                        '</span>' +
                        '<span class="">' +
                        '<a href="#" class="btn btn-danger friend-link-delete-btn">删除</a>' +
                        '</span>' +
                        '</div></td>' +
                        '</tr>');
                });
                table.append(tables);
                friendTableResponsive.append(table);
                $(".friend-link-content .pagination-article").paging({
                    rows: data.pageSize,
                    pageNum: data.pageNum,
                    pages: data.pages,
                    total: data.total,
                    callback: function (current) {
                        getFriendLinkManagement(current);
                    }
                });
                $('.friend-link-content .pagination').addClass('page');

                let friendLinkId;

                // 新增友链
                $('.add-friend-link').click(function () {
                    $('#add-friend-link').modal();
                    $('.sure-add-friend-link-btn').click(function () {
                        let bloggerVal = $('#add-friend-link .blogger').val();
                        let headImg = $('#add-friend-link .head-img-url').val();
                        let bloggerUrl = $('#add-friend-link .blogger-url').val();
                        let introduction = $('#add-friend-link .blogger-introduction').val();
                        if (bloggerVal === null || bloggerVal === "") {
                            $('#add-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '博主不能为空!');
                        } else if (headImg === null || headImg === '') {
                            $('#add-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '头像地址不能为空!');
                        } else if (bloggerUrl === null || bloggerUrl === '') {
                            $('#add-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '博客地址不能为空!');
                        } else {
                            updateFriendLink(null, bloggerVal, headImg, bloggerUrl, introduction, 1);
                        }
                    });
                });

                // 修改友链
                $('.friend-link-management-btn').click(function () {
                    let tr = $(this).parent().parent().parent().parent();
                    friendLinkId = tr.attr("id").substring(1);
                    $('#update-friend-link .blogger').val(tr.children('.blogger').html());
                    $('#update-friend-link .head-img-url').val(tr.children('.blogger-head-img').children().attr('src'));
                    $('#update-friend-link .blogger-url').val(tr.children('.blogger-url').html());
                    $('#update-friend-link .blogger-introduction').val(tr.children('.blogger-introduction').html());

                    $('#update-friend-link').modal();
                    $('.sure-update-friend-link-btn').click(function () {
                        let bloggerVal = $('#update-friend-link .blogger').val();
                        let headImg = $('#update-friend-link .head-img-url').val();
                        let bloggerUrl = $('#update-friend-link .blogger-url').val();
                        let introduction = $('#update-friend-link .blogger-introduction').val();
                        if (bloggerVal === null || bloggerVal === "") {
                            $('#update-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '博主不能为空!');
                        } else if (headImg === null || headImg === '') {
                            $('#update-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '头像地址不能为空!');
                        } else if (bloggerUrl === null || bloggerUrl === '') {
                            $('#update-friend-link').modal('hide');
                            showNoticeBox(noticeBox, '博客地址不能为空!');
                        } else {
                            updateFriendLink(friendLinkId, bloggerVal, headImg, bloggerUrl, introduction, 3);
                        }
                    });
                });

                // 删除友链
                $('.friend-link-delete-btn').click(function () {
                    friendLinkId = $(this).parent().parent().parent().parent().attr("id").substring(1);
                    $('#sub-friend-link').modal();
                    $('.sure-sub-friend-link-btn').click(function () {
                        updateFriendLink(friendLinkId, null, null, null, null, 2)
                    });
                })
            }
        }
    })
}

/**
 * 更新友链
 *
 * @param friendLinkId
 * @param blogger
 * @param url
 * @param type              1、新增    2、删除    3、修改
 */
function updateFriendLink(friendLinkId, blogger, headImg, url, introduction, type) {
    let dataObj = {
        id: friendLinkId,
        blogger: blogger,
        headImg: headImg,
        url: url,
        introduction: "" === introduction ? null : introduction,
        type: type
    };

    $.ajax({
        url: '/super/updateFriendLink',
        type: 'POST',
        data: JSON.stringify(dataObj),
        dataType: 'JSON',
        // // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            $('#add-friend-link').modal('hide');
            $('#update-friend-link').modal('hide');
            $('#sub-friend-link').modal('hide');
            showNoticeBox(noticeBox, data.msg);
            $('.blogger').val("");
            $('.blogger-url').val("");
            getFriendLinkManagement(1);
        },
        error: function () {
            showNoticeBox(noticeBox, '操作失败，请检查网络是否正常！');
        }
    })
}

let feedbackTableResponsive = $('.feedback-content .table-responsive');

/**
 * 反馈管理
 * @param pageNum
 */
function getFeedbackManagement(pageNum) {
    let dataObj = {
        pageNum: pageNum,
        rows: 10
    };

    $.ajax({
        url: '/super/feedbackManagement',
        type: 'GET',
        data: dataObj,
        dataType: 'JSON',
        // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                data = data.data;
                feedbackTableResponsive.empty();
                let table = $('<table class="table table-bordered table-hover">' +
                    '<thead>' +
                    '<tr>' +
                    '<th>反馈人</th><th>邮箱</th><th>内容</th><th>操作</th>' +
                    '</tr>' +
                    '</thead>' +
                    '</table>');
                let tables = $('<tbody class="table table-hover"></tbody>');
                $.each(data.list, function (ind, obj) {
                    tables.append('<tr id="f' + obj.id + '">' +
                        '<td>' + obj.feedbackName + '</td>' +
                        '<td>' + obj.contactInfo + '</td>' +
                        '<td class="overflow-omit">' + obj.feedbackContent + '</td>' +
                        '<td>' +
                        '<div>' +
                        '<span class="">' +
                        '<a href="#" class="btn btn-primary feedback-management-btn" style="margin-right: 10px">查看</a>' +
                        '</span>' +
                        // '<span class="">' +
                        // '<a href="#" class="btn btn-danger feedback-delete-btn">删除</a>' +
                        // '</span>' +
                        '</div>' +
                        '</td>' +
                        '</tr>');
                });
                table.append(tables);
                feedbackTableResponsive.append(table);

                $(".feedback-content .pagination-article").paging({
                    rows: data.pageSize,
                    pageNum: data.pageNum,
                    pages: data.pages,
                    total: data.total,
                    callback: function (current) {
                        getFeedbackManagement(current);
                    }
                });
                $('.feedback-content .pagination').addClass('page');

                $('.feedback-management-btn').click(function () {
                    let feedbackContent = $(this).parent().parent().parent().prev();
                    let contactInfo = feedbackContent.prev();
                    let feedbackName = contactInfo.prev();
                    let feedbackContentText = feedbackContent.text();
                    let contactInfoText = contactInfo.text();
                    let feedbackNameText = feedbackName.text();
                    $('#view-feedback .modal-body').empty().append('<div>' +
                        feedbackNameText + '（' + contactInfoText + '）说：' +
                        '</div>' +
                        '<div class="feedback-content">' + feedbackContentText + '</div>' +
                        '<input type="hidden" value="' + contactInfoText + '">');
                    $('#view-feedback').modal();
                });

                $('.reply-feedback-btn').click(function () {
                    // $('#view-feedback').modal('hide');
                    let contactInfoText = $('#view-feedback input:hidden').val();
                    $('.feedback-to-mail').val(contactInfoText);
                    $('.feedback-subject').val('夏末之家');
                    $('#reply-feedback').modal();
                });

                $('.send-feedback-btn').click(function () {
                    let feedbackNoticeBox = $('#reply-feedback>.notice-box');
                    let toMail = $('.feedback-to-mail').val().trim();
                    let subject = $('.feedback-subject').val().trim();
                    let content = $('.reply-feedback-content').val().trim();
                    if (!toMail || toMail == "") {
                        showNoticeBox(feedbackNoticeBox, "收件人邮箱不能为空！");
                    } else if (!subject || subject == "") {
                        showNoticeBox(feedbackNoticeBox, "主题不能为空！");
                    } else if (!content || content == "") {
                        showNoticeBox(feedbackNoticeBox, "内容不能为空！")
                    } else {
                        let obj = {
                            toMail: toMail,
                            subject: subject,
                            context: content
                        };
                        $.ajax({
                            url: "/super/replyFeedback",
                            type: 'POST',
                            data: JSON.stringify(obj),
                            dataType: 'JSON',
                            contentType: "application/json;charsetset=UTF-8",
                            success: function (data) {
                                if (data.success) {
                                    $('.feedback-to-mail').val("");
                                    $('.feedback-subject').val("");
                                    $('.reply-feedback-content').val("");
                                    $('#view-feedback').modal('hide');
                                    $('#reply-feedback').modal('hide');
                                    showNoticeBox(noticeBox, "发送成功");
                                } else {
                                    showNoticeBox(feedbackNoticeBox, "发送失败，请稍后重试");
                                }
                            },
                            error: function () {
                                showNoticeBox(feedbackNoticeBox, "网络异常，请稍后重试");
                            }
                        });
                    }
                });
            } else {
                showNoticeBox(noticeBox, data.msg);
            }
        },
        error: function () {
            showNoticeBox(noticeBox, '操作失败，请检查网络是否正常！');
        }
    })

}