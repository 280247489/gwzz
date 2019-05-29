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
        $('.titleFile').trigger('click');
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
        if ($('.titleFile')[0].files[0] !== undefined) {
            $(".article_logo_img img").attr("src", URL.createObjectURL($(this)[0].files[0]));
        } else {
            $(".article_logo_img img").attr("src", "./assets/img/add.png");
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
                    var _img_url = content.articleLogo.substr(0, 4);
                    if (_img_url == 'http') {
                        this_img_url = content.articleLogo;
                    } else {
                        this_img_url = server_article + content.articleLogo;
                    }
                    var _ado_url = content.articleAudioUrl.substr(0, 4);
                    if (_ado_url == 'http') {
                        this_ado_url = content.articleAudioUrl;
                    } else {
                        this_ado_url = server_article + content.articleAudioUrl;
                    }
                    $('.radioFile_preview').attr('src', this_ado_url);
                    $('.course_audio_url').val(this_ado_url);

                    $('.article_logo_img img').attr('src', this_img_url);
                    $('.course_logo').val(this_img_url);
                    $('.jodit_wysiwyg').html(content.articleContent).trigger('click');
                    $('.course_status').attr('data-value', content.articleLiveStatus);
                    if (content.articleLiveStatus == '1') {
                        $('.course_status').html('点击下线直播');
                    } else {
                        $('.course_status').html('点击发布直播');
                    }
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
                        var article_type_id = $('.course_type_id').val();
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var article_content = $.trim($('.course_content').val());
                        var article_logo = $('.course_logo').val();
                        var titleFile = $('.titleFile').val();
                        if (titleFile !== '') {
                            var _img = titleFile;
                        } else if (article_logo !== '') {
                            var _img = article_logo;
                        } else {
                            var _img = '没图片';
                        }
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        if (article_title == '') {
                            alert('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (article_describe == '') {
                            alert('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (article_type_id == '') {
                            alert('请选择课程分类！');
                            return false;
                        } else if (article_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            alert('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            alert('请添加正确的图片');
                            return false;
                        } else if (article_content == '') {
                            alert('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log('2');
                        console.log(data);
                        alert('提交成功！');
                    },
                    error: function (data) {
                        console.log('3');
                        console.log(data);
                    }
                }, "json");
            }
        });
        $('.zhibo_submit').click(function () {
            if ($('#courseId').val() == '' || $('#courseId').val() == undefined) {
                return false;
            }


            $('.load_table li').each(function () {
                var tm = '0';
                var time = 0;
                var this_no = $(this).index();
                $(this).attr('data-order', this_no);
                var this_id = $('#courseId').val();
                var this_type = $(this).find('div:nth-child(3)').attr('data-type');
                $(this).find('div:nth-child(1) select').attr('name', 'extList[' + this_no + '].name');
                $(this).find('div:nth-child(1) input').attr('name', 'extList[' + this_no + '].name');
                $(this).find('div:nth-child(1) .courseId').attr('name', 'extList[' + this_no + '].courseId').val(this_id);
                $(this).find('div:nth-child(2).l_d input').attr('name', 'extList[' + this_no + '].type').val(this_type);
                if (this_type == '1') {
                    tm = 'words';
                } else if (this_type == '2') {
                    var _audio = $(this).find('audio').get(0);
                    time = Math.round(_audio.duration);
                    $(this).find('.audio_times').attr('name', 'extList[' + this_no + '].times').val(time);
                    if ($(this).find('div:nth-child(3) input').attr('type') == 'file') {
                        tm = 'audioFile';
                    } else {
                        tm = 'audioUrl';
                    }
                } else if (this_type == '3') {
                    if ($(this).find('div:nth-child(3) input').attr('type') == 'file') {
                        tm = 'imgFile';
                    } else {
                        tm = 'imgUrl';
                    }
                }
                $(this).find('div:nth-child(3) input').attr('name', 'extList[' + this_no + '].' + tm);
            });
            if (lk > 0) {
                return false;
            } else {
                lk = 1;
                setTimeout(function () {
                    lk = 0;
                }, 3300);
                setTimeout(function () {
                    var url_which = eval($('#zhibo_msg_list').attr('data-type'));
                    var formData = new FormData($("#zhibo_msg_list")[0]);
                    $.ajax({
                        url: url_which,
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



                            return false;
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
                }, 300);
            }
        });
        $('.course_status').click(function () {
            var status_curr = $(this).attr('data-value');
            var status = '0';
            if (status_curr == '1') {
                status = '0';
            } else {
                status = '1';
            }
            //sdfsadfsadfsadfsdf
            var arr_status = {
                id: id,
                status: status 
            }
            $.post(url_status, arr_status, function (data) {
                console.log(data);
                if (data.code == '0') {
                    $('.course_status').attr('data-value', data.data.articleLiveStatus);
                    if (data.data.articleLiveStatus == '0') {
                        $('.course_status').html('点击发布直播');
                    } else if (data.data.articleLiveStatus == '1') {
                        $('.course_status').html('点击下线直播');
                    }
                } else {}
            });
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
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var article_content = $.trim($('.course_content').val());
                        var article_logo = $('.course_logo').val();
                        var titleFile = $('.titleFile').val();
                        if (titleFile !== '') {
                            var _img = titleFile;
                        } else if (article_logo !== '') {
                            var _img = article_logo;
                        } else {
                            var _img = '没图片';
                        }
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        if (article_title == '') {
                            alert('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (article_describe == '') {
                            alert('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (article_type_id == '') {
                            alert('请选择课程分类！');
                            return false;
                        } else if (article_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            alert('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            alert('请添加正确的图片');
                            return false;
                        } else if (article_content == '') {
                            alert('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log(data);
                        var content = data.data;
                        $('#zhibo_msg_list').attr('data-article-id', content.id);
                        $('#courseId').val(content.id);
                        alert('添加成功！');
                        window.location.href = "./articleNormalAdd.html?action=edit&id=" + content.id;

                    },
                    error: function (data) {
                        console.log(data);
                    }
                }, "json");
            }
        });

    }
    

});