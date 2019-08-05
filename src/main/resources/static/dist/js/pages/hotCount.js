jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var server_course = sessionStorage.getItem('server_course');
    var url_list = api_back + '/searchHistoryDay/cms/list';
    var lk = 0;
    var page = 1;
    var per_page = '30';
    var _date_yesterday = new Date();
    _date_yesterday.setTime(_date_yesterday.getTime() - 24 * 60 * 60 * 1000);
    var _yesterday = _date_yesterday.getFullYear() + "-" + (_date_yesterday.getMonth() + 1) + "-" + _date_yesterday.getDate();
    // $('#query_start_time').val(_yesterday);
    var _date_today = new Date();
    _date_today.setTime(_date_today.getTime());
    var _today = _date_today.getFullYear() + "-" + (_date_today.getMonth() + 1) + "-" + _date_today.getDate();
    // $('#query_end_time').val(_today);

    var arr_list = {
        pageIndex: page,
        limit: per_page,
        startTime: _yesterday,
        endTime: _today,
        searchType: ''
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
            to_page(data, page);
            new Page({
                id: 'pagination',
                pageTotal: data.data.totalPages, //必填,总页数
                pageAmount: per_page, //每页多少条
                dataTotal: data.data.totalElements, //总共多少条数据
                curPage: 1, //初始页码,不填默认为1
                pageSize: 5, //分页个数,不填默认为5
                showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
                showSkipInputFlag: true, //是否支持跳转,不填默认不显示
                getPage: function (_page) {
                    arr_list = {
                        pageIndex: _page,
                        limit: per_page,
                        startTime: _yesterday,
                        endTime: _today,
                        searchType: ''
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
                            to_page(data, _page);
                        },
                        complete: function () {
                            loading_end();
                        },
                        error: function (data) {
                            _alert_warning(data.msg);
                        }
                    }, "json");

                }
            });
        },
        complete: function () {
            loading_end();
        },
        error: function (data) {
            _alert_warning(data.msg);
        }
    }, "json");

    function to_page(data, page) {
        console.log(data);
        var code = data.code;
        var tr = '';
        if (code == 0) {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="3">暂无数据</td></tr>';
                $('#pagination').hide();
            } else {
                $('#pagination').show();
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    this_no = this_page_start + i + 1;
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        // '<td></td>' +
                        '<td>' + n['1'] + '</td>' +
                        '<td>' + n['2'] + '</td>' +
                        '</tr>';
                });
            }
            $('#hot_list tbody').html(tr);
        }
    }

    $('.hot_search_submit').click(function () {
        var arr_list = {
            pageIndex: page,
            limit: per_page,
            startTime: $('#query_start_time').val(),
            endTime: $('#query_end_time').val(),
            searchType: ''
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
                if ($('#query_start_time').val() == '') {
                    _alert_warning('请选择起始时间');
                    $('.query_start_time').focus();
                    return false;
                } else if ($('#query_end_time').val() == '') {
                    _alert_warning('请选择结束时间');
                    $('.query_end_time').focus();
                    return false;
                } else {
                    loading();
                }
            },
            success: function (data) {
                to_page(data,page);
                new Page({
                    id: 'pagination',
                    pageTotal: data.data.totalPages, //必填,总页数
                    pageAmount: per_page, //每页多少条
                    dataTotal: data.data.totalElements, //总共多少条数据
                    curPage: 1, //初始页码,不填默认为1
                    pageSize: 5, //分页个数,不填默认为5
                    showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
                    showSkipInputFlag: true, //是否支持跳转,不填默认不显示
                    getPage: function (_page) {
                        arr_list = {
                            pageIndex: _page,
                            limit: per_page,
                            startTime: $('#query_start_time').val(),
                            endTime: $('#query_end_time').val(),
                            searchType: ''
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
                                to_page(data, _page);
                            },
                            complete: function () {
                                loading_end();
                            },
                            error: function (data) {
                                _alert_warning(data.msg);
                            }
                        }, "json");

                    }
                });
            },
            complete: function () {
                loading_end();
            },
            error: function (data) {
                _alert_warning(data.msg);
            }
        }, "json");
    });

});