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
    var url_add = api_back + '/album/cms/add';
    var url_update = api_back + '/album/cms/update';
    var lk = 0;
    var img_cache = '';
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    $('.operator_id').val(user.id);
    var action = $_GET['action'];
    var id = $_GET['id'];




    if (action == 'edit' && id !== '' && id !== undefined) {
        $('#albumAdd').attr('data-action', 'edit');
        $('.edit_id').val(id);
        var album_list = sessionStorage.getItem('album_list');
        album_list = JSON.parse(album_list);
        $.each(album_list, function (i, n) {
            if (n.id == id) {
                console.log(n);
                $('.album_name').val(n.albumName);
                $('.album_course_limit').val(n.albumCourseLimit);
                $('.album_logo_img img').attr('src', server_course + n.albumLogo);
                img_cache = server_course + n.albumLogo;
                $('#editor').html(n.albumSynopsis);
            }
        });
    } else {

    }


    $('.album_submit').click(function () {
        if ($('#albumAdd').attr('data-action') == 'edit') {
            _url = url_update;
        } else {
            _url = url_add;
        }
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            $('.album_submit').removeClass('btn-primary').addClass('btn-default');
            setTimeout(function () {
                lk = 0;
                $('.album_submit').removeClass('btn-default').addClass('btn-primary');
            }, 2000);
            var formData = new FormData($("#albumAdd")[0]);
            $.ajax({
                url: _url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                dataType: "json",
                timeout: 50000,
                beforeSend: function () {},
                success: function (data) {
                    console.log(data);
                    var content = data.data;
                    if (data.code == '0') {
                        $('#courseId').val(content.id);
                        $modal({
                            type: 'alert',
                            icon: 'success',
                            timeout: 3000,
                            title: '成功',
                            content: '提交成功！',
                            top: 300,
                            center: true,
                            transition: 300,
                            closable: true,
                            mask: true,
                            pageScroll: true,
                            width: 300,
                            maskClose: true,
                            callBack: function () {
                                window.location.href = "./album.html";
                            }
                        });
                    } else {
                        _alert_warning(data.msg);
                    }
                },
                error: function (data) {
                    _alert_warning(data.msg);
                }
            }, "json");

        }
    });



    $('.album_logo_img').click(function () {
        $('.logoFile').trigger('click');
    });
    $('.logoFile').change(function () {
        if ($(this)[0].files[0] !== undefined) {
            $(this).parent().parent().find(".album_logo_img img").attr("src", URL.createObjectURL($(this)[0].files[0]));
        } else {
            if (action == 'edit') {
                $(this).parent().parent().find(".album_logo_img img").attr("src", img_cache);
            } else {
                $(this).parent().parent().find(".album_logo_img img").attr("src", "dist/img/add.png");
            }
        }
    });

    function _alert_warning(msg) {
        $modal({
            type: 'alert',
            icon: 'warning',
            timeout: 3000,
            title: '警告',
            content: msg,
            top: 300,
            center: true,
            transition: 300,
            closable: true,
            mask: true,
            pageScroll: true,
            width: 300,
            maskClose: true,
            callBack: function () {}
        });
    }

});