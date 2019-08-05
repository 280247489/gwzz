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
    var server_xiqing = 'http://192.168.1.185:8080/';

    var url_detail = api_back + '/feedback/cms/detail';
    var lk = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var oid = $_GET['oid'];

    if (oid == '' || oid == null || oid == undefined) {
        $modal({
            type: 'alert',
            icon: 'warning',
            timeout: 3000,
            title: '警告',
            content: '错误的ID',
            top: 300,
            center: true,
            transition: 300,
            closable: true,
            mask: true,
            pageScroll: true,
            width: 300,
            maskClose: true,
            callBack: function () {
                window.location.href = "./opinion.html";
            }
        });
        return false;
    }


    var arr_detail = {
        id: oid
    }
    $.ajax({
        url: url_detail,
        type: 'POST',
        data: arr_detail,
        async: true,
        cache: false,
        dataType: "json",
        timeout: 50000,
        beforeSend: function (data) {
            loading();
        },
        success: function (data) {
            to_page(data);
        },
        complete: function () {
            loading_end();
        },
        error: function (data) {
            _alert_warning(data.msg);
        }
    }, "json");

    function to_page(data) {
        console.log(data);
        if (data.code == '0') {
            $('.feedbackName').html(data.data.feedbackName);
            $('.feedbackContactUs').html(data.data.feedbackContactUs);
            $('.feedbackType').html(data.data.feedbackType);
            $('.feedbackCreateTime').html(data.data.feedbackCreateTime);
            $('.feedbackContent').html(data.data.feedbackContent);
            var _str = data.data.feedbackImg;
            var _img = _str.split(',');
            var _image = '';
            if (_img != '') {
                $.each(_img, function (ii, nn) {
                    _image += '<div class="col-md-4 col-lg-4 text-center"><img class="opinion_view_img img_prev" src="' + server_xiqing + nn + '"></div>';
                });
            }
            $('.opinion_img').html(_image);
        } else {
            _alert_warning(data.msg);
        }
    }


});