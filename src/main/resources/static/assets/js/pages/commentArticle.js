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
    var url = api_back + '/articleComment/cms/list';
    var lk = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var page = parseInt($_GET['page']);
    var per_page = '30';
    if (page == '' || page == undefined || isNaN(page)) {
        page = 1;
    } else {}



    var arr_articleComment = {
        page: page - 1,
        size: per_page,
        key_words: '',
        phone_number: '',
        user_name: '',
        sort_status: 'desc',
        course_type_id: ''
    }
    // $.post(url, arr_articleComment, function (data) {

    // });






    // $('.articleHealth_list').on('click', '.articleHealth_edit', function () {
    //     var id = $(this).attr('data-id');
    //     window.location.href = "./articleHealthAdd.html?action=edit&id=" + id;
    // });



});