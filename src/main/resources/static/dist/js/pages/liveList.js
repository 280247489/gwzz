jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var server_course = sessionStorage.getItem('server_course');
    var url_list = api_back + '/liveMaster/cms/list';
    var url_status = api_back + '/liveMaster/cms/status';
    var url_online = api_back + '/liveMaster/cms/online';
    var lk = 0;
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var option_updater = '<option value="">更新人</option>';
    $.each(user_list, function (i, n) {
        option_updater += '<option value="' + n.id + '">' + n.name + '</option>';
    });
    $('#live_search_updater').html(option_updater);




    var page = 1;
    var per_page = '30';


    arr_list = {
        page: page - 1,
        size: per_page,
        live_master_name: '',
        operator_id: '',
        status: ''
    }
    $.post(url_list, arr_list, function (data) {
        console.log(data);
        if (data.code == '0') {
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
                    _arr_list = {
                        page: page - 1,
                        size: per_page,
                        live_master_name: '',
                        operator_id: '',
                        status: ''
                    }
                    $.post(url_list, _arr_list, function (data) {
                        this_page(data, page);
                    });

                }
            });
        } else {
            _alert_warning(data.msg);
        }
    });

    $('.live_search_submit').click(function () {
        var _live_master_name = $('#live_search_title').val();
        var _operator_id = $('#live_search_updater').val();
        var _status = $('#live_search_isOnline').val();
        arr_list_search = {
            page: page - 1,
            size: per_page,
            live_master_name: _live_master_name,
            operator_id: _operator_id,
            status: _status
        }
        $.post(url_list, arr_list_search, function (data) {
            console.log(data);
            if (data.code == '0') {
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
                        _arr_list_search = {
                            page: page - 1,
                            size: per_page,
                            live_master_name: _live_master_name,
                            operator_id: _operator_id,
                            status: _status
                        }
                        $.post(url_list, _arr_list_search, function (data) {
                            this_page(data, page);
                        });

                    }
                });
            } else {
                _alert_warning(data.msg);
            }
        });
    });

    function this_page(data, page) {
        var tr = '';
        if (data.code == 0) {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="6">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    var _status = '';
                    var _online = '';
                    var u_name = '';
                    var _is_show = '';
                    this_no = this_page_start + i + 1;
                    if (n.liveMasterStatus == '0') {
                        _status = '<span class="text-gray">关闭</span>';
                        _statys_btn = '<a href="javascript:void(0);" class="live_status_edit btn btn-sm btn-default">开启<a>';
                    } else if (n.liveMasterStatus == '1') {
                        _status = '<span class="text-green">开启</span>';
                        _statys_btn = '<a href="javascript:void(0);" class="live_status_edit btn btn-sm btn-info">完毕<a>';
                    } else if (n.liveMasterStatus == '2') {
                        _status = '<span class="text-gray">完毕</span>';
                        _statys_btn = '<a href="javascript:void(0);" class="btn btn-sm disabled btn-default">完毕<a>';
                    }

                    if (n.liveMasterIsOnline == '0') {
                        _online = '<span class="text-gray">下线</span>';
                        _online_btn = '<a href="javascript:void(0);" class="live_online_edit btn btn-sm btn-default">上线<a>';
                    } else if (n.liveMasterIsOnline == '1') {
                        _online = '<span class="text-green">上线</span>';
                        _online_btn = '<a href="javascript:void(0);" class="live_online_edit btn btn-sm btn-info">下线<a>';
                    }
                    $.each(user_list, function (k, v) {
                        if (n.liveMasterUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td>' + n.liveMasterName + '</td>' +
                        '<td class="live_status" data-val="' + n.liveMasterStatus + '">' + _status + '</td>' +
                        '<td class="live_online" data-val="' + n.liveMasterIsOnline + '">' + _online + '</td>' +
                        '<td>' + n.liveMasterUpdateTime + '</td>' +
                        '<td class="live_updater" data-val="' + n.liveMasterUpdateId + '">' + u_name + '</td>' +
                        '<td><a href="liveAdd.html?action=edit&id=' + n.id + '" class="live_edit btn btn-sm btn-primary">编辑</a>&nbsp;' + _statys_btn + '&nbsp;' + _online_btn + '</td>' +
                        '</tr>';
                });
            }
            $('.live_list tbody').html(tr);
        }
    }

    $('.live_list').on('click', '.live_status_edit', function () {
        var _tr = $(this).parents('tr');
        _this_id = _tr.attr('data-id');
        _this_status = _tr.find('.live_status').attr('data-val');
        var arr_status = {
            id: _this_id,
            operator_id: user.id
        }
        $(document).keyup(function (event) {
            if (event.keyCode == 13 || event.keyCode == 32) {
                event.returnValue = false;
            }
        });
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
                $.post(url_status, arr_status, function (data) {
                    console.log(data);
                    if (data.code == '0') {
                        if (data.data == '0') {
                            _tr.find('.live_status_edit').html('关闭').removeClass('btn-info').addClass('btn-default').removeAttr("disabled");
                            _tr.find('.live_status').attr('data-val', '0');
                            _tr.find('.live_status span').removeClass('text-green').addClass('text-gray').html('开启');
                        } else if (data.data == '1') {
                            $('.live_list tr').each(function () {
                                if ($(this).find('.live_status').attr('data-val') == '1') {
                                    $(this).find('.live_status').attr('data-val', '2');
                                    $(this).find('.live_status span').removeClass('text-green').addClass('text-gray').html('完毕');
                                    $(this).find('.live_status_edit').html('完毕').removeClass('btn-info').addClass('btn-default').attr("disabled", true);
                                }
                            });
                            _tr.find('.live_status_edit').html('开启').removeClass('btn-default').addClass('btn-info').removeAttr("disabled");
                            _tr.find('.live_status').attr('data-val', '1');
                            _tr.find('.live_status span').removeClass('text-gray').addClass('text-green').html('完毕');


                        } else if (data.data == '2') {
                            _tr.find('.live_status_edit').html('完毕').removeClass('btn-info').addClass('btn-default').attr("disabled", true);
                            _tr.find('.live_status').attr('data-val', '2');
                            _tr.find('.live_status span').removeClass('text-green').addClass('text-gray').html('完毕');
                        } else {
                            _alert_warning(data.msg);
                        }

                    } else {
                        _alert_warning(data.msg);
                    }
                });
            }
        });
    });
    $('.live_list').on('click', '.live_online_edit', function () {
        var _tr = $(this).parents('tr');
        _this_id = _tr.attr('data-id');
        _this_online = _tr.find('.live_online').attr('data-val');
        var arr_online = {
            id: _this_id,
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
                        if (data.data == '0') {
                            _tr.find('.live_online_edit').html('上线').removeClass('btn-info').addClass('btn-default');
                            _tr.find('.live_online').attr('data-val', '0');
                            _tr.find('.live_online span').removeClass('text-green').addClass('text-gray').html('下线');
                        } else if (data.data == '1') {
                            _tr.find('.live_online_edit').html('下线').removeClass('btn-default').addClass('btn-info');
                            _tr.find('.live_online').attr('data-val', '1');
                            _tr.find('.live_online span').removeClass('text-gray').addClass('text-green').html('上线');
                        } else {
                            _alert_warning(data.msg);
                        }
                    } else {
                        _alert_warning(data.msg);
                    }
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