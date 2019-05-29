jQuery(document).ready(function () {
    var $_GET = (function () {
        var url = window.document.location.href.toString();
        var u = url.split("?");
        if (typeof (u[1]) == "string") {
            u = u[1].split("&");
            var get = {};
            for (var i in u) {
                var j = u[i].split("=");
                get[j[0]] = j[1];
            }
            return get;
        } else {
            return {};
        }
    })();
    var api_back = sessionStorage.getItem('api_back');
    var server_course = sessionStorage.getItem('server_course');
    var url = api_back + '/course/cms/list';
    var lk = 0;
    var creater = '<option value="">--更新人--</option>';
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);







    var page = parseInt($_GET['page']);
    var per_page = '30';
    if (page == '' || page == undefined || isNaN(page)) {
        page = 1;
    } else {}



    $('.articleHealth_search_submit').click(function () {
        var formData = new FormData($("#temp")[0]);
        $.ajax({
            url: 'http://192.168.1.200:8081/articleType/cms/add',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function () {
                console.log('1');
            },
            success: function (data) {
                alert('提交成功');
                console.log('2');
                console.log(data);
                // var recode = data.code;
            },
            error: function (data) {
                console.log('3');
                console.log(data);
            }
        }, "json");
    });








    $('.articleHealth_list').on('click', '.articleHealth_edit', function () {
        var id = $(this).attr('data-id');
        window.location.href = "./articleHealthAdd.html?action=edit&id=" + id;
    });



});