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
    var server_article = sessionStorage.getItem('server_course');
    var url = api_back + '/article/cms/add';

    var url_single = api_back + '/article/cms/detail';
    var url_update = api_back + '/article/cms/update';

    var url_status = api_back + '/article/cms/liveStatus';
    var lk = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var action = $_GET['action'];
    var id = $_GET['id'];
    var article_type_select = '<option value="">--文章分类--</option>';
    var article_type = sessionStorage.getItem('article_type');
    article_type = JSON.parse(article_type);



    $.each(article_type, function (i, n) {
        article_type_select += '<option value="' + n.id + '">' + n.typeName + '</option>';
    });
    $('.course_type_id').html(article_type_select);
    $('.has-switch').click(function () {
        vl = $(this).find('.switch-animate');
        if (vl.hasClass('switch-on')) {
            vl.removeClass('switch-on').addClass('switch-off');
            $(this).find('input').val('0');
            $(this).attr("data-value", "0");
        } else {
            vl.removeClass('switch-off').addClass('switch-on');
            $(this).attr("data-value", "1");
            $(this).find('input').val('1');
        }
    });
    $('.article_logo_img').click(function () {
        $(this).parent().find('.titleFile').trigger('click');
    });
    $('.choose_radio').click(function () {
        $('.radioFile').trigger('click');
    });
    $(".radioFile").change(function () {
        if ($('.radioFile')[0].files[0] !== undefined) {
            $(".radioFile_preview").attr("src", URL.createObjectURL($(this)[0].files[0]));
        } else {
            $(".radioFile_preview").attr("src", "");
        }
    });
    $(".titleFile").change(function () {
        if ($(this)[0].files[0] !== undefined) {
            $(this).parent().parent().find(".article_logo_img img").attr("src", URL.createObjectURL($(this)[0].files[0]));
        } else {
            $(this).parent().parent().find(".article_logo_img img").attr("src", "dist/img/add.png");
        }
    });




    if (action == 'edit' && id !== '' && id !== undefined) {
        $('.course_update_id').val(user.id);
        $('.edit_id').val(id);
        $('.article_submit').html('提交修改');
        $('#zhibo_msg_list').attr('data-article-id', id);
        $('#courseId').val(id);
        to_page(id);

        function to_page(id) {
            var arr_single = {
                id: id
            }
            $.post(url_single, arr_single, function (data) {
                console.log(data);
                var recode = data.code;
                var content = data.data;
                if (recode == '0') {
                    $('.course_title').val(content.articleTitle);
                    $('.course_describe').val(content.articleDescribe);
                    $('.course_key_words').val(content.articleKeyWords);
                    $('.course_label').val(content.articleLabel);
                    $('.article_release_time').val(content.articleReleaseTime);
                    $('.course_type_id').val(content.typeId);
                    if (content.articleOnline == '1') {
                        $('.switch-online').attr('data-value', content.articleOnline);
                        $('.switch-online').find('.switch-animate').removeClass('switch-off').addClass('switch-on');
                        $('.course_online').val(content.articleOnline);
                    } else {
                        $('.switch-online').attr('data-value', '0');
                        $('.switch-online').find('.switch-animate').removeClass('switch-on').addClass('switch-off');
                        $('.course_online').val('0');
                    }
                    if (content.articleRecommend == '1') {
                        $('.switch-recommend').attr('data-value', content.articleRecommend);
                        $('.switch-recommend').find('.switch-animate').removeClass('switch-off').addClass('switch-on');
                        $('.course_recommend').val(content.articleRecommend);
                    } else {
                        $('.switch-recommend').attr('data-value', '0');
                        $('.switch-recommend').find('.switch-animate').removeClass('switch-on').addClass('switch-off');
                        $('.course_recommend').val('0');
                    }
                    if (content.articleRecommend == '1') {
                        $('.course_recommend').parent().removeClass('switch-off switch-on').addClass('switch-on');
                        $('.course_recommend').val(content.articleRecommend);
                    } else {
                        $('.course_recommend').parent().removeClass('switch-off switch-on').addClass('switch-off');
                        $('.course_recommend').val('0');
                    }
                    var _img_url1 = content.articleLogo1.substr(0, 4);
                    var _img_url2 = content.articleLogo2.substr(0, 4);
                    var _img_url3 = content.articleLogo3.substr(0, 4);
                    if (_img_url1 == 'http') {
                        this_img_url1 = content.articleLogo1;
                    } else {
                        this_img_url1 = server_article + content.articleLogo1;
                    }
                    if (_img_url2 == 'http') {
                        this_img_url2 = content.articleLogo2;
                    } else {
                        this_img_url2 = server_article + content.articleLogo2;
                    }
                    if (_img_url3 == 'http') {
                        this_img_url3 = content.articleLogo3;
                    } else {
                        this_img_url3 = server_article + content.articleLogo3;
                    }
                    var _ado_url = content.articleAudioUrl.substr(0, 4);
                    if (_ado_url == 'http') {
                        this_ado_url = content.articleAudioUrl;
                    } else {
                        this_ado_url = server_article + content.articleAudioUrl;
                    }
                    $('.radioFile_preview').attr('src', this_ado_url);
                    $('.course_audio_url').val(this_ado_url);

                    $('.article_logo_img1 img').attr('src', this_img_url1);
                    $('.article_logo_img2 img').attr('src', this_img_url2);
                    $('.article_logo_img3 img').attr('src', this_img_url3);
                    $('.course_logo1').val(content.articleLogo1);
                    $('.course_logo2').val(content.articleLogo2);
                    $('.course_logo3').val(content.articleLogo3);
                    $('.jodit_wysiwyg').html(content.articleContent).trigger('click');
                } else {
                    console.log('错误');
                }
            });
        }


        $('.article_submit').click(function () {
            if (lk > 0) {
                return false;
            } else {
                lk = 1;
                setTimeout(function () {
                    lk = 0;
                }, 3000);
                var formData = new FormData($("#articleHealthAdd")[0]);
                $.ajax({
                    url: url_update,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: "json",
                    timeout: 50000,
                    beforeSend: function () {
                        var article_title = $.trim($('.course_title').val());
                        var article_describe = $.trim($('.course_describe').val());
                        var article_release_time =$('.article_release_time').val();
                        var article_type_id = $('.course_type_id').val();
                        var article_logo_img1 = $('.article_logo_img1 img').attr('src');
                        var article_logo_img2 = $('.article_logo_img2 img').attr('src');
                        var article_logo_img3 = $('.article_logo_img3 img').attr('src');
                        var article_content = $.trim($('.course_content').val());
                        var article_logo1 = $('.course_logo1').val();
                        var article_logo2 = $('.course_logo2').val();
                        var article_logo3 = $('.course_logo3').val();
                        var titleFile1 = $('.titleFile1').val();
                        var titleFile2 = $('.titleFile2').val();
                        var titleFile3 = $('.titleFile3').val();
                        if (titleFile1 !== '') {
                            var _img1 = titleFile1;
                        } else if (article_logo1 !== '') {
                            var _img1 = article_logo1;
                        } else {
                            var _img1 = '没图片';
                        }

                        if (titleFile2 !== '') {
                            var _img2 = titleFile2;
                        } else if (article_logo2 !== '') {
                            var _img2 = article_logo2;
                        } else {
                            var _img2 = '没图片';
                        }

                        if (titleFile3 !== '') {
                            var _img3 = titleFile3;
                        } else if (article_logo3 !== '') {
                            var _img3 = article_logo3;
                        } else {
                            var _img3 = '没图片';
                        }
                        var suffix_img1 = _img1.substr(_img1.lastIndexOf("."));
                        var suffix_img2 = _img2.substr(_img2.lastIndexOf("."));
                        var suffix_img3 = _img3.substr(_img3.lastIndexOf("."));

                        if (article_title == '') {
                            _alert_warning('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (article_describe == '') {
                            _alert_warning('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (article_release_time == '') {
                            _alert_warning('请选择发布时间！');
                            return false;
                        }  else if (article_type_id == '') {
                            _alert_warning('请选择课程分类！');
                            return false;
                        } else if (article_logo1 == '' && titleFile1 == '') {
                            console.log(suffix_img1);
                            _alert_warning('请添加正确的图片1！');
                            return false;
                        } else if (article_logo2 == '' && titleFile2 == '') {
                            console.log(suffix_img2);
                            _alert_warning('请添加正确的图片2！');
                            return false;
                        } else if (article_logo3 == '' && titleFile3 == '') {
                            console.log(suffix_img3);
                            _alert_warning('请添加正确的图片3！');
                            return false;
                        } else if (suffix_img1 !== '.jpg' && suffix_img1 !== '.JPG' && suffix_img1 !== '.png' && suffix_img1 !== '.PNG' && suffix_img1 !== '.gif' && suffix_img1 !== '.GIF' && suffix_img1 !== '.jpeg' && suffix_img1 !== '.JPEG') {
                            console.log(titleFile1);
                            console.log(suffix_img1);
                            _alert_warning('请添加正确的图片1');
                            return false;
                        } else if (suffix_img2 !== '.jpg' && suffix_img2 !== '.JPG' && suffix_img2 !== '.png' && suffix_img2 !== '.PNG' && suffix_img2 !== '.gif' && suffix_img2 !== '.GIF' && suffix_img2 !== '.jpeg' && suffix_img2 !== '.JPEG') {
                            console.log(titleFile2);
                            console.log(suffix_img2);
                            _alert_warning('请添加正确的图片2');
                            return false;
                        } else if (suffix_img3 !== '.jpg' && suffix_img3 !== '.JPG' && suffix_img3 !== '.png' && suffix_img3 !== '.PNG' && suffix_img3 !== '.gif' && suffix_img3 !== '.GIF' && suffix_img3 !== '.jpeg' && suffix_img3 !== '.JPEG') {
                            console.log(titleFile3);
                            console.log(suffix_img3);
                            _alert_warning('请添加正确的图片3');
                            return false;
                        } else if (article_content == '') {
                            _alert_warning('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log(data);
                        var content = data.data;
                        if (data.code == '0') {
                            $('#zhibo_msg_list').attr('data-article-id', content.id);
                            $('#courseId').val(content.id);
                            $modal({
                                type: 'alert',
                                icon: 'success',
                                timeout: 3000,
                                title: '成功',
                                content: '添加成功！',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                    window.location.href = "./article.html";
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
    } else {
        $('.course_create_id').val(user.id);
        $('.article_submit').click(function () {
            if (lk > 0) {
                return false;
            } else {
                lk = 1;
                setTimeout(function () {
                    lk = 0;
                }, 3000);
                var formData = new FormData($("#articleHealthAdd")[0]);
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: "json",
                    timeout: 50000,
                    beforeSend: function () {
                        var article_title = $.trim($('.course_title').val());
                        var article_describe = $.trim($('.course_describe').val());
                        var article_type_id = $('.course_type_id').val();
                        var article_logo_img1 = $('.article_logo_img1 img').attr('src');
                        var article_logo_img2 = $('.article_logo_img2 img').attr('src');
                        var article_logo_img3 = $('.article_logo_img3 img').attr('src');
                        var article_content = $.trim($('.course_content').val());
                        var article_logo1 = $('.course_logo1').val();
                        var article_logo2 = $('.course_logo2').val();
                        var article_logo3 = $('.course_logo3').val();
                        var titleFile1 = $('.titleFile1').val();
                        var titleFile2 = $('.titleFile2').val();
                        var titleFile3 = $('.titleFile3').val();
                        if (titleFile1 !== '') {
                            var _img1 = titleFile1;
                        } else if (article_logo1 !== '') {
                            var _img1 = article_logo1;
                        } else {
                            var _img1 = '没图片';
                        }

                        if (titleFile2 !== '') {
                            var _img2 = titleFile2;
                        } else if (article_logo2 !== '') {
                            var _img2 = article_logo2;
                        } else {
                            var _img2 = '没图片';
                        }

                        if (titleFile3 !== '') {
                            var _img3 = titleFile3;
                        } else if (article_logo !== '') {
                            var _img3 = article_logo3;
                        } else {
                            var _img3 = '没图片';
                        }
                        var suffix_img1 = _img1.substr(_img1.lastIndexOf("."));
                        var suffix_img2 = _img2.substr(_img2.lastIndexOf("."));
                        var suffix_img3 = _img3.substr(_img3.lastIndexOf("."));
                        if (article_title == '') {
                            _alert_warning('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (article_describe == '') {
                            _alert_warning('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (article_type_id == '') {
                            _alert_warning('请选择课程分类！');
                            return false;
                        } else if (article_logo1 == '' && titleFile1 == '') {
                            console.log(suffix_img1);
                            _alert_warning('请添加正确的图片1！');
                            return false;
                        } else if (article_logo2 == '' && titleFile2 == '') {
                            console.log(suffix_img2);
                            _alert_warning('请添加正确的图片2！');
                            return false;
                        } else if (article_logo3 == '' && titleFile3 == '') {
                            console.log(suffix_img3);
                            _alert_warning('请添加正确的图片3！');
                            return false;
                        } else if (suffix_img1 !== '.jpg' && suffix_img1 !== '.JPG' && suffix_img1 !== '.png' && suffix_img1 !== '.PNG' && suffix_img1 !== '.gif' && suffix_img1 !== '.GIF' && suffix_img1 !== '.jpeg' && suffix_img1 !== '.JPEG') {
                            console.log(titleFile1);
                            console.log(suffix_img1);
                            _alert_warning('请添加正确的图片1');
                            return false;
                        } else if (suffix_img2 !== '.jpg' && suffix_img2 !== '.JPG' && suffix_img2 !== '.png' && suffix_img2 !== '.PNG' && suffix_img2 !== '.gif' && suffix_img2 !== '.GIF' && suffix_img2 !== '.jpeg' && suffix_img2 !== '.JPEG') {
                            console.log(titleFile2);
                            console.log(suffix_img2);
                            _alert_warning('请添加正确的图片2');
                            return false;
                        } else if (suffix_img3 !== '.jpg' && suffix_img3 !== '.JPG' && suffix_img3 !== '.png' && suffix_img3 !== '.PNG' && suffix_img3 !== '.gif' && suffix_img3 !== '.GIF' && suffix_img3 !== '.jpeg' && suffix_img3 !== '.JPEG') {
                            console.log(titleFile3);
                            console.log(suffix_img3);
                            _alert_warning('请添加正确的图片3');
                            return false;
                        } else if (article_content == '') {
                            _alert_warning('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log(data);
                        var content = data.data;
                        $('#zhibo_msg_list').attr('data-article-id', content.id);
                        $('#courseId').val(content.id);
                        $modal({
                            type: 'alert',
                            icon: 'success',
                            timeout: 3000,
                            title: '成功',
                            content: '添加成功！',
                            top: 300,
                            center: true,
                            transition: 300,
                            closable: true,
                            mask: true,
                            pageScroll: true,
                            width: 300,
                            maskClose: true,
                            callBack: function () {
                                window.location.href = "./article.html";
                            }
                        });

                    },
                    error: function (data) {
                        _alert_warning(data.msg);
                    }
                }, "json");
            }
        });

    }

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