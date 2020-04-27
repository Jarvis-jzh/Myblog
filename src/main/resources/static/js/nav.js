/**
 * 图标弹窗
 */
$(function () {
    $('[data-toggle="icontip"]').tooltip()
});


/**
 * 获取最新文章列并填充
 */
var recentPosts = $('#recent-posts>ul');
var noticeBox = $('.notice-box');
$.ajax({
    type: "GET",
    url: '/articles/recentPosts',
    data: "",
    dataType: 'JSON',
    contentType: "application/json;charsetset=UTF-8",
    success: function (data) {
        // console.log(data);
        if (data.success) {
            recentPosts.empty();
            $("#recent-posts").prev().hide();
            $.each(data.data, function (ind, obj) {
                let posts = $('<li>' +
                    '<a href="' + obj.articleUrl + '">' +
                    '<span class="card-omit-time">' + obj.articleTitle + '</span>' +
                    '<span class="card-right">' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '</span>' +
                    '</a>' +
                    '</li>');
                recentPosts.append(posts);
            });
        } else {
            showNoticeBox(noticeBox, "网络异常，请稍后重试");
        }
    },
    error: function () {
        showNoticeBox(noticeBox, "网络异常，请稍后重试");
    }
});

/**
 * 获取分类和文章数
 */
var recentCate = $('#recent-cate>ul');
$.ajax({
    url: "/categories/categoryNameAndArticleNum",
    type: "GET",
    data: "",
    dataType: 'JSON',
    contentType: "application/json;charsetset=UTF-8",
    success: function (data) {
        if (data.success) {
            data = data.data;
            recentCate.empty();
            $('#recent-cate').prev().hide();
            $.each(data, function (ind, obj) {
                let cate = $('<li>' +
                    '<a href="/category/' + obj.categoryName + '">' +
                    '<span class="card-omit-num">' + obj.categoryName + '</span>' +
                    '<span class="card-right">' + obj.articleNum + '篇</span>' +
                    '</a>' +
                    '</li>');
                recentCate.append(cate);
            });
            cateShow();
        } else {
            showNoticeBox(noticeBox, "网络异常，请稍后重试");
        }
    },
    error: function () {
        showNoticeBox(noticeBox, "网络异常，请稍后重试");
    }
});

/**
 * 分类只显示前5，其余隐藏，单击显示全部
 */
function cateShow() {
    let cateLis = recentCate.children('li');
    if (cateLis.length > 5) {
        cateLis.filter('li:gt(4)').hide();
        let showMore = $('<div class = "card-show-more">' +
            '<a>' +
            '<span>展开</span>' +
            '</a>' +
            '</div>');
        $('#recent-cate').append(showMore);
        showMore.on('click', 'a', function () {
            cateLis.filter('li:gt(4)').show('fast');
            $(this).hide();
        })
    }
}

/**
 * 标签云
 */
$.ajax({
    type: "GET",
    url: "/tag/tags",
    data: "",
    dataType: 'JSON',
    contentType: "application/json;charsetset=UTF-8",
    success: function (data) {
        if (data.success) {
            $("#tags").next().hide();
            $('#my-canvas-container').show();
            data = data.data;
            let tags = $('#tags>ul');
            $.each(data, function (ind, obj) {
                tags.append('<li><a href="/tag/' + obj + '">' + obj + '</a></li>');
            });
            tagcanvas();
        }
    },
    error: function () {
        showNoticeBox(noticeBox, "网络异常，请稍后重试");
    }
});

function tagcanvas() {
    if(!$('#tag-canvas').tagcanvas({
        initial: [0.8, -0.3],
        textColour: '#000000',
        outlineColour: '#cccccc',
        decel: 0.5,
        depth: 0.8,
        textFont: null,
        maxSpeed: 0.07,
        minSpeed: 0.01,
        wheelZoom: false,
        freezeDecel: true,
        outlineMethod: 'size',
        shuffleTags: true
    },'tags')) {
        $('#my-canvas-container').hide();
    }
}

/**
 * 获取文章、分类、标签数
 */
$.ajax({
    url: '/indexNums',
    type: 'GET',
    data: "",
    dataType: 'JSON',
    contentType: "application/json;charsetset=UTF-8",
    success: function (data) {
        if (data.success) {
            data = data.data;
            $('#article-count').html(data.articleCount).numScroll();
            $('#category-count').html(data.categoryCount).numScroll();
            $('#tag-count').html(data.tagCount).numScroll();
        }
    },
    error: function () {
        showNoticeBox(noticeBox, "网络异常，请稍后重试");
    }
});