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
    var url = api_back + '/course/cms/add';
    var url_single = api_back + '/course/cms/detail';
    var url_update = api_back + '/course/cms/update';
    var url_live_relationList = api_back + '/liveMaster/cms/relationList';
    var url_live_list = api_back + '/liveMaster/cms/list';
    var lk = 0;
    var _lock = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var action = $_GET['action'];
    var id = $_GET['id'];
    var album = $_GET['album'];
    var course_type_select = '<option value="">--课程分类--</option>';
    var course_type = sessionStorage.getItem('course_type');
    course_type = JSON.parse(course_type);
    var course_live_list = get_live_list();
    var course_live_relationList = get_live_relationList();
    var _option_live = '<option value="">请选择直播</option>';

    $.each(course_type, function (i, n) {
        course_type_select += '<option value="' + n.id + '">' + n.typeName + '</option>';
    });
    $('.course_type_id').html(course_type_select);
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
                var content = data.data.course;
                var live = data.data.live;
                if (recode == '0') {
                    $('.album_id').val(content.albumId);
                    $('.course_number').val(content.courseNumber);
                    $('.course_title').val(content.courseTitle);
                    $('.course_describe').val(content.courseDescribe);
                    $('.course_key_words').val(content.courseKeyWords);
                    $('.course_label').val(content.courseLabel);
                    $('.course_release_time').val(content.courseReleaseTime);
                    $('.course_type_id').val(content.courseTypeId);
                    if (content.courseOnline == '1') {
                        $('.switch-online').attr('data-value', content.courseOnline);
                        $('.switch-online').find('.switch-animate').removeClass('switch-off').addClass('switch-on');
                        $('.course_online').val(content.courseOnline);
                    } else {
                        $('.switch-online').attr('data-value', '0');
                        $('.switch-online').find('.switch-animate').removeClass('switch-on').addClass('switch-off');
                        $('.course_online').val('0');
                    }
                    if (content.courseRecommend == '1') {
                        $('.switch-recommend').attr('data-value', content.courseRecommend);
                        $('.switch-recommend').find('.switch-animate').removeClass('switch-off').addClass('switch-on');
                        $('.course_recommend').val(content.courseRecommend);
                    } else {
                        $('.switch-recommend').attr('data-value', '0');
                        $('.switch-recommend').find('.switch-animate').removeClass('switch-on').addClass('switch-off');
                        $('.course_recommend').val('0');
                    }
                    if (content.courseRecommend == '1') {
                        $('.course_recommend').parent().removeClass('switch-off switch-on').addClass('switch-on');
                        $('.course_recommend').val(content.courseRecommend);
                    } else {
                        $('.course_recommend').parent().removeClass('switch-off switch-on').addClass('switch-off');
                        $('.course_recommend').val('0');
                    }
                    var _img_url = content.courseLogo.substr(0, 4);
                    if (_img_url == 'http') {
                        this_img_url = content.courseLogo;
                    } else {
                        this_img_url = server_course + content.courseLogo;
                    }
                    var _ado_url = content.courseAudioUrl.substr(0, 4);
                    if (_ado_url == 'http') {
                        this_ado_url = content.courseAudioUrl;
                    } else {
                        this_ado_url = server_course + content.courseAudioUrl;
                    }
                    $('.radioFile_preview').attr('src', this_ado_url);
                    $('.course_audio_url').val(this_ado_url);
                    $('.article_logo_img img').attr('src', this_img_url);
                    $('.course_logo').val(this_img_url);
                    $('.jodit_wysiwyg').html(content.courseContent).trigger('click');
                    $('.course_status').attr('data-value', content.courseLiveStatus);
                    if (live !== null) {
                        if (course_live_list != '' && course_live_list != null && course_live_list != 'null') {
                            $.each(course_live_list, function (i, n) {
                                _option_live += '<option value="' + n.id + '">' + n.liveMasterName + '</option>';
                            });
                        } else {
                            _alert_warning('直播列表错误！');
                        }
                        $('.liveId').html(_option_live);
                        $('.liveId').val(live.id);
                        $('.course_live_search').attr('disabled', true);
                        $('.liveId').attr('disabled', true);
                    } else {
                        if (course_live_relationList != '' && course_live_relationList != null && course_live_relationList != 'null') {
                            $.each(course_live_relationList, function (i, n) {
                                _option_live += '<option value="' + n.id + '">' + n.name + '</option>';
                            });
                        } 
                        $('.liveId').html(_option_live);
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
                        var course_number = $.trim($('.course_number').val());
                        var course_title = $.trim($('.course_title').val());
                        var course_describe = $.trim($('.course_describe').val());
                        var course_type_id = $('.course_type_id').val();
                        var course_release_time = $('.course_release_time').val();
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var radioFile_preview = $('.radioFile_preview').attr('src');
                        var _audio = $('.radioFile_preview').get(0);
                        var course_content = $.trim($('.course_content').val());
                        var course_logo = $('.course_logo').val();
                        var course_audio_url = $('.course_audio_url').val();
                        var radioFile = $('.radioFile').val();
                        var titleFile = $('.titleFile').val();
                        if (titleFile !== '') {
                            var _img = titleFile;
                        } else if (course_logo !== '') {
                            var _img = course_logo;
                        } else {
                            var _img = '没图片';
                        }
                        if (radioFile !== '') {
                            var _ado = radioFile;
                        } else if (course_audio_url !== '') {
                            var _ado = course_audio_url;
                        } else {
                            var _ado = '没音频';
                        }
                        time = Math.round(_audio.duration);
                        if (!isNaN(time)) {
                            $(this).find('.audio_times').val(time);
                        }
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        var suffix_ado = _ado.substr(_ado.lastIndexOf("."));
                        if (course_number == '') {
                            _alert_warning('期数不能为空！');
                            $('.course_number').focus();
                            return false;
                        } else if (course_title == '') {
                            _alert_warning('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (course_describe == '') {
                            _alert_warning('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (course_release_time == '') {
                            _alert_warning('请选择发布时间！');
                            return false;
                        } else if (course_type_id == '') {
                            _alert_warning('请选择课程分类！');
                            return false;
                        } else if (course_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            _alert_warning('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            _alert_warning('请添加正确的图片');
                            return false;
                        } else if (course_audio_url == '' && radioFile == '') {
                            console.log(suffix_ado);
                            _alert_warning('请添加正确的音频文件！');
                            return false;
                        } else if (suffix_ado !== '.mp3' && suffix_ado !== '.mp3' && suffix_ado !== '.wav' && suffix_ado !== '.WAV') {
                            console.log(suffix_ado);
                            _alert_warning('请添加正确的音频文件');
                            return false;
                        } else if (course_content == '') {
                            _alert_warning('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log(data);
                        if (data.code == '0') {
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
                                    window.location.href = "./course.html";
                                }
                            });
                        } else {
                            _alert_warning(data.msg);
                        }
                    },
                    error: function (data) {
                        _alert_warning(data.msg);
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
                $(this).find('div.l_d input.this_type').attr('name', 'extList[' + this_no + '].type').val(this_type);
                if (this_type == '1') {
                    tm = 'words';
                } else if (this_type == '2') {
                    var _audio = $(this).find('audio').get(0);
                    time = Math.round(_audio.duration);
                    $(this).find('.audio_times').attr('name', 'extList[' + this_no + '].times').val(time);
                    if ($(this).find('div:nth-child(3) .this_value').attr('type') == 'file') {
                        tm = 'audioFile';
                    } else {
                        tm = 'audioUrl';
                    }
                } else if (this_type == '3') {
                    if ($(this).find('div:nth-child(3) .this_value').attr('type') == 'file') {
                        tm = 'imgFile';
                    } else {
                        tm = 'imgUrl';
                    }
                }
                $(this).find('div:nth-child(3) .this_value').attr('name', 'extList[' + this_no + '].' + tm);
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
                        beforeSend: function () {},
                        success: function (data) {
                            console.log(data);
                            if (data.code == '0') {
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
                                        window.location.href = "./course.html";
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
                }, 300);
            }
        });
    } else if (action == 'add' && album !== '' && album !== undefined) {
        $('.album_id').val(album);
        $('.course_create_id').val(user.id);
        if (course_live_relationList != '' && course_live_relationList != null && course_live_relationList != 'null') {
            $.each(course_live_relationList, function (i, n) {
                _option_live += '<option value="' + n.id + '">' + n.name + '</option>';
            });
        }
        $('.liveId').html(_option_live);
        $('.course_live_search').keyup(function () {
            var key_words = $(this).val();
            var reg = new RegExp(key_words);
            var option = '';
            if (key_words !== '') {
                $.each(course_live_list, function (k, v) {
                    if (reg.test(v.name)) {
                        option += '<option data-value=' + v.name + ' value="' + v.id + '">' + v.name + '</option>';
                    }
                });
            } else {
                option += '<option value="">请选择</option>';
                $.each(course_live_list, function (k, v) {
                    option += '<option data-value=' + v.name + ' value="' + v.id + '">' + v.name + '</option>';
                });
            }
            $('.liveId').html(option);
        });



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
                        var course_number = $.trim($('.course_number').val());
                        var course_title = $.trim($('.course_title').val());
                        var course_describe = $.trim($('.course_describe').val());
                        var course_type_id = $('.course_type_id').val();
                        var course_release_time = $('.course_release_time').val();
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var radioFile_preview = $('.radioFile_preview').attr('src');
                        var _audio = $('.radioFile_preview').get(0);
                        var course_content = $.trim($('.course_content').val());
                        var course_logo = $('.course_logo').val();
                        var course_audio_url = $('.course_audio_url').val();
                        var radioFile = $('.radioFile').val();
                        var titleFile = $('.titleFile').val();
                        if (titleFile !== '') {
                            var _img = titleFile;
                        } else if (course_logo !== '') {
                            var _img = course_logo;
                        } else {
                            var _img = '没图片';
                        }
                        if (radioFile !== '') {
                            var _ado = radioFile;
                        } else if (course_audio_url !== '') {
                            var _ado = course_audio_url;
                        } else {
                            var _ado = '没音频';
                        }
                        time = Math.round(_audio.duration);
                        if (!isNaN(time)) {
                            $(this).find('.audio_times').val(time);
                        }
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        var suffix_ado = _ado.substr(_ado.lastIndexOf("."));
                        if (course_number == '') {
                            _alert_warning('期数不能为空！');
                            $('.course_number').focus();
                            return false;
                        } else if (course_title == '') {
                            _alert_warning('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (course_describe == '') {
                            _alert_warning('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (course_release_time == '') {
                            _alert_warning('请选择发布时间！');
                            return false;
                        } else if (course_type_id == '') {
                            _alert_warning('请选择课程分类！');
                            return false;
                        } else if (course_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            _alert_warning('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            _alert_warning('请添加正确的图片');
                            return false;
                        } else if (course_audio_url == '' && radioFile == '') {
                            console.log(suffix_ado);
                            _alert_warning('请添加正确的音频文件！');
                            return false;
                        } else if (suffix_ado !== '.mp3' && suffix_ado !== '.mp3' && suffix_ado !== '.wav' && suffix_ado !== '.WAV') {
                            console.log(suffix_ado);
                            _alert_warning('请添加正确的音频文件');
                            return false;
                        } else if (course_content == '') {
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
                                    window.location.href = "./course.html";
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
        $modal({
            type: 'alert',
            icon: 'warning',
            timeout: 3000,
            title: '警告',
            content: '链接错误！',
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
    }

    function get_live_list() {
        var arr_live_list = {
            page: 0,
            size: '99999',
            live_master_name: '',
            operator_id: '',
            status: ''
        }
        var _data = '';
        $.ajax({
            url: url_live_list,
            type: 'POST',
            data: arr_live_list,
            async: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
            success: function (data) {
                if (data.code == '0') {
                    _data = data.data.data;
                } else {}
            },
            complete: function () {},
            error: function (data) {}
        }, "json");
        return _data;
    }

    function get_live_relationList() {
        var arr_live_list = {
            page: 0,
            size: '99999',
            live_master_name: '',
            operator_id: '',
            status: ''
        }
        var _data = '';
        $.ajax({
            url: url_live_relationList,
            type: 'POST',
            data: arr_live_list,
            async: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
            success: function (data) {
                if (data.code == '0') {
                    _data = data.data;
                } else {}
            },
            complete: function () {},
            error: function (data) {}
        }, "json");
        return _data;
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