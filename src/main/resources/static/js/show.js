// 增加访问量
getVisitorNum();

/**
 * 启动editormd工具将md格式转成html
 */
$(function () {
    let wordsView;
    wordsView = editormd.markdownToHTML("wordsView", {
        htmlDecode: "true", // you can filter tags decode
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
    });
});

/**
 * 获取文章访问量
 */
function getVisitorCount() {
    let articleId = $('.article-header').attr("id").substring(1);
    $.ajax({
        url: "/visitor/visitorCountByArticleId",
        type: "GET",
        data: {
            articleId: articleId
        },
        // async: false,
        dataType: "JSON",
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                $('.visitor-count').html(data.data);
            }
        }
    })
}

getVisitorCount();

