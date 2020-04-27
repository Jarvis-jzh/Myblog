var noticeBox = $('.notice-box');
let archiveName = "";
$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url: window.location.href,
    // async: false,
    success: function (data, status, xhr) {
        archiveName = xhr.getResponseHeader("archive");
        if (archiveName) {
            archiveName = $.deUnicode(archiveName);
            let bc = $('<li class="active"><a href="/archive">归档</a></li>' +
                '<li class="active">' + archiveName + '</li>');
            $('.breadcrumb').append(bc);
            $('.card-category').hide();
            // 填充文章
            putInArticlesInfo(archiveName, 1);
        } else {
            let bc = $('<li class="active">归档</li>');
            $('.breadcrumb').append(bc);
            $('.show-article').hide();
            // 填充路径导航
            putInCardCategory();
        }
    }
});

// 增加访问量
getVisitorNum();

/**
 * 填充文章内容
 *
 * @param archiveName
 * @param pageNum
 */
function putInArticlesInfo(archiveName, pageNum) {
    let dataObj = {
        archiveName: archiveName,
        pageNum: pageNum,
        rows: 5
    };
    $.ajax({
        url: '/archive/articleByArchive',
        type: 'GET',
        data: dataObj,
        dataType: "JSON",
        // async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.success) {
                scrollTo(0, 0);
                let articles = $('.articles');
                articles.empty();
                data = data.data;
                if (data.list == 0) {
                    showNoticeBox(noticeBox, "找不到文章呀")
                }
                $.each(data.list, function (ind, obj) {
                    let article = $('<div class="article stereo-shadow"></div>');
                    let header = $('<header class="article-header">' +
                        '<h1 class="article-title">' +
                        '<a href="/article/' + obj.articleId + '">' + obj.articleTitle + '</a>' +
                        '</h1>' +
                        '</header>');
                    let type = $('<div class="article-type">' +
                        '<span class="label label-success">' + obj.articleType + '</span>' +
                        '<span class="article-time">' +
                        '<i class="iconfont icon-shijian">' +
                        '<a href="/archive/' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '">' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '</a>' +
                        '</i>' +
                        '</span>' +
                        '<i class="iconfont icon-ren">' +
                        '<span>' + obj.originalAuthor + '</span>' +
                        '</i>' +
                        '<i class="iconfont icon-ziyuan">' +
                        '<a href="/category/' + obj.articleCategories + '">' + obj.articleCategories + '</a>' +
                        '</i>' +
                        '</div>');
                    let tags = $('<div class="article-tags"></div>');
                    $.each(obj.articleTags.split(','), function (ind, obj) {
                        let tag = $('<i class="iconfont icon-tag">' +
                            '<a class="article-tag" href="/tag/' + obj + '">' + obj + '</a>' +
                            '</i>');
                        tags.append(tag);
                    });

                    let main = $('<div class="article-main">' + obj.articleTabloid + '</div>');
                    let other = $('<div class="article-other"></div>');
                    header.append(type);
                    other.append(tags);
                    article.append(header).append(main).append(other);
                    articles.append(article);
                });
                $("#pagination").paging({
                    rows: data.pageSize,
                    pageNum: data.pageNum,
                    pages: data.pages,
                    total: data.total,
                    callback: function (current) {
                        putInArticlesInfo(archiveName, current);
                    }
                });
                $('.pagination').addClass('page');
            }

        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，请稍后重试！")
        }
    });
}

/**
 * 填充分类路径导航
 */
function putInCardCategory() {
    $.ajax({
        url: '/archive/archives',
        type: 'GET',
        dataType: "JSON",
        contentType: "application/json",
        success: function (data) {
            if (data.success) {
                data = data.data;
                let cardCategory = $('.card-category>ul');
                $.each(data, function (ind, obj) {
                    let lis = $('<li>' +
                        '<a href="/archive/' + obj.archiveName + '">' + obj.archiveName + '(' + obj.archiveCount + ')</a>' +
                        '</li>');
                    cardCategory.append(lis);
                });
            } else {
                showNoticeBox(noticeBox, "网络异常，请稍后重试！");
            }

        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，请稍后重试！");
        }
    })
}
