/**
 * 分页
 * @param current
 */
function pagination(current) {
    $.ajax({
        type: 'GET',
        url: '/test/page',
        dataType: 'json',
        data: {
            rows: 3,
            pageNum: current
        },
        success: function (data) {
            scrollTo(0, 0);

            $("#pagination").paging({
                rows: data.data.rows,
                pageNum: data.data.pageNum,
                pages: data.data.pages,
                total: data.data.total,
                flag: 1,
                callback: function (current) {
                    pagination(current);
                }
            });
        },
        error: function () {
            alert("获得文章信息失败！");
        }
    });
}

// pagination(1);

function getTest() {
    console.log(4);
    $.ajax({
        url: '/test/getTest',
        type: 'GET',
        // data: $.param({
        //     id: 1,
        //     name: 'test'
        // }),
        data: {
            id: null,
            name: null
        },
        // contentType:"application/json",
        dataType: 'JSON',
        success: function (data) {
            console.log(data)
        },
        error: function () {
            console.log('error')
        }
    })
}

function repeatSubmit() {
    $.ajax({
        url: '/test/repeatSubmit',
        type: 'POST',
        success: function (data) {
            console.log(data);
        },
        error: function () {
            console.log('error');
        }
    })
}

// getTest();

