// 记数
let i = 0;

let aCategory = "";
/**
 * 查看是否是修改文章
 */
$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url: window.location.href,
    // async: false,
    success: function (data, status, xhr) {
        let id = xhr.getResponseHeader("id");
        if (id) {
            getDraftArticle(id);
        }
    }
});

/**
 * 获取文章草稿
 * @param id
 */
function getDraftArticle(id) {
    $.ajax({
        type: "GET",
        url: "/articles/draftArticle",
        data: {
            id: id
        },
        // async: false,
        contentType: "application/json;charsetset=UTF-8",
        success: function (data) {
            if (data.success) {
                data = data.data;
                $('.editor-article-title').val(data.articleTitle);
                $('#my-editormd-markdown-doc').html(data.articleContent);
                $('#select-type').val(data.articleType);
                $('#select-categories').val(data.articleCategories);
                $('#original-author').val(data.originalAuthor);
                $('#article-url').val(data.articleUrl);

                if ("转载" === data.articleType) {
                    $('#editor-article-author').addClass("show");
                }

                aCategory = data.articleCategories;
                let tags = data.articleTags;
                $.each(tags, function (ind, obj) {
                    $('.editor-article-tag').append($('<div style="display: inline-block;"><p class="tag-name" contenteditable="true">' + obj + '</p>' +
                        '<i class="iconfont icon-jian remove-tag"></i></div>'));
                    i++;
                });
                let id = data.id;
                if (id != 0) {
                    $('.sure-publish-btn').attr("id", id);
                }
                $('.modal-footer').attr("id", data.articleId);
            } else {
                getDraftArticle(id);
            }
        },
        error: function () {
            showNoticeBox(noticeBox, '网络异常，请稍后重试！');
        }
    })
}


$('.editor-article-author').hide();

let testEditor;
$(function () {
    testEditor = editormd("my-editormd", { //注意1：这里的就是上面的DIV的id属性值
        width: "100%",
        height: 740,
        syncScrolling: true, //设置双向滚动
        path: "plugin/editormd/lib/", //lib目录的路径
        // previewTheme: "dark", //代码块使用dark主题
        codeFold: true,
        emoji: true,
        tocm: true, // Using [TOCM]
        tex: true, // 开启科学公式TeX语言支持，默认关闭
        flowChart: true, // 开启流程图支持，默认关闭
        sequenceDiagram: true, // 开启时序/序列图支持，默认关闭,
        htmlDecode: true, //不过滤标签
        imageUpload: true, //上传图片
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp", "JPG", "JPEG", "GIF", "PNG", "BMP", "WEBP"],
        imageUploadURL: "/file/uploadArticleImage",
        onload: function () {
            // console.log('onload', this);
        },
        saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
        toolbarIcons: function () {
            return ["bold", "del", "italic", "quote", "|", "h1", "h2", "h3", "h4", "h5", "h6", "|", "list-ul", "list-ol",
                "hr", "|", "link", "image", "code", "code-block", "table", "datetime", "html-entities", "emoji", "|", "watch",
                "preview", "fullscreen", "clear", "search", "|", "help", "info"
            ]
        }
    });
});

let publishBtn = $('.publish-btn');
let articleTitle = $('.editor-article-title');
let articleContent = $('#my-editormd-html-code');
let noticeBox = $('.notice-box');
publishBtn.click(function () {
    let articleTitleValues = articleTitle.val();
    let articleContentValues = articleContent.val();
    if (articleTitleValues.length === 0) {
        showNoticeBox(noticeBox, "文章标题不能为空");
    } else if (articleContentValues.length === 0) {
        showNoticeBox(noticeBox, "文章內容不能为空");
    } else {
        $('#my-alert').modal();
        $.ajax({
            type: "GET",
            url: "/categories/categoryNameAndArticleNum",
            // async: false,
            data: {},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    data = data.data;
                    let selectCategories = $('#select-categories');
                    selectCategories.empty();
                    selectCategories.append($('<option class="categoriesOption" value="choose">请选择</option>'));
                    for (let i = 0; i < data.length; i++) {
                        selectCategories.append($('<option class="categoriesOption" value="' + data[i]['categoryName'] + '">' + data[i]['categoryName'] + '</option>'));
                    }
                    if(null != aCategory && aCategory !== "" && aCategory.length > 0){
                        selectCategories.val(aCategory);
                    }
                } else {
                    showNoticeBox(noticeBox, '获取分类失败，请稍后重试！');
                }
                // if (aCategory !== "" && aCategory.length > 0) {
                //     selectCategories.val(aCategory);
                // }
            },
            error: function () {
                showNoticeBox(noticeBox, '网络异常，请稍后重试！');
            }
        });
    }
});

