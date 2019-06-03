jQuery(document).ready(function () {
    var web_shezhen = 'http://yaoyue.houaihome.com';
    var server_shezhen = 'http://image.houaihome.com';
    var api_back = 'http://192.168.1.200:8081';
    var api_login = api_back +'/sysAdmin/cms/login';
    var api_admin = api_back +'/sysAdmin/cms/options';
    var api_type_course = api_back +'/courseType/cms/list';
    var api_type_article = api_back +'/articleType/cms/list';
    var server_course = 'http://192.168.1.200:8091/file/';
    var lk = 0;


    var user = sessionStorage.getItem('user');
    sessionStorage.setItem("API_shezhen", web_shezhen);
    sessionStorage.setItem("server_shezhen", server_shezhen);
    sessionStorage.setItem("api_back", api_back);
    sessionStorage.setItem("server_course", server_course);
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
                        $.post(api_admin, '', function (data) {
                            var this_code = data.code;
                            if(this_code == 0){
                                sessionStorage.setItem('user_list', JSON.stringify(data.data));
                            }
                        });
                        $.post(api_type_course, '', function (data) {
                            var this_code = data.code;
                            if(this_code == 0){
                                sessionStorage.setItem('course_type', JSON.stringify(data.data));
                            }
                        });
                        $.post(api_type_article, '', function (data) {
                            var this_code = data.code;
                            if(this_code == 0){
                                sessionStorage.setItem('article_type', JSON.stringify(data.data));
                            }
                        });
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