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
    var url = api_back + '/article/cms/list';
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
        article_title: '',
        article_update_id: '',
        article_online: '',
        sort_status: 'desc',
        type_id: ''
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
                            if (n.articleCreateId == v.id) {
                                c_name = v.name;
                            }
                            if (n.articleUpdateId == v.id) {
                                u_name = v.name;
                            }
                        });
                        var _img_url = n.articleLogo.substr(0, 4);
                        if (_img_url == 'http') {
                            this_img_url = n.articleLogo;
                        } else {
                            this_img_url = server_course + n.articleLogo;
                        }
                        if (n.articleOnline == 1) {
                            is_online = '<span class="text-success">已上线</span>';
                        } else {
                            is_online = '<span class=".text-warning">已下线</span>';
                        }
                        tr += '<tr data-id="' + n.id + '"><td>' + this_no + '</td><td><img class="article_thumb" src="' + this_img_url + '" alt="' + n.articleTitle + '" /></td><td>' + n.articleTitle + '</td><td>' + c_name + '</td><td>' + n.articleCreateTime + '</td><td>' + u_name + '</td><td>' + n.articleUpdateTime + '</td><td class="articleHealth_count"><span>浏览：' + n.articleTotalView + '</span><span>点赞：' + n.articleTotalLike + '</span><span>分享：' + n.articleTotalShare + '</span></td><td>' + is_online + '</td><td><a data-id="' + n.id + '" class="articleHealth_edit" href="javascript:void(0);"><i class="icon-pencil-square"></i></a></td></tr>';
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
                    article_title: '',
                    article_update_id: '',
                    article_online: '',
                    sort_status: 'desc',
                    type_id: ''
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
            article_title: title,
            article_update_id: creater,
            article_online: isOnline,
            sort_status: orderBy,
            type_id: ''
        }
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 3000);
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
                                    if (n.articleCreateId == v.id) {
                                        c_name = v.name;
                                    }
                                    if (n.articleUpdateId == v.id) {
                                        u_name = v.name;
                                    }
                                });
                                var _img_url = n.articleLogo.substr(0, 4);
                                if (_img_url == 'http') {
                                    this_img_url = n.articleLogo;
                                } else {
                                    this_img_url = server_course + n.articleLogo;
                                }
                                if (n.articleOnline == 1) {
                                    is_online = '<span class="text-success">已上线</span>';
                                } else {
                                    is_online = '<span class=".text-warning">已下线</span>';
                                }
                                tr_search += '<tr data-id="' + n.id + '"><td>' + this_no + '</td><td><img class="article_thumb" src="' + this_img_url + '" alt="' + n.articleTitle + '" /></td><td>' + n.articleTitle + '</td><td>' + c_name + '</td><td>' + n.articleCreateTime + '</td><td>' + u_name + '</td><td>' + n.articleUpdateTime + '</td><td class="articleHealth_count"><span>浏览：' + n.articleTotalView + '</span><span>点赞：' + n.articleTotalLike + '</span><span>分享：' + n.articleTotalShare + '</span></td><td>' + is_online + '</td><td><a data-id="' + n.id + '" class="articleHealth_edit" href="javascript:void(0);"><i class="icon-pencil-square"></i></a></td></tr>';
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
                            article_title: title,
                            article_update_id: creater,
                            article_online: isOnline,
                            sort_status: orderBy,
                            type_id: ''
                        }
                        $.post(url, arr_search, function (data) {
                            this_page(data, page);
                        });

                    }
                });
            });
        }
    });






    $('.articleHealth_list').on('click', '.articleHealth_edit', function () {
        var id = $(this).attr('data-id');
        window.location.href = "./articleNormalAdd.html?action=edit&id=" + id;
    });



});