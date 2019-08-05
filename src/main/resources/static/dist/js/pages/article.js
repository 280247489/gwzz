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
    var url_online = api_back + '/article/cms/online';
    var url_managerView = api_back + '/article/cms/managerView';
    var lk = 0;
    var creater = '<option value="">选择更新人</option>';
    var type_option = '<option value="">选择分类</option>';
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    $.each(user_list, function (i, n) {
        creater += '<option value="' + n.id + '">' + n.name + '</option>';
    });
    $('#article_search_creater').html(creater);

    var article_type = sessionStorage.getItem('article_type');
    article_type = JSON.parse(article_type);

    $.each(article_type, function (i, n) {
        type_option += '<option value="' + n.id + '">' + n.typeName + '</option>';
    });
    $('#article_search_type_id').html(type_option);


    var page = 1;
    var per_page = '30';
    arr = {
        page: page - 1,
        size: per_page,
        article_title: '',
        article_update_id: '',
        article_online: '',
        sort_status: 'desc',
        type_id: ''
    }
    loading();
    $.post(url, arr, function (data) {
        console.log(data);
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
        loading_end();
    });

    $('.article_search_submit').click(function () {
        var title = $('#article_search_title').val();
        var creater = $('#article_search_creater').val();
        var isOnline = $('#article_search_isOnline').val();
        var orderBy = $('#article_search_orderBy').val();
        var type_id = $('#article_search_type_id').val();
        arr_search = {
            page: page - 1,
            size: per_page,
            article_title: title,
            article_update_id: creater,
            article_online: isOnline,
            sort_status: orderBy,
            type_id: type_id
        }
        if (lk > 0) {
            return false;
        } else {
            loading();
            lk = 1;
            $('.article_search_submit').removeClass('btn-primary').addClass('btn-default');
            setTimeout(function () {
                lk = 0;
                $('.article_search_submit').removeClass('btn-default').addClass('btn-primary');
            }, 2000);
            $.post(url, arr_search, function (data) {
                this_page_search(data, page);
                console.log(data);
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
                            type_id: type_id
                        }
                        $.post(url, arr_search, function (data) {
                            this_page(data, page);
                        });

                    }
                });
            });
            loading_end();
        }
    });


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
            console.log(data);
            if (data.code == '0') {
                span.attr('data-type', status);
                if (status == '0') {
                    tr.find('.article_isonline span').html('下线').removeClass('text-green').addClass('text-gray');
                    span.removeClass('btn-info').addClass('btn-default');
                    span.html('上线');
                } else if (status == '1') {
                    tr.find('.article_isonline span').html('上线').removeClass('text-gray').addClass('text-green');
                    span.removeClass('btn-default').addClass('btn-info');
                    span.html('下线');
                }
            } else {}
        });
    });

    function this_page(data, page) {
        var tr = '';
        var c_name;
        var u_name;
        var code = data.code;
        if (code == 0) {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="10">暂无数据</td></tr>';
                $('#pagination').hide();
            } else {
                $('#pagination').show();
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
                    var _img_url1 = n.articleLogo1.substr(0, 4);
                    var _img_url2 = n.articleLogo2.substr(0, 4);
                    var _img_url3 = n.articleLogo3.substr(0, 4);
                    if (_img_url1 == 'http') {
                        this_img_url1 = n.articleLogo1;
                    } else {
                        this_img_url1 = server_course + n.articleLogo1;
                    }
                    if (_img_url2 == 'http') {
                        this_img_url2 = n.articleLogo2;
                    } else {
                        this_img_url2 = server_course + n.articleLogo2;
                    }
                    if (_img_url3 == 'http') {
                        this_img_url3 = n.articleLogo3;
                    } else {
                        this_img_url3 = server_course + n.articleLogo3;
                    }

                    if (n.articleOnline == '1') {
                        _is_online = '<span class="text-green">上线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.articleOnline + '" class="btn btn-info btn-sm is_online">下线</a>';
                    } else {
                        _is_online = '<span class="text-gray">下线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.articleOnline + '" class="btn btn-default btn-sm is_online">上线</a>';
                    }

                    tr +=
                        '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td><img class="article_thumb img_prev" src="' + this_img_url1 + '"/></td>' +
                        '<td class="list_title">' + n.articleTitle + '</td>' +
                        '<td class="list_time">' + n.articleReleaseTime + '</td>' +
                        // '<td>推荐</td>' +
                        '<td class="list_time">' + n.articleUpdateTime + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td class="articleHealth_count"><span>浏览：' + n.articleTotalView + '</span><span>点赞：' + n.articleTotalLike + '</span><span>回复：' + n.articleTotalComment + '</span><span class="view_add"><a href="javascript:void(0);" data-val="' + n.articleTotalManagerView + '">增加：' + n.articleTotalManagerView + '</a></span></td>' +
                        '<td class="article_isonline">' + _is_online + '</td>' +
                        '<td><a data-id="' + n.id + '" class="articleHealth_edit btn btn-primary btn-sm" href="javascript:void(0);">编辑</a>&nbsp;' + '<a class="article_this_view btn btn-sm btn-primary" href="./commentArticle.html?action=view&id=' + n.id + '">评论</a>&nbsp;' + is_online_btn + '</td>' +
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
                $('#pagination').hide();
            } else {
                $('#pagination').show();

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
                    var _img_url1 = n.articleLogo1.substr(0, 4);
                    var _img_url2 = n.articleLogo2.substr(0, 4);
                    var _img_url3 = n.articleLogo3.substr(0, 4);
                    if (_img_url1 == 'http') {
                        this_img_url1 = n.articleLogo1;
                    } else {
                        this_img_url1 = server_course + n.articleLogo1;
                    }
                    if (_img_url2 == 'http') {
                        this_img_url2 = n.articleLogo2;
                    } else {
                        this_img_url2 = server_course + n.articleLogo2;
                    }
                    if (_img_url3 == 'http') {
                        this_img_url3 = n.articleLogo3;
                    } else {
                        this_img_url3 = server_course + n.articleLogo3;
                    }
                    if (n.articleOnline == '1') {
                        _is_online = '<span class="text-green">上线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.articleOnline + '" class="btn btn-info btn-sm is_online">下线</a>';
                    } else {
                        _is_online = '<span class="text-gray">下线</span>';
                        is_online_btn = '<a href="javascript:void(0);" data-type="' + n.articleOnline + '" class="btn btn-default btn-sm is_online">上线</a>';
                    }
                    tr_search +=
                        '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td><img class="article_thumb img_prev" src="' + this_img_url1 + '"/></td>' +
                        '<td class="list_title">' + n.articleTitle + '</td>' +
                        '<td class="list_time">' + n.articleReleaseTime + '</td>' +
                        // '<td>推荐</td>' +
                        '<td class="list_time">' + n.articleUpdateTime + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td class="articleHealth_count"><span>浏览：' + n.articleTotalView + '</span><span>点赞：' + n.articleTotalLike + '</span><span>回复：' + n.articleTotalComment + '</span><span class="view_add"><a href="javascript:void(0);" data-val="' + n.articleTotalManagerView + '">增加：' + n.articleTotalManagerView + '</a></span></td>' +
                        '<td class="article_isonline">' + _is_online + '</td>' +
                        '<td><a data-id="' + n.id + '" class="articleHealth_edit btn btn-primary btn-sm" href="javascript:void(0);">编辑</a>&nbsp;' + '<a class="article_this_view btn btn-sm btn-primary" href="./commentArticle.html?action=view&id=' + n.id + '">评论</a>&nbsp;' + is_online_btn + '</td>' +
                        '</tr>';
                });
            }
            $('.course_list tbody').html(tr_search);
        }
    }


    $('.course_list').on('click', '.articleHealth_edit', function () {
        var id = $(this).attr('data-id');
        window.location.href = "./articleAdd.html?action=edit&id=" + id;
    });
    //修改阅读量
    var _tr
    $('.course_list').on('click', '.view_add a', function () {
        var _num = $(this).attr('data-val');
        _tr = $(this).parents('tr');
        $('.managerView').show();
        $('.managerView_edit_id').val(_tr.attr('data-id'));
        $('.managerView_num').val(_num);
    });
    $('.managerView_submit').click(function () {
        if ($('.managerView_num').val().trim() == '' || $('.managerView_num').val().trim() == null) {
            var _changeNum = 0
        } else {
            var _changeNum = $('.managerView_num').val().trim();
        }
        //url_managerView
        var arr_managerView = {
            articleId: $('.managerView_edit_id').val(),
            changeNum: _changeNum
        }
        $.ajax({
            url: url_managerView,
            type: 'POST',
            data: arr_managerView,
            async: false,
            cache: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
            success: function (data) {
                console.log(data);
                //console.log(data.data);
                if (data.code == '0') {
                    _cancel();
                    _tr.find('.view_add a').attr('data-val',data.data).html('增加：'+data.data);
                    $modal({
                        type: 'alert',
                        icon: 'success',
                        timeout: 3000,
                        title: '成功',
                        content: '修改成功',
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
    });
    $('.managerView_cancel').click(function () {
        _cancel();
    });

    function _cancel() {
        $('.managerView').hide();
        $('.managerView_edit_id').val('');
        $('.managerView_num').val('');
    }
});