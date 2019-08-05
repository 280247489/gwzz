jQuery(document).ready(function () {
    var api_back = 'http://39.97.232.184:18080/gwzz';
    var api_login = api_back + '/sysAdmin/cms/login';
    var api_admin = api_back + '/sysAdmin/cms/options';
    var api_type_course = api_back + '/courseType/cms/list';
    var api_type_article = api_back + '/articleType/cms/list';
    var server_course = 'http://39.97.232.184:18080/testFile/';
    var url_editer = api_back + '/jodit/uploadImg';
    var lk = 0;

    

    var user = sessionStorage.getItem('user');
    sessionStorage.setItem("api_back", api_back);
    sessionStorage.setItem("server_course", server_course);
    sessionStorage.setItem("url_editer", url_editer);
    if (user !== null) {
        window.location.href = "./index.html";
        return false;
    }
    $('#login').click(function () {
        id = $('#user_id').val();
        pwd = $('#user_psd').val();

        arr = {
            loginName: id,
            pwd: pwd
        }
        if (id == '' || pwd == '') {
            $('.login_callback').html('用户名密码不能为空！').fadeIn(200, function () {
                setTimeout(function () {
                    $('.login_callback').fadeOut(300, function () {
                        $('.login_callback').html('');
                    });
                }, 2019);
            });
        } else {
            if (lk > 0) {
                return false;
            } else {
                lk = 1;
                setTimeout(function () {
                    lk = 0;
                }, 3000);
                $.post(api_login, arr, function (data) {
                    console.log(data);
                    var recode = data.code;
                    var msg = data.msg;
                    if (recode == 1) {
                        $('.login_callback').html(msg).fadeIn(200, function () {
                            setTimeout(function () {
                                $('.login_callback').fadeOut(300, function () {
                                    $('.login_callback').html('');
                                });
                            }, 2019);
                        });
                    } else if (recode == 0) {
                        $.ajax({
                            url: api_admin,
                            type: 'POST',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            dataType: "json",
                            timeout: 50000,
                            beforeSend: function () {},
                            success: function (data) {
                                var this_code = data.code;
                                if (this_code == 0) {
                                    sessionStorage.setItem('user_list', JSON.stringify(data.data));
                                } else {
                                    sessionStorage.clear();
                                    return false;
                                }
                            },
                            error: function (data) {
                                console.log(data);
                            }
                        }, "json");
                        $.ajax({
                            url: api_type_course,
                            type: 'POST',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            dataType: "json",
                            timeout: 50000,
                            beforeSend: function () {},
                            success: function (data) {
                                var this_code = data.code;
                                if (this_code == 0) {
                                    sessionStorage.setItem('course_type', JSON.stringify(data.data));
                                } else {
                                    sessionStorage.clear();
                                    return false;
                                }
                            },
                            error: function (data) {
                                console.log(data);
                            }
                        }, "json");
                        $.ajax({
                            url: api_type_article,
                            type: 'POST',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            dataType: "json",
                            timeout: 50000,
                            beforeSend: function () {},
                            success: function (data) {
                                var this_code = data.code;
                                if (this_code == 0) {
                                    sessionStorage.setItem('article_type', JSON.stringify(data.data));
                                } else {
                                    sessionStorage.clear();
                                    return false;
                                }
                            },
                            error: function (data) {
                                console.log(data);
                            }
                        }, "json");
                        var user = data.data;
                        sessionStorage.setItem('user', JSON.stringify(user));
                        sessionStorage.setItem("user_name", data.data.name);
                        setTimeout(function () {
                            window.location.href = "./index.html";
                        }, 200);
                    } else {
                        $('.login_callback').html('请联系管理员！').fadeIn(200, function () {
                            setTimeout(function () {
                                $('.login_callback').fadeOut(300, function () {
                                    $('.login_callback').html('');
                                });
                            }, 2019);
                        });
                    }

                });
            }

        }
    });
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $("#login").trigger("click");
        }
    });
});