// 添加标签
let addTagsBtn = $(".add-tags-btn");
$(function () {
    let $wrapper = $('.editor-article-tag');
    let appendPanel = function () {
        let panel = $('<div style="display: inline-block;"><p class="tag-name" contenteditable="true"></p>' +
            '<i class="iconfont icon-jian remove-tag"></i></div>');
        $wrapper.append(panel);
        $('.tag-name').click(function () {
            $(this).focus();
        });
    };

    addTagsBtn.on('click', function () {
        if (i >= 4) {
            addTagsBtn.hide();
        }
        let value = $('.tag-name').eq(i - 1).html();
        if (value != "") {
            appendPanel(i++);
        }
    });

    $('.editor-article-tag').on('click', '.remove-tag', function () {
        $(this).parent().remove();
        i--;
        if (i < 4) {
            addTagsBtn.show();
        }
    })
});


// 获取文章类型并判断
let articleType = $('#select-type');
$('#select-type').blur(function () {
    if (articleType.val() == '原创') {
        $('.editor-article-author').hide();
    } else if (articleType.val() == '转载') {
        $('.editor-article-author').show();
    }
});

// 确认发布博客
let surePublishBtn = $('.sure-publish-btn');
let tags = $('.editor-article-tag');
let articleCategories = $('#select-categories');
let originalAuthor = $('#original-author');
let articleUrl = $('#article-url');

surePublishBtn.click(function () {
    let tagNum = tags.find('.tag-name').length;
    let articleTypeValue = articleType.val();
    let articleCategoriesValue = articleCategories.val();
    let originalAuthorValue = originalAuthor.val();
    let articleUrlValue = articleUrl.val();

    let articleTitleValues = articleTitle.val();
    let articleContentValues = articleContent.val();
    if (tagNum === 0 || $('.tag-name').eq(tagNum - 1).html() === "") {
        showNoticeBox(noticeBox, '文章标签不能为空');
    } else if (articleTypeValue === "choose") {
        showNoticeBox(noticeBox, '文章类型不能为空');
    } else if (articleCategoriesValue === 'choose') {
        showNoticeBox(noticeBox, '文章分类不能为空');
    } else if (articleTypeValue === '转载' && originalAuthorValue === '') {
        showNoticeBox(noticeBox, '文章作者不能为空');
    } else if (articleTypeValue === '转载' && articleUrlValue === '') {
        showNoticeBox(noticeBox, '文章链接不能为空');
    } else {
        let articleTagsValue = [];
        for (let j = 0; j < tagNum; j++) {
            articleTagsValue[j] = $('.tag-name').eq(j).html();
        }

        // console.log($('.sure-publish-btn').attr("id"));

        let obj = {
            id: $('.sure-publish-btn').attr("id"),
            articleId: $('.modal-footer').attr("id"),
            originalAuthor: originalAuthorValue === "" ? null : originalAuthorValue,
            articleTitle: articleTitleValues,
            articleContent: articleContentValues,
            articleTags: articleTagsValue.join(","),
            articleType: articleTypeValue,
            articleCategories: articleCategoriesValue,
            articleUrl: articleUrlValue === '' ? null : articleUrlValue,
            articleHtmlContent: testEditor.getHTML()
        };

        $.ajax({
            type: "POST",
            url: '/super/editor',
            traditional: true, //传数组
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                // console.log(data);
                if (data.success) {
                    fnClose('博客发布成功');
                    window.location = data.data;
                } else {
                    showNoticeBox(noticeBox, data.msg);
                }
            }
        });
    }
});

function fnClose(msg) {
    let fnClose = function (e) {
        if(null === msg || '' === msg){
            e.returnValue = '确定离开当前页面吗?';
        }
        e.returnValue = msg;
    };
    window.addEventListener('beforeunload', fnClose);
}

