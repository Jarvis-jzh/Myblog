let articles = $('.articles');

// 增加访问量
getVisitorNum();

function putInMyArticle(pageNum) {
    let dataObj = {
        pageNum: pageNum,
        rows: 5
    };

    let noticeBox = $('.notice-box');

    $.ajax({
        type: 'GET',
        url: '/articles/myArticles',
        data: dataObj,
        dataType: 'JSON',
        // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                scrollTo(0, 0);
                data = data.data;
                articles.empty();
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
                        '<a href="/archive/' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '">' + $.dateFormat(obj.createTime, "YYYY-mm-dd") + '</a>'+
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
                            '<a class="article-tag" href="/tag/'+obj+'">'+obj+'</a>' +
                            '</i>');
                        tags.append(tag);
                    });

                    let main = $('<div class="article-main">' + obj.articleTabloid + '</div>');
                    // let other = $('<div class="article-other"></div>');
                    header.append(type);
                    // other.append();
                    article.append(header).append(main).append(tags);
                    articles.append(article);
                });
                $("#pagination").paging({
                    rows: data.pageSize,
                    pageNum: data.pageNum,
                    pages: data.pages,
                    total: data.total,
                    callback: function (current) {
                        putInMyArticle(current);
                    }
                });
                $('.pagination').addClass('page');
            } else {
                showNoticeBox(noticeBox, "网络异常，请稍后重试！");
            }
        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，请稍后重试！");
        }
    });
}

putInMyArticle(1);