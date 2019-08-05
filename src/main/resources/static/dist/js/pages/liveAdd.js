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
    var url_add = api_back + '/liveMaster/cms/add';
    var url_detail = api_back + '/liveMaster/cms/detail';
    var url_update = api_back + '/liveMaster/cms/update';
    var lk = 0;
    var _lock = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var action = $_GET['action'];
    var id = $_GET['id'];
    $('.live_updater').val(user.id);

    if (action == 'edit' && id !== '' && id !== undefined) {
        $('.course_update_id').val(user.id);
        $('.edit_id').val(id);
        $('.article_submit').html('提交修改');
        $('#zhibo_msg_list').attr('data-id', id);
        $('#courseId').val(id);
        to_live(id);

        function to_live(id) {
            var arr_detail = {
                id: id
            }
            var li = '';
            loading();
            $.post(url_detail, arr_detail, function (data) {
                if (data.code == '0') {
                    console.log(data);
                    $('.live_id').val(id);
                    $('.live_number').val(data.data.master.liveNumber);
                    $('.live_title').val(data.data.master.liveMasterName);
                    $('.live_describe').val(data.data.master.liveMasterDescribe);
                    $('.live_time_start').val(data.data.master.liveMasterStarttime);
                    $('.live_time_end').val(data.data.master.liveMasterEndtime);
                    var content = data.data.slave;
                    var _type;
                    var _content;
                    $.each(content, function (i, n) {
                        var prew = '';
                        var _time = '';
                        var _readonly = 'readonly="readonly"';
                        var _logo = '';
                        if (n.liveSlaveType == '1') {
                            _content = n.liveSlaveWords;
                            _type = '文字';
                            prew = '<p>' + n.liveSlaveWords + '</p>';
                        } else if (n.liveSlaveType == '2') {
                            _readonly = '';
                            var http = n.liveSlaveAudio.substr(0, 4);
                            var _title = getFileName1(n.liveSlaveAudio);
                            if (http == 'http') {
                                prew = '<audio src="' + n.liveSlaveAudio + '" title="' + _title + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><span class="file_name">' + _title + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                            } else {
                                prew = '<audio src="' + server_course + n.liveSlaveAudio + '" title="' + _title + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><span class="file_name" title>' + _title + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                            }
                            _type = '音频';
                            _content = n.liveSlaveAudio;
                            _time = n.liveSlaveAudioTime;
                        } else if (n.liveSlaveType == '3') {
                            var http = n.liveSlaveImgurl.substr(0, 4);
                            var _title = getFileName1(n.liveSlaveImgurl);
                            if (http == 'http') {
                                prew = '<img class="img_in_table img_prev" src="' + n.liveSlaveImgurl + '" title="' + _title + '"><span class="file_name">' + _title + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                            } else {
                                prew = '<img class="img_in_table img_prev" src="' + server_course + n.liveSlaveImgurl + '" title="' + _title + '"><span class="file_name">' + _title + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>';
                            }
                            _type = '图片';
                            _content = n.liveSlaveImgurl;
                        }

                        // if(n.liveSlaveLogo == '' || n.liveSlaveLogo == null){
                        //     _logo = api_back + '/dist/img/avatar.jpg';
                        // }else{
                        //     _logo = n.liveSlaveLogo;
                        // }
                        li += '<li data-order="" data-id="' + n.id + '" class="excelcontent">' +
                            '<div class="l_a">' +
                            '<input type="hidden" class="courseId" value="" name="">' +
                            '<input type="text" class="speaker form-control" value="' + n.liveSlaveNickname + '" name="name">' +
                            '</div>' +
                            '<div class="l_b"><img class="l_logo img_prev" src="'+ n.liveSlaveLogo +'" ><input type="hidden" class="l_logo_url" value="' + n.liveSlaveLogo + '" name="" ></div>' +
                            '<div class="l_c"  data-type="' + n.liveSlaveType + '">' + _type + '</div>' +
                            '<div class="l_d" data-type="' + n.liveSlaveType + '">' + prew +
                            '<div class="hide">' +
                            '<input class="this_type" type="hidden" value="" name="">' +
                            '<input class="this_value" type="hidden" value="' + _content + '" name="">' +
                            '</div></div>' +
                            '<div class="l_e"><input class="audio_times form-control" type="number" value="' + _time + '" name="times" ' + _readonly + '></div>' +
                            '<div class="l_f">' +
                            '<button type="button" class="delete btn btn-primary" href="javascript:void(0);">删除</button>' +
                            '</div>' +
                            '</li>';
                    });
                    $('.load_table').html(li);
                    $('.load_table').find('audio').each(function () {
                        var _li = $(this).parents('li');
                        var _audio = $(this).get(0);
                        _audio.addEventListener("canplay", function () {
                            time = Math.round(_audio.duration);
                            if (!isNaN(time)) {
                                _li.find('.audio_times').val(time);
                            }
                        });
                    });

                } else {
                    _alert_warning(data.msg);
                }
                loading_end();
            });
        }
    } else {
        $('.course_create_id').val(user.id);
    }


    $('#zhibo_submit').click(function () {
        if (action == 'edit') {
            var _url = url_update;
        } else {
            var _url = url_add;
        }
        $('.load_table li').each(function () {
            var tm = '0';
            var time = 0;
            var this_no = $(this).index();
            $(this).attr('data-order', this_no);
            var this_id = $('#courseId').val();
            var this_type = $(this).find('.l_d').attr('data-type');
            $(this).find('.l_a select').attr('name', 'extList[' + this_no + '].name');
            $(this).find('.l_a input').attr('name', 'extList[' + this_no + '].name');
            $(this).find('.l_a .courseId').attr('name', 'extList[' + this_no + '].courseId').val(this_id);
            $(this).find('.l_b input').attr('name', 'extList[' + this_no + '].userLogo');
            $(this).find('div.l_d input.this_type').attr('name', 'extList[' + this_no + '].type').val(this_type);
            if (this_type == '1') {
                tm = 'words';
            } else if (this_type == '2') {
                $(this).find('.audio_times').attr('name', 'extList[' + this_no + '].times');
                if ($(this).find('.l_d .this_value').attr('type') == 'file') {
                    tm = 'audioFile';
                } else {
                    tm = 'audioUrl';
                }
            } else if (this_type == '3') {
                if ($(this).find('.l_d .this_value').attr('type') == 'file') {
                    tm = 'imgFile';
                } else {
                    tm = 'imgUrl';
                }
            }
            $(this).find('.l_d .this_value').attr('name', 'extList[' + this_no + '].' + tm);
        });
        $('.list_remove .list_remove_single').each(function () {
            var _this_no = $(this).index();
            $(this).attr('name', 'removeList[' + _this_no + '].slaveId');
        });
        //return false;
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 3300);
            setTimeout(function () {
                var formData = new FormData($("#zhibo_msg_list")[0]);
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
                    beforeSend: function () {
                        if ($('.live_number').val().trim() == '') {
                            //_alert_warning('');
                            $modal({
                                type: 'alert',
                                icon: 'warning',
                                timeout: 3000,
                                title: '警告',
                                content: '期数不能为空！',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                    $('.live_number').focus();
                                }
                            });
                            return false;
                        } else if ($('.live_title').val().trim() == '') {
                            //_alert_warning('');
                            $modal({
                                type: 'alert',
                                icon: 'warning',
                                timeout: 3000,
                                title: '警告',
                                content: '标题不能为空！',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                    $('.live_title').focus();
                                }
                            });
                            return false;
                        } else if ($('.live_describe').val().trim() == '') {
                            $modal({
                                type: 'alert',
                                icon: 'warning',
                                timeout: 3000,
                                title: '警告',
                                content: '副标题不能为空！',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                    $('.live_describe').focus();
                                }
                            });
                            return false;
                        }
                        // else if ($('.live_time_start').val().trim() == '') {
                        //     $modal({
                        //         type: 'alert',
                        //         icon: 'warning',
                        //         timeout: 3000,
                        //         title: '警告',
                        //         content: '请选择直播时间！',
                        //         top: 300,
                        //         center: true,
                        //         transition: 300,
                        //         closable: true,
                        //         mask: true,
                        //         pageScroll: true,
                        //         width: 300,
                        //         maskClose: true,
                        //         callBack: function () {
                        //             $('.live_time').focus();
                        //         }
                        //     });
                        //     return false;
                        // } 
                        else {}
                    },
                    success: function (data) {
                        console.log(data);
                        if (data.code == '0') {
                            $modal({
                                type: 'alert',
                                icon: 'success',
                                timeout: 3000,
                                title: '成功',
                                content: '提交成功',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                    window.location.href = "liveList.html";
                                }
                            });
                        } else {
                            _alert_warning(data.msg);
                        }
                    },
                    error: function (data) {
                        console.log(data);
                        _alert_warning(data.msg);
                    }
                }, "json");
            }, 300);
        }


    });

    $('#excel_file').click(function () {
        $('#excel-file').trigger('click');
    });
    $('.new_msg_file').click(function () {
        $('.new_msg_file_url').val('');
        $(".load_table li:last-child").find('.l_d .this_value').attr('type', 'file').trigger('click');
    });
    $('.new_msg_file_url').blur(function () {
        $(".load_table li:last-child").find('.l_d .this_value').attr('type', 'hidden');
    });
    $(".load_table").on('change', '.new_file', function () {
        var _li = $(this).parents('li');
        var _this = $(this).val();
        var _this_name = getFileName(_this);
        var suffix_this = _this.substr(_this.lastIndexOf("."));
        var _type = $(this).parent().parent().attr('data-type');
        if ($(".new_file")[0].files[0] !== undefined) {
            if (_type == '2') {
                if (suffix_this !== '.mp3' && suffix_this !== '.MP3' && suffix_this !== '.wav' && suffix_this !== '.WAV' && suffix_this !== '.amr' && suffix_this !== '.AMR') {
                    alert('请选择正确格式的音频文件!(mp3、wav、amr)');
                    return false;
                }
                _li.find('.audio_times').val('0');
                $(".new_msg_file").html("<i class='icon-check'></i> 已选择");
                $(this).parent().parent().find('audio').remove();
                $(this).parent().parent().find('img').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().find('a').remove();
                $(this).parent().parent().append('<audio title="' + _this_name + '" src="' + URL.createObjectURL($(this)[0].files[0]) + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><span class="file_name">' + _this_name + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
                var _audio = _li.find('audio').get(0);
                _audio.addEventListener("canplay", function () {
                    time = Math.round(_audio.duration);
                    if (!isNaN(time)) {
                        _li.find('.audio_times').val(time);
                    }
                });
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
                $(this).parent().parent().append('<img class="img_in_table img_prev" title="' + _this_name + '" src="' + URL.createObjectURL($(this)[0].files[0]) + '" /><span class="file_name">' + _this_name + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            }
        } else {
            $(".new_msg_file").html("<i class='icon-cloud-upload'></i> 上传文件");
        }
    });
    $('.zhibo_msg_add').click(function () {
        $('.new_msg_word').val('');
        var li = '<li data-order="" class="excelcontent">' +
            '<div class="l_a"><input type="hidden" class="courseId" value="" name=""><input type="text" class="speaker form-control" value="李老师" name="name"></div>' +
            '<div class="l_b"><img class="l_logo img_prev" src="" ><input type="hidden" class="l_logo_url" value="" ></div>' +
            '<div class="sec l_c">文字</div>' +
            '<div class="l_d" data-type="1"><p></p>' +
            '<div class="hide"><input class="this_type" type="hidden" value="1" name="" /><input class="new_file this_value" type="hidden" value="" name=""></div>' +
            '</div>' +
            '<div class="l_e"><input class="audio_times form-control" type="number" value="" name="times"></div></div>' +
            '<div class="l_f"><button type="button" class="delete btn btn-primary" href="javascript:void(0);">删除</button>' +
            '</li>';
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
        $(".load_table li:last-child").find('.l_d .this_value').val('');
        $('.new_msg_word').val('');
        $(".new_msg_file").html("<i class='icon-cloud-upload'></i> 上传文件");
        $('.load_table li:last-child').find('.l_d').attr('data-type', $(this).val());
        $('.load_table li:last-child').find('.l_d').find('audio').remove();
        $('.load_table li:last-child').find('.l_d').find('p').remove();
        $('.load_table li:last-child').find('.l_d').find('img').remove();
        $('.load_table li:last-child').find('.l_d').find('a').remove();
        $('.load_table li:last-child').find('.l_d').find('.file_name').remove();
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
    $('.new_msg_logo').change(function () {
        $(".load_table li:last-child .l_logo").attr('src',$(this).val());
        $(".load_table li:last-child .l_logo_url").val($(this).val());
    });

    $('.new_msg_file_url').focus(function () {
        $('.load_table li:last-child').find('.l_d .this_value').attr('type', 'hidden');
    });
    $('.new_msg_file_url').change(function () {
        if ($('.new_file').val() !== '') {} else {
            $('.new_file').val($(this).val());
            var _type = $('.new_msg_type:checked').val();

            if (_type == '2') {
                $('.new_file').parent().parent().find('audio').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().find('img').remove();
                $('.new_file').parent().parent().append('<audio src="' + $(this).val() + '" class="audio_36" controls="controls">浏览器不支持audio标签</audio><span class="file_name">' + getFileName1($(this).val()) + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            } else if (_type == '3') {
                $('.new_file').parent().parent().find('audio').remove();
                $('.new_file').parent().parent().find('img').remove();
                $('.new_file').parent().parent().find('p').remove();
                $('.new_file').parent().parent().append('<img class="img_in_table img_prev" src="' + $(this).val() + '" /><span class="file_name">' + getFileName1($(this).val()) + '</span><a href="javascript:void(0)" class="url_change"><i class="icon-folder-open"></i></a>');
            }
            $('.load_table li:last-child').find('.l_d .this_value').val($(this).val());
        }
    });
    $('.new_msg_word').change(function () {
        $('.new_file').parent().parent().find('audio').remove();
        $('.new_file').parent().parent().find('img').remove();
        $('.new_file').parent().parent().find('p').remove();
        var wd = $('.new_msg_word').val().split("\n").join("<br />");
        $('.new_file').parent().parent().append('<p>' + wd + '</p>');

        $('.load_table li:last-child').find('.l_d .this_value').val(wd);
    });
    $('.new_msg_add').click(function () {
        if($('.new_msg_speaker').val().trim() == ''){
            $modal({
                type: 'alert',
                icon: 'warning',
                timeout: 3000,
                title: '警告',
                content: '请填写发言人',
                top: 300,
                center: true,
                transition: 300,
                closable: true,
                mask: true,
                pageScroll: true,
                width: 300,
                maskClose: true,
                callBack: function () {
                    $('.new_msg_speaker').focus();
                }
            });
            return false;
        } else if($('.new_msg_logo').val().trim() == ''){
            $modal({
                type: 'alert',
                icon: 'warning',
                timeout: 3000,
                title: '警告',
                content: '请填写头像地址',
                top: 300,
                center: true,
                transition: 300,
                closable: true,
                mask: true,
                pageScroll: true,
                width: 300,
                maskClose: true,
                callBack: function () {
                    $('.new_msg_logo').focus();
                }
            });
            return false;
        }else{
            $('.sec').removeClass('sec');
            $('.new_file').removeClass('new_file');
            $('.single_msg').fadeOut();
        }
    });
    $(".load_table").on('click', '.delete', function () {
        var _li = $(this).parents('li');
        var this_id = _li.attr('data-id');
        if (confirm("确定删除?")) {
            if (_li.find('.l_d').attr('data-type') == '2') {
                if (this_id !== '' && this_id !== null && this_id !== undefined) {
                    $('.list_remove').append('<input type="text" name="" value="' + this_id + '" class="list_remove_single">');
                }
            }
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
            this_li.find('.l_d .new').attr('type', 'file').trigger('click');
            $('.edit_msg_file_url').val('');
            if (this_li.find('.l_d').attr('data-type') == '2') {
                this_li.find('.audio_times').val('0');
            } else {
                this_li.find('.audio_times').val('');
            }
        }
    });
    $(".load_table").on('change', '.new', function () {
        var data_type = this_li.find('.l_d').attr('data-type');
        var _this = $(this).val();
        var _title = getFileName(_this);
        var _suffix = _this.substr(_this.lastIndexOf("."));
        if (this_li.find('.new')[0].files[0] !== undefined) {
            if (data_type == 2) {
                if (_suffix !== '.mp3' && _suffix !== '.MP3' && _suffix !== '.wav' && _suffix !== '.WAV' && _suffix !== '.amr' && _suffix !== '.AMR') {
                    alert('请选择正确格式的音频文件!(mp3、wav、amr)');
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
            this_li.find('.file_name').html(_title);

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
        this_li.find('.file_name').html(getFileName1($(this).val()));
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
            this_li.find('.file_name').html(getFileName1(this_value));
            this_li.find('.img_in_table').attr('src', _v);
            this_li.find('.audio_36').attr('src', _v);
        } else if (this_type == 'file') {
            this_li.find('.img_in_table').attr('src', URL.createObjectURL(this_li.find('.this_value')[0].files[0]));
            this_li.find('.audio_36').attr('src', URL.createObjectURL(this_li.find('.this_value')[0].files[0]));
            this_li.find('.file_name').html(getFileName(this_value));
        }
        $('.single_edit').fadeOut();
    });
    $('.list_clear').click(function () {
        if (confirm('确定清空？')) {
            $('.load_table').html('');
        } else {
            return false;
        }
    });
    $('.load_table').on('mouseover','.file_name',function(){
        $(this).attr('title', $(this).html());
    });
    function getFileName(o) {
        var pos = o.lastIndexOf("\\");
        return o.substring(pos + 1);
    }
    function getFileName1(o) {
        var pos = o.lastIndexOf("/");
        return o.substring(pos + 1);
    }

});