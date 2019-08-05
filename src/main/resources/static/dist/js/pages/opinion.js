jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var url_list = api_back + '/feedback/cms/list';
    var server_course = sessionStorage.getItem('server_course');
    var server_xiqing = 'http://192.168.1.185:8080/';

    var lk = 0;
    var page = 1;
    var per_page = '30';
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);

    var arr_list = {
        page: page - 1,
        size: per_page,
        feedbackName: '',
        feedbackContactUs: '',
        feedbackType: ''
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
            console.log(data);
            to_page(data, page)
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
                    arr_list = {
                        page: page - 1,
                        size: per_page,
                        feedbackName: '',
                        feedbackContactUs: '',
                        feedbackType: ''
                    }
                    $.post(url_list, arr_list, function (data) {
                        to_page(data, page);
                    });

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


    $('.opinion_search_submit').click(function () {
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 3000);
            var _feedbackName = '';
            var _feedbackContactUs = '';
            var _feedbackType = '';
            var arr_list_search = {
                page: page - 1,
                size: per_page,
                feedbackName: _feedbackName,
                feedbackContactUs: _feedbackContactUs,
                feedbackType: _feedbackType
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
                    console.log(data);
                    to_page(data, page)
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
                            arr_list_search = {
                                page: page - 1,
                                size: per_page,
                                feedbackName: _feedbackName,
                                feedbackContactUs: _feedbackContactUs,
                                feedbackType: _feedbackType
                            }
                            $.post(url_list, arr_list_search, function (data) {
                                to_page(data, page);
                            });

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
        }
    });




    function to_page(data, page) {
        var tr = '';
        if (data.code == '0') {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="11">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    var u_name = '';
                    var _reply = '<a href="javascript:void(0);" class="btn btn-sm btn-default opinion_reply">回复</a>';
                    var this_no = this_page_start + i + 1;
                    var _image = '';
                    var _str = n.feedbackImg;
                    var _img = _str.split(',');
                    if (_img != '') {
                        $.each(_img, function (ii, nn) {
                            _image += '<img class="opinion_list_img img_prev" src="' + server_xiqing + nn + '">';
                        });
                    }
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td class="_opinion_user_name" data-val="' + n.userId + '">' + n.userName + '</td>' +
                        '<td class="">' + n.feedbackName + '</td>' +
                        '<td class="">' + n.feedbackContactUs + '</td>' +
                        '<td class="">' + n.feedbackType + '</td>' +
                        '<td class="">' + n.feedbackContent + '</td>' +
                        '<td class="">' + _image + '</td>' +
                        '<td class="list_time">' + n.feedbackCreateTime + '</td>' +
                        '<td></td>' +
                        '<td>' + u_name + '</td>' +
                        '<td><a href="opinionView.html?oid=' + n.id + '" class="btn btn-sm btn-primary opinion_view">查看</a>&nbsp;' + _reply + '&nbsp;<ahref="javascript:void(0);" class="btn btn-sm btn-primary opinion_del"><i class="fa fa-trash"></i></a></td>' +
                        '</tr>';
                });
            }
            $('.opinion_list tbody').html(tr);
        }
    }




    
});