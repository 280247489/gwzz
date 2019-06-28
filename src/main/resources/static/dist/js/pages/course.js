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
    var url = api_back + '/course/cms/list';
    var url_album_list = api_back + '/album/cms/list';
    var url_online = api_back + '/course/cms/online';
    var url_live_untied = api_back + '/course/cms/live_untied';
    var lk = 0;
    var creater = '<option value="">--更新人--</option>';
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    $.each(user_list, function (i, n) {
        creater += '<option value="' + n.id + '">' + n.name + '</option>';
    });
    $('#course_search_creater').html(creater);
    var action = $_GET['action'];
    var _album_id = $_GET['id'];
    if (action == 'album' && _album_id !== '' && _album_id !== undefined) {
        $('.course_add_btn').show().click(function () {
            window.location.href = "./courseAdd.html?action=add&album=" + _album_id;
        });
        $('#course_search_album').hide();
    }
    var album_list = sessionStorage.getItem('album_list');
    album_list = JSON.parse(album_list);
    var _option_album = '<option value="">选择专辑</option>';
    if (album_list == '' || album_list == null) {
        var arr_album_list = {
            page: 0,
            size: '99999'
        }
        $.post(url_album_list, arr_album_list, function (data) {
            console.log(data);
            if (data.code == '0') {
                var content = data.data.data;
                sessionStorage.setItem('album_list', JSON.stringify(content));
                if (content['0'] == '' || content['0'] == undefined) {} else {
                    $.each(content, function (i, n) {
                        _option_album += '<option value="' + n.id + '">' + n.albumName + '</option>';
                    });
                }
                $('#course_search_album').html(_option_album);
            } else {
                _alert_warning(data.msg);
            }
        });
    } else {
        $.each(album_list, function (i, n) {
            _option_album += '<option value="' + n.id + '">' + n.albumName + '</option>';
        });
        $('#course_search_album').html(_option_album);
    }
    var page = 1;
    var per_page = '30';
    arr = {
        page: page - 1,
        size: per_page,
        course_title: '',
        course_update_id: '',
        course_online: '',
        sort_status: 'desc',
        course_type_id: '',
        album_id: _album_id
    }
    $.post(url, arr, function (data) {
        this_page(data, page);
        new Page({
            id: 'pagination',
            pageTotal: data.data.totalPages, //必填,总页数
            pageAmount: per_page, //每页多少条
            dataTotal: data.data.totalElements, //总共多少条数据
            curPage: 1, //初始页码,不填默认为1
            pageSize: 5, //分页个数,不填默认为5
            showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
            showSkipInputFlag: true, //是否支持跳转,不填默认不显示
            getPage: function (page) {
                arr = {
                    page: page - 1,
                    size: per_page,
                    course_title: '',
                    course_update_id: '',
                    course_online: '',
                    sort_status: 'desc',
                    course_type_id: '',
                    album_id: _album_id
                }
                $.post(url, arr, function (data) {
                    this_page(data, page);
                });

            }
        });
    });

    $('.course_search_submit').click(function () {
        var title = $('#course_search_title').val();
        var creater = $('#course_search_creater').val();
        var isOnline = $('#course_search_isOnline').val();
        var orderBy = $('#course_search_orderBy').val();
        var albumId = $('#course_search_album').val();
        if (albumId !== '') {
            this_album_id = albumId;
        } else {
            this_album_id = _album_id;
        }
        arr_search = {
            page: page - 1,
            size: per_page,
            course_title: title,
            course_update_id: creater,
            course_online: isOnline,
            sort_status: orderBy,
            course_type_id: '',
            album_id: this_album_id
        }
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            $('.course_search_submit').removeClass('btn-primary').addClass('btn-default');
            setTimeout(function () {
                lk = 0;
                $('.course_search_submit').removeClass('btn-default').addClass('btn-primary');
            }, 2000);
            $.post(url, arr_search, function (data) {

                this_page_search(data, page);
                new Page({
                    id: 'pagination',
                    pageTotal: data.data.totalPages, //必填,总页数
                    pageAmount: per_page, //每页多少条
                    dataTotal: data.data.totalElements, //总共多少条数据
                    curPage: 1, //初始页码,不填默认为1
                    pageSize: 5, //分页个数,不填默认为5
                    showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
                    showSkipInputFlag: true, //是否支持跳转,不填默认不显示
                    getPage: function (page) {
                        arr_search = {
                            page: page - 1,
                            size: per_page,
                            course_title: title,
                            course_update_id: creater,
                            course_online: isOnline,
                            sort_status: orderBy,
                            course_type_id: '',
                            album_id: _album_id
                        }
                        $.post(url, arr_search, function (data) {
                            this_page(data, page);
                        });

                    }
                });
            });
        }
    });

    function this_page(data, page) {
        var tr = '';
        var c_name;
        var u_name;
        var code = data.code;
        if (code == 0) {
            console.log(data);
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="10">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    this_no = this_page_start + i + 1;
                    $.each(user_list, function (k, v) {
                        if (n.courseCreateId == v.id) {
                            c_name = v.name;
                        }
                        if (n.courseUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    var _img_url = n.courseLogo.substr(0, 4);
                    if (_img_url == 'http') {
                        this_img_url = n.courseLogo;;
                    } else {
                        this_img_url = server_course + n.courseLogo;
                    }
                    if (n.courseOnline == 1) {
                        _is_online = '<span class="text-green">上线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.courseOnline + '" class="btn btn-info btn-sm is_online">下线</a>';
                    } else {
                        _is_online = '<span class="text-gray">下线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.courseOnline + '" class="btn btn-default btn-sm is_online">上线</a>';
                    }
                    if (n.masterId == null || n.masterId == '' || n.masterId == 'null') {
                        is_live_btn = '';
                    } else {
                        is_live_btn = '<a data-val="' + n.masterId + '" class="course_live_master  btn btn-sm btn-primary" href="javascript:void(0);">解绑</a>';
                    }

                    tr +=
                        '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td><img class="article_thumb" src="' + this_img_url + '"/></td>' +
                        '<td>' + n.courseTitle + '</td>' +
                        '<td>' + n.courseReleaseTime + '</td>' +
                        // '<td>推荐</td>' +
                        '<td>' + n.courseUpdateTime + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td class="articleHealth_count"><span>浏览：' + n.courseTotalView + '</span><span>点赞：' + n.courseTotalLike + '</span><span>回复：' + n.courseTotalComment + '</span></td>' +
                        '<td class="article_isonline">' + _is_online + '</td>' +
                        '<td>'+
                        '<a data-id="' + n.id + '" class="articleHealth_edit btn btn-sm btn-primary" href="./courseAdd.html?action=edit&id=' + n.id + '&album=' + n.albumId + '">编辑</a>&nbsp;'+
                        '<a class="course_this_view btn btn-sm btn-primary" href="./commentCourse.html?action=view&id=' + n.id + '">评论</a>'+
                        '&nbsp;' + is_online_btn + '&nbsp;' + is_live_btn +
                        '</td>' +
                        '</tr>';
                });
            }
            $('.course_list tbody').html(tr);
        }
    }

    function this_page_search(data, page) {
        var tr_search = '';
        var code = data.code;
        var c_name;
        var u_name;
        if (code == 0) {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr_search += '<tr><td colspan="10">暂无数据</td></tr>';
            } else {

                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {

                    this_no = this_page_start + i + 1;
                    $.each(user_list, function (k, v) {
                        if (n.courseCreateId == v.id) {
                            c_name = v.name;
                        }
                        if (n.courseUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    var _img_url = n.courseLogo.substr(0, 4);
                    if (_img_url == 'http') {
                        this_img_url = n.courseLogo;
                    } else {
                        this_img_url = server_course + n.courseLogo;
                    }
                    if (n.courseOnline == 1) {
                        _is_online = '<span class="text-green">上线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.courseOnline + '" class="btn btn-info btn-sm is_online">下线</a>';
                    } else {
                        _is_online = '<span class="text-gray">下线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.courseOnline + '" class="btn btn-default btn-sm is_online">上线</a>';
                    }
                    if (n.masterId == null || n.masterId == '' || n.masterId == 'null') {
                        is_live_btn = '';
                    } else {
                        is_live_btn = '<a data-val="' + n.masterId + '" class="course_live_master  btn btn-sm btn-primary" href="javascript:void(0);">解绑</a>';
                    }
                    tr_search +=
                        '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td><img class="article_thumb" src="' + this_img_url + '"/></td>' +
                        '<td>' + n.courseTitle + '</td>' +
                        '<td>' + n.courseReleaseTime + '</td>' +
                        // '<td>推荐</td>' +
                        '<td>' + n.courseUpdateTime + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td class="articleHealth_count"><span>浏览：' + n.courseTotalView + '</span><span>点赞：' + n.courseTotalLike + '</span><span>回复：' + n.courseTotalComment + '</span></td>' +
                        '<td class="article_isonline">' + _is_online + '</td>' +
                        '<td>' +
                        '<a data-id="' + n.id + '" class="articleHealth_edit btn btn-primary btn-sm" href="./courseAdd.html?action=edit&id=' + n.id + '&album=' + n.albumId + '">编辑</a>&nbsp;' +
                        '<a class="course_this_view btn btn-sm btn-primary" href="./commentCourse.html?action=view&id=' + n.id + '">评论</a>'+
                        '&nbsp;' + is_online_btn + '&nbsp;' + is_live_btn +
                        '</td>' +
                        '</tr>';
                });
            }
            $('.course_list tbody').html(tr_search);
        }
    }

    $('.course_list').on('click', '.is_online', function () {
        var status_curr = $(this).attr('data-type');
        var tr = $(this).parents('tr');
        var span = $(this);
        var status = '0';
        if (status_curr == '1') {
            status = '0';
        } else {
            status = '1';
        }
        var arr_status = {
            id: $(this).parent().parent().attr('data-id'),
            online: status,
            operator_id: user.id
        }
        $.post(url_online, arr_status, function (data) {
            if (data.code == '0') {
                span.attr('data-type', data.data);
                if (data.data == '0') {
                    tr.find('.article_isonline span').html('下线').removeClass('text-green').addClass('text-gray');
                    span.removeClass('btn-info').addClass('btn-default');
                    span.html('上线');

                } else if (data.data == '1') {
                    tr.find('.article_isonline span').html('上线').removeClass('text-gray').addClass('text-green');
                    span.removeClass('btn-default').addClass('btn-info');
                    span.html('下线');
                }
            } else {
                _alert_warning(data.msg);
            }
        });
    });
    $('.course_list').on('click', '.course_live_master', function () {
        var _tr = $(this).parents('tr');
        var _btn = $(this);
        var _course_id = _tr.attr('data-id');
        var _master_id = $(this).attr('data-val');
        var arr_live_untied = {
            course_id:_course_id,
            master_id:_master_id,
            operator_id:user.id
        }
        console.log(arr_live_untied);

        $.post(url_live_untied, arr_live_untied, function (data) {
            if(data.code == '0'){
                $modal({
                    type: 'alert',
                    icon: 'success',
                    timeout: 3000,
                    title: '成功',
                    content: '解绑成功',
                    top: 300,
                    center: true,
                    transition: 300,
                    closable: true,
                    mask: true,
                    pageScroll: true,
                    width: 300,
                    maskClose: true,
                    callBack: function () {
                        _btn.remove();
                    }
                });
            }
        });
    });
    // $('.course_list').on('click', '.alert_url', function () {
    //     var _url = 'http://hdqd.houaihome.com/zhibo/index.php?id=' + $(this).attr('data-id');
    //     $modal({
    //         type: 'alert',
    //         icon: 'info',
    //         timeout: 60000,
    //         title: '生成链接',
    //         content: _url,
    //         top: 300,
    //         center: true,
    //         transition: 300,
    //         closable: true,
    //         mask: true,
    //         pageScroll: true,
    //         width: 400,
    //         maskClose: false,
    //         callBack: function () {}
    //     });
    // });
    // $('.course_list').on('click', '.articleHealth_edit', function () {
    //     var id = $(this).attr('data-id');
    //     window.location.href = "./courseAdd.html?action=edit&id=" + id;
    // });

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