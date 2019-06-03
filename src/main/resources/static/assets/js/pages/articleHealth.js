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
    var url_online = api_back + '/course/cms/online';
    var lk = 0;
    var creater = '<option value="">--更新人--</option>';
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    $.each(user_list, function (i, n) {
        creater += '<option value="' + n.id + '">' + n.name + '</option>';
    });
    $('#articleHealth_search_creater').html(creater);

    $.each(user_list, function (i, n) {
        creater += '<option value="' + n.id + '">' + n.name + '</option>';
    });






    var page = parseInt($_GET['page']);
    var per_page = '30';
    if (page == '' || page == undefined || isNaN(page)) {
        page = 1;
    } else {}
    arr = {
        page: page - 1,
        size: per_page,
        course_title: '',
        course_update_id: '',
        course_online: '',
        sort_status: 'desc',
        course_type_id: ''
    }
    $.post(url, arr, function (data) {
        function this_page(data, page) {
            var tr = '';
            var c_name;
            var u_name;
            var code = data.code;
            if (code == 0) {
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
                            this_img_url = n.courseLogo;
                        } else {
                            this_img_url = server_course + n.courseLogo;
                        }
                        if (n.courseOnline == 1) {
                            is_online = '<span data-type="'+ n.courseOnline +'" class="btn btn-info btn-sm is_online">上线</span>';
                        } else {
                            is_online = '<span data-type="'+ n.courseOnline +'" class="btn btn-default btn-sm is_online">下线</span>';
                        }
                        tr += '<tr data-id="' + n.id + '"><td>' + this_no + '</td><td><img class="article_thumb" src="' + this_img_url + '" alt="' + n.courseTitle + '" /></td><td>' + n.courseTitle + '</td><td>' + c_name + '</td><td>' + n.courseCreateTime + '</td><td>' + u_name + '</td><td>' + n.courseUpdateTime + '</td><td class="articleHealth_count"><span>浏览：' + n.courseTotalView + '</span><span>点赞：' + n.courseTotalLike + '</span><span>分享：' + n.courseTotalShare + '</span></td><td>' + is_online + '</td><td><a data-id="' + n.id + '" class="articleHealth_edit" href="javascript:void(0);"><i class="icon-pencil-square"></i></a>&nbsp;&nbsp;<a data-id="' + n.id + '" class="alert_url" href="javascript:void(0);"><i class="icon-qrcode"></i></a></td></tr>';
                    });
                }
                $('.articleHealth_list tbody').html(tr);
            }
        }
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
                    course_type_id: ''
                }
                $.post(url, arr, function (data) {
                    this_page(data, page);
                });

            }
        });
    });

    $('.articleHealth_search_submit').click(function () {
        var title = $('#articleHealth_search_title').val();
        var creater = $('#articleHealth_search_creater').val();
        var isOnline = $('#articleHealth_search_isOnline').val();
        var orderBy = $('#articleHealth_search_orderBy').val();
        arr_search = {
            page: page - 1,
            size: per_page,
            course_title: title,
            course_update_id: creater,
            course_online: isOnline,
            sort_status: orderBy,
            course_type_id: ''
        }
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            $('.articleHealth_search_submit').removeClass('btn-info').addClass('btn-default');
            setTimeout(function () {
                lk = 0;
                $('.articleHealth_search_submit').removeClass('btn-default').addClass('btn-info');
            }, 2000);
            $.post(url, arr_search, function (data) {
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
                                    is_online = '<span data-type="'+ n.courseOnline +'" class="btn btn-info btn-sm is_online">上线</span>';
                                } else {
                                    is_online = '<span data-type="'+ n.courseOnline +'" class="btn btn-default btn-sm is_online">下线</span>';
                                }
                                tr_search += '<tr data-id="' + n.id + '"><td>' + this_no + '</td><td><img class="article_thumb" src="' + this_img_url + '" alt="' + n.courseTitle + '" /></td><td>' + n.courseTitle + '</td><td>' + c_name + '</td><td>' + n.courseCreateTime + '</td><td>' + u_name + '</td><td>' + n.courseUpdateTime + '</td><td class="articleHealth_count"><span>浏览：' + n.courseTotalView + '</span><span>点赞：' + n.courseTotalLike + '</span><span>分享：' + n.courseTotalShare + '</span></td><td>' + is_online + '</td><td><a data-id="' + n.id + '" class="articleHealth_edit" href="javascript:void(0);"><i class="icon-pencil-square"></i></a>&nbsp;&nbsp;<a data-id="' + n.id + '" class="alert_url" href="javascript:void(0);"><i class="icon-qrcode"></i></a></td></tr>';
                            });
                        }
                        $('.articleHealth_list tbody').html(tr_search);
                    }
                }
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
                            course_type_id: ''
                        }
                        $.post(url, arr_search, function (data) {
                            this_page(data, page);
                        });

                    }
                });
            });
        }
    });

    $('.articleHealth_list').on('click', '.is_online', function () {
        var status_curr = $(this).attr('data-type');
        var span = $(this);
        var status = '0';
        if (status_curr == '1') {
            status = '0';
        } else {
            status = '1';
        }
        var arr_status = {
            id:  $(this).parent().parent().attr('data-id'),
            online: status
        }
        $.post(url_online, arr_status, function (data) {
            if (data.code == '0') {
                span.attr('data-type', data.data);
                if (data.data == '0') {
                    span.html('下线');
                    span.addClass('btn-default').removeClass('btn-info');
                } else if (data.data == '1') {
                    span.html('上线');
                    span.addClass('btn-info').removeClass('btn-default');
                }
            } else {}
        });
    });

    $('.articleHealth_list').on('click', '.alert_url', function () {
        alert('http://hdqd.houaihome.com/zhibo/index.php?id=' + $(this).attr('data-id'));

    });
    $('.articleHealth_list').on('click', '.articleHealth_edit', function () {
        var id = $(this).attr('data-id');
        window.location.href = "./articleHealthAdd.html?action=edit&id=" + id;
    });



});