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
    var url_Ext_add = api_back + '/courseExt/cms/add';
    var url_single = api_back + '/course/cms/detail';
    var url_update = api_back + '/course/cms/update';
    var url_Ext = api_back + '/courseExt/cms/list';
    var url_Ext_upd = api_back + '/courseExt/cms/update';
    var lk = 0;
    var _lock = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var action = $_GET['action'];
    var id = $_GET['id'];
    var course_type_select = '<option value="">--课程分类--</option>';
    var course_type = sessionStorage.getItem('course_type');
    course_type = JSON.parse(course_type);



    $.each(course_type, function (i, n) {
        course_type_select += '<option value="' + n.id + '">' + n.typeName + '</option>';
    });
    $('.course_type_id').html(course_type_select);
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
        to_Ext(id);

        function to_page(id) {
            var arr_single = {
                id: id
            }
            $.post(url_single, arr_single, function (data) {
                var recode = data.code;
                var content = data.data;
                if (recode == '0') {
                    $('.course_title').val(content.courseTitle);
                    $('.course_describe').val(content.courseDescribe);
                    $('.course_key_words').val(content.courseKeyWords);
                    $('.course_label').val(content.courseLabel);
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
                } else {
                    console.log('错误');
                }
            });
        }

        function to_Ext(id) {
            var arr_Ext = {
                courseId: id
            }
            var li = '';
            $.post(url_Ext, arr_Ext, function (data) {
                console.log(data);
                var content = data.data;
                if (content[0] == undefined || content[0] == '') {
                    $('#zhibo_msg_list').attr('data-type', 'url_Ext_add');
                } else {
                    $('#zhibo_msg_list').attr('data-type', 'url_Ext_upd');
                }
                var _type;
                var _content;
                $.each(content, function (i, n) {
                    if (n.courseExtType == '1') {
                        _content = n.courseExtWords;
                        _type = '文字';
                        prew = '<p>' + n.courseExtWords + '</p>';
                    } else if (n.courseExtType == '2') {
                        var http = n.courseExtAudio.substr(0, 4);
                        if (http == 'http') {
                            prew = '<audio src="' + n.courseExtAudio + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                        } else {
                            prew = '<audio src="' + server_course + n.courseExtAudio + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                        }
                        _type = '音频';
                        _content = n.courseExtAudio;
                    } else if (n.courseExtType == '3') {
                        var http = n.courseExtImgUrl.substr(0, 4);
                        if (http == 'http') {
                            prew = '<img class="img_in_table" src="' + n.courseExtImgUrl + '"><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                        } else {
                            prew = '<img class="img_in_table" src="' + server_course + n.courseExtImgUrl + '"><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                        }
                        _type = '图片';
                        _content = n.courseExtImgUrl;
                    }
                    li += '<li data-order="" class="excelcontent"><div><input type="hidden" class="courseId" value="" name=""><input type="text" class="speaker form-control" value="' + n.courseExtNickname + '" name="name"></div><div  data-type="' + n.courseExtType + '">' + _type + '</div><div class="l_d" data-type="' + n.courseExtType + '">' + prew + '<div class="hide"><input class="this_type" type="hidden" value="" name=""><input class="this_value" type="hidden" value="' + _content + '" name=""></div></div><div><button type="button" class="delete btn btn-primary" href="javascript:void(0);">删除</button><input class="audio_times" type="hidden" value="0" name="times"></div></li>';
                });
                $('.load_table').html(li);
            })
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
                        var course_title = $.trim($('.course_title').val());
                        var course_describe = $.trim($('.course_describe').val());
                        var course_type_id = $('.course_type_id').val();
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var radioFile_preview = $('.radioFile_preview').attr('src');
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
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        var suffix_ado = _ado.substr(_ado.lastIndexOf("."));
                        if (course_title == '') {
                            alert('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (course_describe == '') {
                            alert('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (course_type_id == '') {
                            alert('请选择课程分类！');
                            return false;
                        } else if (course_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            alert('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            alert('请添加正确的图片');
                            return false;
                        } else if (course_audio_url == '' && radioFile == '') {
                            console.log(suffix_ado);
                            alert('请添加正确的音频文件！');
                            return false;
                        } else if (suffix_ado !== '.mp3' && suffix_ado !== '.mp3' && suffix_ado !== '.wav' && suffix_ado !== '.WAV') {
                            console.log(suffix_ado);
                            alert('请添加正确的音频文件');
                            return false;
                        } else if (course_content == '') {
                            alert('文章内容不能为空！');
                            $('.jodit_wysiwyg').focus();
                            return false;
                        } else {}
                    },
                    success: function (data) {
                        console.log(data);
                        alert('提交成功！');
                    },
                    error: function (data) {
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
                        beforeSend: function () {
                        },
                        success: function (data) {
                            alert('提交成功');
                            console.log(data);
                            window.location.href = "./articleHealth.html";
                        },
                        error: function (data) {
                            console.log(data);
                        }
                    }, "json");
                }, 300);
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
                        var course_title = $.trim($('.course_title').val());
                        var course_describe = $.trim($('.course_describe').val());
                        var course_type_id = $('.course_type_id').val();
                        var article_logo_img = $('.article_logo_img img').attr('src');
                        var radioFile_preview = $('.radioFile_preview').attr('src');
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
                        var suffix_img = _img.substr(_img.lastIndexOf("."));
                        var suffix_ado = _ado.substr(_ado.lastIndexOf("."));
                        if (course_title == '') {
                            alert('标题不能为空！');
                            $('.course_title').focus();
                            return false;
                        } else if (course_describe == '') {
                            alert('描述不能为空！');
                            $('.course_describe').focus();
                            return false;
                        } else if (course_type_id == '') {
                            alert('请选择课程分类！');
                            return false;
                        } else if (course_logo == '' && titleFile == '') {
                            console.log(suffix_img);
                            alert('请添加正确的图片！');
                            return false;
                        } else if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                            console.log(titleFile);
                            console.log(suffix_img);
                            alert('请添加正确的图片');
                            return false;
                        } else if (course_audio_url == '' && radioFile == '') {
                            console.log(suffix_ado);
                            alert('请添加正确的音频文件！');
                            return false;
                        } else if (suffix_ado !== '.mp3' && suffix_ado !== '.mp3' && suffix_ado !== '.wav' && suffix_ado !== '.WAV') {
                            console.log(suffix_ado);
                            alert('请添加正确的音频文件');
                            return false;
                        } else if (course_content == '') {
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
                        window.location.href = "./articleHealthAdd.html?action=edit&id=" + content.id;

                    },
                    error: function (data) {
                        console.log(data);
                    }
                }, "json");
            }
        });

    }
    $('#excel_file').click(function () {
        $('#excel-file').trigger('click');
    });
    $('.new_msg_file').click(function () {
        $('.new_msg_file_url').val('');
        $(".load_table li:last-child").find('div:nth-child(3) .this_value').attr('type', 'file').trigger('click');
    });
    $('.new_msg_file_url').blur(function () {
        $(".load_table li:last-child").find('div:nth-child(3) .this_value').attr('type', 'hidden');
    });
    $(".load_table").on('change', '.new_file', function () {
        var _this = $(this).val();
        var suffix_this = _this.substr(_this.lastIndexOf("."));
        var _type = $(this).parent().parent().attr('data-type');
        if ($(".new_file")[0].files[0] !== undefined) {
            if (_type == '2') {
                if (suffix_this !== '.mp3' && suffix_this !== '.MP3' && suffix_this !== '.wav' && suffix_this !== '.WAV') {
                    alert('请选择正确格式的音频文件!(mp3、wav)');
                    return false;
                }
                $(".new_msg_file").html("<i class='icon-check'></i> 已选择");
                $(this).parent().parent().find('audio').remove();
                $(this).parent().parent().find('img').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().find('a').remove();
                $(this).parent().parent().append('<audio src="' + URL.createObjectURL($(this)[0].files[0]) + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            } else if (_type == '3') {
                if (suffix_this !== '.jpg' && suffix_this !== '.JPG' && suffix_this !== '.jpeg' && suffix_this !== '.JPEG' && suffix_this !== '.png' && suffix_this !== '.PNG' && suffix_this !== '.gif' && suffix_this !== '.GIF') {
                    alert('请选择正确格式的图片文件!(jpg、jpeg、gif、png)');
                    return false;
                }
                $(".new_msg_file").html("<i class='icon-check'></i> 已选择");
                $(this).parent().parent().find('audio').remove();
                $(this).parent().parent().find('img').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().find('a').remove();
                $(this).parent().parent().append('<img class="img_in_table" src="' + URL.createObjectURL($(this)[0].files[0]) + '" /><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            }
        } else {
            $(".new_msg_file").html("<i class='icon-cloud-upload'></i> 上传文件");
        }
    });
    $('.zhibo_msg_add').click(function () {
        $('.new_msg_word').val('');
        var li = '<li data-order="" class="excelcontent"><div><input type="hidden" class="courseId" value="" name=""><input type="text" class="speaker form-control" value="李老师" name="name"></div><div class="sec"></div><div class="l_d" data-type="1"><p></p><div class="hide"><input class="this_type" type="hidden" value="1" name="" /><input class="new_file this_value" type="hidden" value="" name=""></div></div><div><button type="button" class="delete btn btn-primary" href="javascript:void(0);">删除</button><input class="audio_times" type="hidden" value="0" name="times"></div></li>';
        $('.no_msg').remove();
        $(".load_table").append(li);
        $('.new_msg_type:eq(0)').trigger('click');
        $('.single_msg').fadeIn();
    });
    $('.new_msg_cancel').click(function () {
        $('.new_msg_file_url').val('');
        $('.new_msg_word').val('');
        $(".new_msg_file").html("<i class='icon-cloud-upload'></i> 上传文件");
        $(".load_table li:last-child").remove();
        $('.single_msg').fadeOut();
    });
    $('.new_msg_type').change(function () {
        $('.new_msg_file_url').val('');
        $(".load_table li:last-child").find('div:nth-child(3) .this_value').val('');
        $('.new_msg_word').val('');
        $(".new_msg_file").html("<i class='icon-cloud-upload'></i> 上传文件");
        $('.load_table li:last-child').find('div:nth-child(3)').attr('data-type', $(this).val());
        $('.load_table li:last-child').find('div:nth-child(3)').find('audio').remove();
        $('.load_table li:last-child').find('div:nth-child(3)').find('p').remove();
        $('.load_table li:last-child').find('div:nth-child(3)').find('img').remove();
        $('.load_table li:last-child').find('div:nth-child(3)').find('a').remove();
        if ($(this).val() == '2') {
            $('.new_msg_word_box').hide();
            $('.new_msg_file_box').fadeIn();
            $('.load_table li:last-child').find('.sec').html('音频');
            $('.load_table li:last-child').find('.l_d .this_value').attr('type', 'hidden');
        } else if ($(this).val() == '3') {
            $('.new_msg_word_box').hide();
            $('.new_msg_file_box').fadeIn();
            $('.load_table li:last-child').find('.sec').html('图片');
        } else {
            $('.new_msg_file_box').hide();
            $('.new_msg_word_box').fadeIn();
            $('.load_table li:last-child').find('.sec').html('文字');
            $('.load_table li:last-child').find('.l_d .this_value').attr('type', 'hidden');
        }
    });
    $('.new_msg_speaker').change(function () {
        $(".load_table li:last-child .speaker").val($(this).val());
    });

    $('.new_msg_file_url').focus(function () {
        $('.load_table li:last-child').find('div:nth-child(3) .this_value').attr('type', 'hidden');
    });
    $('.new_msg_file_url').change(function () {
        if ($('.new_file').val() !== '') {} else {
            $('.new_file').val($(this).val());
            var _type = $('.new_msg_type:checked').val();
            if (_type == '2') {
                $('.new_file').parent().parent().find('audio').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().find('img').remove();
                $('.new_file').parent().parent().append('<audio src="' + $(this).val() + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            } else if (_type == '3') {
                $('.new_file').parent().parent().find('audio').remove();
                $('.new_file').parent().parent().find('img').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().append('<img class="img_in_table" src="' + $(this).val() + '" /><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            }
            $('.load_table li:last-child').find('div:nth-child(3) .this_value').val($(this).val());
        }
    });
    $('.new_msg_word').change(function () {
        $('.new_file').parent().parent().find('audio').remove();
        $('.new_file').parent().parent().find('img').remove();
        $('.new_file').parent().parent().find('p').remove();
        var wd = $('.new_msg_word').val().split("\n").join("<br />");
        $('.new_file').parent().parent().append('<p>' + wd + '</p>');

        $('.load_table li:last-child').find('div:nth-child(3) .this_value').val(wd);
    });
    $('.new_msg_add').click(function () {
        $('.sec').removeClass('sec');
        $('.new_file').removeClass('new_file');
        $('.single_msg').fadeOut();
    });
    $(".load_table").on('click', '.delete', function () {
        if (confirm("确定删除?")) {
            $(this).parent().parent().remove();
        }
    });

    $(".load_table").on('click', '.l_d p', function () {
        var div = $(this).parent();
        var _val = $(this).html().split("<br>").join("\n");
        $(this).replaceWith('<textarea class="this_msg form-control">' + _val + '</textarea>');
        div.find('.this_msg').focus();
    });
    $(".load_table").on('blur', '.this_msg', function () {
        var div = $(this).parent();
        var new_val = $(this).val().split("\n").join("<br />");
        $(this).replaceWith('<p>' + new_val + '</p>');
        div.find('.this_value').val(new_val);
    });






    var this_input, this_li, this_input, this_value;
    $(".load_table").on('click', '.url_change', function () {
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 2000);
            $('.edit_msg_file').html("<i class='icon-cloud-upload'></i> 上传文件");
            $('.edit_msg_file_url').val('');

            this_index = $(this).parents('li').index();
            this_li = $(this).parents('li');
            this_input = this_li.find('.this_value');
            this_value = this_input.val();
            this_type = this_input.attr('type');
            this_li.find('.this_value').addClass('old');
            this_li.find('.l_d .hide').append('<input class="this_value new" type="file" value="" name="">');
            var _value = '';
            if (this_type == 'file') {} else if (this_type == 'hidden') {
                var _h = this_value.substr(0, 4);
                if (_h == 'http') {
                    _value = this_value;
                } else {
                    _value = server_course + this_value;
                }
            }
            $('.edit_msg_file_url').val(_value);
            $('.single_edit').fadeIn();
        }
    });
    $('.edit_msg_file').click(function () {
        if (_lock > 0) {
            return false;
        } else {
            _lock = 1;
            setTimeout(function () {
                _lock = 0;
            }, 1000);
            this_li.find('.l_d .new').trigger('click');
            $('.edit_msg_file_url').val('');

        }
    });
    $(".load_table").on('change', '.new', function () {
        var data_type = this_li.find('.l_d').attr('data-type');
        var _this = $(this).val();
        var _suffix = _this.substr(_this.lastIndexOf("."));
        if (this_li.find('.new')[0].files[0] !== undefined) {
            if (data_type == 2) {
                if (_suffix !== '.mp3' && _suffix !== '.MP3' && _suffix !== '.wav' && _suffix !== '.WAV') {
                    alert('请选择正确格式的音频文件!(mp3、wav)');
                    return false;
                }
            } else if (data_type == 3) {
                if (_suffix !== '.jpg' && _suffix !== '.JPG' && _suffix !== '.jpeg' && _suffix !== '.JPEG' && _suffix !== '.png' && _suffix !== '.PNG' && _suffix !== '.gif' && _suffix !== '.GIF') {
                    alert('请选择正确格式的图片文件!(jpg、jpeg、gif、png)');
                    return false;
                }
            }
            $('.edit_msg_file').html("<i class='icon-check'></i> 已选择");
            this_li.find('.img_in_table').attr('src', URL.createObjectURL(this_li.find('.new')[0].files[0]));
            this_li.find('.audio_36').attr('src', URL.createObjectURL(this_li.find('.new')[0].files[0]));

        } else {
            $('.edit_msg_file').html("<i class='icon-cloud-upload'></i> 上传文件");
            this_li.find('.img_in_table').attr('src', '');
            this_li.find('.audio_36').attr('src', '');
        }
    });
    $('.edit_msg_file_url').blur(function () {
        this_li.find('.new').attr('type', 'hidden').val($(this).val());
        this_li.find('.img_in_table').attr('src', $(this).val());
        this_li.find('.audio_36').attr('src', $(this).val());
    });
    $('.edit_msg_submit').click(function () {
        if (this_li.find('.new').val() == '') {
            alert('请选择文件或填写链接');
            return false;
        } else {
            this_li.find('.old').remove();
            this_li.find('.new').removeClass('new');
            $('.single_edit').fadeOut();
        }
    });
    $('.edit_msg_cancel').click(function () {
        this_li.find('.new').remove();
        if (this_type == 'hidden') {
            var _h = this_value.substr(0, 4);
            if (_h == 'http') {
                _v = this_value;
            } else {
                _v = server_course + this_value;
            }
            this_li.find('.img_in_table').attr('src', _v);
            this_li.find('.audio_36').attr('src', _v);
        } else if (this_type == 'file') {
            this_li.find('.img_in_table').attr('src', URL.createObjectURL(this_li.find('.this_value')[0].files[0]));
            this_li.find('.audio_36').attr('src', URL.createObjectURL(this_li.find('.this_value')[0].files[0]));
        }
        $('.single_edit').fadeOut();
    });
    $('.list_clear').click(function(){
        if(confirm('确定清空？')){
            $('.load_table').html('');
        }else{
            return false;
        }
    });
});