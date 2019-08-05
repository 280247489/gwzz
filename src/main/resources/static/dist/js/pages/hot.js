jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var server_course = sessionStorage.getItem('server_course');
    var url_list = api_back + '/hotSearch/cms/list';
    var url_add = api_back + '/hotSearch/cms/add';
    var url_edit = api_back + '/hotSearch/cms/update';
    var url_online = api_back + '/hotSearch/cms/online';
    var url_remove = api_back + '/hotSearch/cms/remove';
    var lk = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    $('.hot_operatorId').val(user.id);
    var arr_list = {
        keyWord: '',
        operatorId: ''
    }

    $.ajax({
        url: url_list,
        type: 'POST',
        data: arr_list,
        async: true,
        cache: false,
        dataType: "json",
        timeout: 50000,
        beforeSend: function (data) {
            loading();
        },
        success: function (data) {
            to_page(data);
        },
        complete: function () {
            loading_end();
        },
        error: function (data) {
            _alert_warning(data.msg);
        }
    }, "json");

    function to_page(data) {
        console.log(data);
        var code = data.code;
        var tr = '';
        if (code == 0) {
            var content = data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="7">暂无数据</td></tr>';
                $('#pagination').hide();
            } else {
                $('#pagination').show();
                this_page_start = 0;
                $.each(content, function (i, n) {
                    var _status = '';
                    var _operatorName = '';
                    this_no = this_page_start + i + 1;
                    if (n.status == '1') {
                        _status = '<span class="text-green">上线</span>';
                        _status_btn = '<a href="javascript:void(0);" class="btn btn-info btn-sm is_online">下线</a>'
                    } else {
                        _status = '<span class="text-gray">下线</span>';
                        _status_btn = '<a href="javascript:void(0);" class="btn btn-default btn-sm is_online">上线</a>'
                    }
                    $.each(user_list, function (k, v) {
                        if (n.operatorId == v.id) {
                            _operatorName = v.name;
                        }
                    });
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td class="_hot_words" data-val="' + n.keyWord + '">' + n.keyWord + '</td>' +
                        '<td class="_hot_sorts" data-val="' + n.sort + '">' + n.sort + '</td>' +
                        '<td class="_hot_status" data-val="' + n.status + '">' + _status + '</td>' +
                        '<td>' + _operatorName + '</td>' +
                        '<td>' + n.lastUpdateTime + '</td>' +
                        '<td><a class="hot_edit btn btn-primary btn-sm" href="javascript:void(0);">编辑</a>&nbsp;' + _status_btn + '&nbsp;<a class="hot_del btn btn-primary btn-sm" href="javascript:void(0);">删除</a></td>' +
                        '</tr>';
                });
            }
            $('#hot_list tbody').html(tr);
        }
    }

    $('.hot_search_submit').click(function(){
        var arr_search = {
            keyWord: $('#hot_search_words').val(),
            operatorId: $('#hot_search_user').val()
        }
        $.ajax({
            url: url_list,
            type: 'POST',
            data: arr_search,
            async: true,
            cache: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {
                loading();
            },
            success: function (data) {
                to_page(data);
            },
            complete: function () {
                loading_end();
            },
            error: function (data) {
                _alert_warning(data.msg);
            }
        }, "json");

    });

    $('.hot_list').on('click', '.hot_edit', function () {
        $('#hot_add_choose').attr('data-action', 'edit');
        $('.hot_add').show();
        var _tr = $(this).parents('tr');

        $('.hot_upd_id').val(_tr.attr('data-id'));
        $('.hot_add_keyWord').val(_tr.find('._hot_words').attr('data-val'));
        $('.hot_add_sort').val(_tr.find('._hot_sorts').attr('data-val'));

    });
    $('.hot_add_btn').click(function () {
        $('#hot_add_choose').attr('data-action', 'add');
        $('.hot_add').show();
    });
    $('.new_hot_cancel').click(function () {
        cancel();
    });

    function cancel() {
        $("#hot_add_choose")[0].reset();
        $('#hot_add_choose').attr('data-action', '');
        $('.hot_add').hide();
    }


    $('.hot_list').on('click', '.is_online', function () {
        var _tr = $(this).parents('tr');
        //var _now = _tr.find('._hot_status').attr('data-val');
        var arr_online = {
            id: _tr.attr('data-id')
        }
        $.ajax({
            url: url_online,
            type: 'POST',
            data: arr_online,
            async: true,
            cache: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
            success: function (data) {
                console.log(data);
                if (data.code == '0') {
                    _tr.find('_hot_status').attr('data-val', data.data);
                    if (data.data == '0') {
                        _tr.find('._hot_status').html('<span class="text-gray">下线</span>');
                        _tr.find('.is_online').html('上线').removeClass('btn-info').addClass('btn-default');
                    } else if (data.data == '1') {
                        _tr.find('._hot_status').html('<span class="text-green">上线</span>');
                        _tr.find('.is_online').html('下线').removeClass('btn-default').addClass('btn-info');
                    }
                } else {
                    _alert_warning('修改状态失败！');
                }
            },
            complete: function () {},
            error: function (data) {
                _alert_warning(data.msg);
            }
        }, "json");
    });
    $('.hot_list').on('click', '.hot_del', function () {
        var _tr = $(this).parents('tr');
        var arr_remove = {
            id: _tr.attr('data-id')
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '请小心操作',
            content: '确认删除？',
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
                $.ajax({
                    url: url_remove,
                    type: 'POST',
                    data: arr_remove,
                    async: true,
                    cache: false,
                    dataType: "json",
                    timeout: 50000,
                    beforeSend: function (data) {
                    },
                    success: function (data) {
                        if (data.code == '0') {
                            _tr.remove();
                            $modal({
                                type: 'alert',
                                icon: 'success',
                                timeout: 3000,
                                title: '成功',
                                content: '删除成功！',
                                top: 300,
                                center: true,
                                transition: 300,
                                closable: true,
                                mask: true,
                                pageScroll: true,
                                width: 300,
                                maskClose: true,
                                callBack: function () {
                                }
                            });
                        } else {
                            _alert_warning(data.msg);
                        }
                    },
                    complete: function () {},
                    error: function (data) {
                        _alert_warning(data.msg);
                    }
                }, "json");
            }
        });
    
    });

    $('.new_hot_add').click(function () {
        var _action = $('#hot_add_choose').attr('data-action');
        if (_action == 'add') {
            _action_url = url_add;
        } else if (_action == 'edit') {
            _action_url = url_edit;
        } else {
            return false;
        }
        var formData = new FormData($("#hot_add_choose")[0]);
        $.ajax({
            url: _action_url,
            type: 'POST',
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
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
                            window.location.reload();
                        }
                    });
                } else {
                    _alert_warning(data.msg);
                }
            },
            complete: function () {
                cancel();
            },
            error: function (data) {
                _alert_warning(data.msg);
            }
        }, "json");
    });
});