jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var url_list = api_back + '/album/cms/list';
    var url_online = api_back + '/album/cms/online';
    var url_isHomePage = api_back + '/album/cms/isHomePage';
    var url_isEnd = api_back + '/album/cms/isEnd';
    var url_sort = api_back + '/album/cms/sort';
    var server_course = sessionStorage.getItem('server_course');
    var lk = 0;
    var page = 1;
    var per_page = '30';
    var user = sessionStorage.getItem('user');
    var sort_cache = '';
    user = JSON.parse(user);
    var arr_list = {
        page: page - 1,
        size: per_page
    }
    $.post(url_list, arr_list, function (data) {
        console.log(data);
        if (data.code == '0') {
            var content = data.data.data;
            sessionStorage.setItem('album_list', JSON.stringify(content));
            var tr = '';
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="10">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    this_no = this_page_start + i + 1;

                    if (n.albumIsOnline == '0') {
                        _online = '<span class="text-gray">下线</span>';
                        _online_btn = '<a href="javascript:void(0);" class="album_online btn btn-sm btn-default">上线</a>';
                    } else if (n.albumIsOnline == '1') {
                        _online = '<span class="text-green">上线</span>';
                        _online_btn = '<a href="javascript:void(0);" class="album_online btn btn-sm btn-info">下线</a>';
                    }
                    if (n.albumIsEnd == '0') {
                        _IsEnd = '<span class="text-green">未完结</span>';
                        _IsEnd_btn = '<a href="javascript:void(0);" class="album_end btn btn-sm btn-info">完结</a>';
                    } else if (n.albumIsEnd == '1') {
                        _IsEnd = '<span class="text-gray">已完结</span>';
                        _IsEnd_btn = '<a href="javascript:void(0);" class="album_end btn btn-sm btn-default">取消</a>';
                    }
                    if (n.albumIsHomePage == '0') {
                        _IsHomePage = '<span class="text-gray">未推荐</span>';
                        _IsHomePage_btn = '<a href="javascript:void(0);" class="album_homepage btn btn-sm btn-default">推荐</a>';
                    } else if (n.albumIsHomePage == '1') {
                        _IsHomePage = '<span class="text-green">已推荐</span>';
                        _IsHomePage_btn = '<a href="javascript:void(0);" class="album_homepage btn btn-sm btn-info">取消</a>';
                    }
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td><img class="article_thumb img_prev" src="' + server_course + n.albumLogo + '"/></td>' +
                        '<td class="albumSort" data-val="' + n.albumSort + '"><span>' + n.albumSort + '</span></td>' +
                        '<td>' + n.albumName + '</td>' +
                        '<td>' + n.albumCourseSum + '</td>' +
                        '<td>' + n.albumTotalView + '</td>' +
                        '<td class="albumIsOnline" data-val="' + n.albumIsOnline + '">' + _online + '</td>' +
                        '<td class="albumIsHomePage" data-val="' + n.albumIsHomePage + '">' + _IsHomePage + '</td>' +
                        '<td class="albumIsEnd" data-val="' + n.albumIsEnd + '">' + _IsEnd + '</td>' +
                        '<td>' +
                        '<a href="albumAdd.html?action=edit&id=' + n.id + '" class="album_edit btn btn-sm btn-primary">编辑</a>' + '&nbsp;' +
                        '<a href="course.html?action=album&id=' + n.id + '" class="album_view btn btn-sm btn-primary">列表</a>' +
                        '&nbsp;' + _online_btn + '&nbsp;' + _IsHomePage_btn + '&nbsp;' + _IsEnd_btn +
                        '</td>' +
                        '</tr>';
                });
                $('.album_list tbody').html(tr);
            }
        } else {
            _alert_warning(data.msg);
        }
    });

    $(".album_list").on('click', '.albumSort span', function () {
        var _td = $(this).parent();
        sort_cache = _td.attr('data-val');
        _td.html('<input type="number" min="1" max="100" class="sort_edit form-control" value="' + sort_cache + '">');
        _td.find('.sort_edit').focus();
    });
    $(".album_list").on('blur', '.sort_edit', function () {
        var new_sort = $(this).val();
        var _id = $(this).parents('tr').attr('data-id');
        var _td = $(this).parent();
        if (new_sort > 100 || new_sort < 1) {
            _td.attr('data-val', sort_cache).html('<span>' + sort_cache + '</span>');
            _alert_warning('排序为1-100的数字');
        } else {
            var arr_sort = {
                id: _id,
                album_sort: new_sort,
                operator_id: user.id
            }
            $.post(url_sort, arr_sort, function (data) {
                if (data.code == '0') {
                    _td.attr('data-val', new_sort).html('<span>' + new_sort + '</span>');
                } else {
                    _td.attr('data-val', sort_cache).html('<span>' + sort_cache + '</span>');
                    _alert_warning(data.msg);
                }
            });

        }
    });

    $('.album_list').on('click', '.album_homepage', function () {
        var btn = $(this);
        var _tr = $(this).parents('tr');
        var status_curr = _tr.find('.albumIsHomePage').attr('data-val');
        var _new_status = '0';
        if (status_curr == '1') {
            _new_status = '0';
        } else {
            _new_status = '1';
        }
        var arr_isHomePage = {
            id: $(this).parent().parent().attr('data-id'),
            album_is_home_page: _new_status,
            operator_id: user.id
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '请小心操作',
            content: '确认修改？',
            transition: 300,
            closable: true,
            mask: true,
            top: 400,
            center: true,
            pageScroll: false,
            width: 500,
            maskClose: false,
            cancelText: '取消',
            confirmText: '确认',
            cancel: function (close) {
                console.log('取消');
                close();
            },
            confirm: function (close) {
                close();
                $.post(url_isHomePage, arr_isHomePage, function (data) {
                    if (data.code == '0') {
                        _tr.find('.albumIsHomePage').attr('data-val', _new_status);
                        if (_new_status == '0') {
                            _tr.find('.albumIsHomePage span').removeClass('text-green').addClass('text-gray').html('未推荐');
                            btn.removeClass('btn-info').addClass('btn-default');
                            btn.html('推荐');
                        } else if (_new_status == '1') {
                            _tr.find('.albumIsHomePage span').removeClass('text-gray').addClass('text-green').html('已推荐');
                            btn.removeClass('btn-default').addClass('btn-info');
                            btn.html('取消');
                        }
                    } else {}
                });

            }
        });
    });

    $('.album_list').on('click', '.album_end', function () {
        var btn = $(this);
        var _tr = $(this).parents('tr');
        var end_curr = _tr.find('.albumIsEnd').attr('data-val');
        var _new_end = '0';
        if (end_curr == '1') {
            _new_end = '0';
        } else {
            _new_end = '1';
        }
        var arr_isEnd = {
            id: $(this).parent().parent().attr('data-id'),
            album_is_end: _new_end,
            operator_id: user.id
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '请小心操作',
            content: '确认修改？',
            transition: 300,
            closable: true,
            mask: true,
            top: 400,
            center: true,
            pageScroll: false,
            width: 500,
            maskClose: false,
            cancelText: '取消',
            confirmText: '确认',
            cancel: function (close) {
                console.log('取消');
                close();
            },
            confirm: function (close) {
                close();
                $.post(url_isEnd, arr_isEnd, function (data) {
                    if (data.code == '0') {
                        _tr.find('.albumIsEnd').attr('data-val', _new_end);
                        if (_new_end == '0') {
                            _tr.find('.albumIsEnd span').removeClass('text-gray').addClass('text-green').html('未完结');
                            btn.removeClass('btn-default').addClass('btn-info');
                            btn.html('完结');
                        } else if (_new_end == '1') {
                            _tr.find('.albumIsEnd span').removeClass('text-green').addClass('text-gray').html('已完结');
                            btn.removeClass('btn-info').addClass('btn-default');
                            btn.html('取消');
                        }
                    } else {}
                });

            }
        });
    });



    $('.album_list').on('click', '.album_online', function () {
        var btn = $(this);
        var _tr = $(this).parents('tr');
        var online_curr = _tr.find('.albumIsOnline').attr('data-val');
        var _new_online = '0';
        if (online_curr == '1') {
            _new_online = '0';
        } else {
            _new_online = '1';
        }
        var arr_online = {
            id: $(this).parent().parent().attr('data-id'),
            album_is_online: _new_online,
            operator_id: user.id
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '请小心操作',
            content: '确认修改？',
            transition: 300,
            closable: true,
            mask: true,
            top: 400,
            center: true,
            pageScroll: false,
            width: 500,
            maskClose: false,
            cancelText: '取消',
            confirmText: '确认',
            cancel: function (close) {
                console.log('取消');
                close();
            },
            confirm: function (close) {
                close();
                $.post(url_online, arr_online, function (data) {
                    if (data.code == '0') {
                        _tr.find('.albumIsOnline').attr('data-val', _new_online);
                        if (_new_online == '0') {
                            _tr.find('.albumIsOnline span').removeClass('text-green').addClass('text-gray').html('下线');
                            btn.removeClass('btn-info').addClass('btn-default');
                            btn.html('上线');
                        } else if (_new_online == '1') {
                            _tr.find('.albumIsOnline span').removeClass('text-gray').addClass('text-green').html('上线');
                            btn.removeClass('btn-default').addClass('btn-info');
                            btn.html('下线');
                        }
                    } else {}
                });

            }
        });
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