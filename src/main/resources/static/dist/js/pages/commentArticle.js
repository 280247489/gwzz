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

    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    $('.user_id').val(user.id);
    $('.user_logo').val(user.logo);
    $('.user_name').val(user.name);

    var api_back = sessionStorage.getItem('api_back');
    var url = api_back + '/articleComment/cms/list';
    var re_comment = api_back + '/articleComment/cms/add';
    var remove_comment = api_back + '/articleComment/cms/remove';
    var lk = 0;
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var action = $_GET['action'];
    var id = $_GET['id'];
    var page = 1;
    var per_page = '30';
    var article_id;
    if(action == 'view' && id != ''){
        article_id = id;
    }else{
        article_id = '';
    }

    var arr_articleComment_all = {
        page: page - 1,
        size: per_page,
        key_words: '',
        phone_number: '',
        article_name: '',
        user_name: '',
        comment_type: '',
        query_start_time: '',
        query_end_time: '',
        sort_role: '0',
        comment_root_id: '',
        article_id:article_id,
        id: ''
    }
    $.post(url, arr_articleComment_all, function (data) {
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
                _arr = {
                    page: page - 1,
                    size: per_page,
                    key_words: '',
                    phone_number: '',
                    article_name: '',
                    user_name: '',
                    comment_type: '',
                    query_start_time: '',
                    query_end_time: '',
                    sort_role: '0',
                    comment_root_id: '',
                    article_id:article_id,
                    id: ''
                }
                $.post(url, _arr, function (data) {
                    this_page(data, page);
                });

            }
        });
    });
    $('.comment_search_submit').click(function () {
        var key_words = $('#key_words').val();
        var article_title = $('#article_title').val();
        var comment_type = $('#comment_type').val();
        var user_name = $('#user_name').val();
        var phone_number = $('#phone_number').val();
        var query_start_time = $('#query_start_time').val();
        var query_end_time = $('#query_end_time').val();
        var arr_search = {
            page: page - 1,
            size: per_page,
            key_words: key_words,
            phone_number: phone_number,
            article_name: article_title,
            user_name: user_name,
            comment_type: comment_type,
            query_start_time: query_start_time,
            query_end_time: query_end_time,
            sort_role: '0',
            comment_root_id: '',
            article_id:article_id,
            id: ''
        }
        $.post(url, arr_search, function (data) {
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
                    _arr = {
                        page: page - 1,
                        size: per_page,
                        key_words: key_words,
                        phone_number: phone_number,
                        article_name: article_title,
                        user_name: user_name,
                        comment_type: comment_type,
                        query_start_time: query_start_time,
                        query_end_time: query_end_time,
                        sort_role: '0',
                        comment_root_id: '',
                        article_id:article_id,
                        id: ''
                    }
                    $.post(url, _arr, function (data) {
                        this_page(data, page);
                    });

                }
            });
        });


    });

    function this_page(data, page) {
        var tr = '';
        var code = data.code;
        var _type;
        if (code == 0) {
            var content = data.data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="8">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    this_no = this_page_start + i + 1;
                    if (n.commentType == '0') {
                        _type = '<span>点赞：' + n.like + '</span><span>回复：' + n.commentSum + '</span>';
                        _view = '<a class="view_comment btn btn-sm btn-primary" href="commentArticleView.html?action=view&id=' + n.id + '&commentType=' + n.commentType + '&commentRootId=' + n.commentRootId + '">查看</a>&nbsp;';
                        _content = n.commentContent;
                    } else if (n.commentType == '1') {
                        // _type = '<i class="fa fa-comments text-yellow"></i>';
                        _type = '';
                        _view = '';
                        if(n.commentParentUserName == ''){
                            _content = n.commentContent
                        }else{
                            _content = n.commentContent + '<span class="text-light-blue">&nbsp;//'+ n.commentParentUserName +'：</span>' + n.commentParentContent;
                        }
                    }

                    tr += '<tr data-id="' + n.id + '" data-rid="' + n.commentRootId + '" data-type=' + n.commentType + ' data-pid="' + n.commentParentId + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td>' + n.userName + '</td>' +
                        '<td>' + n.tel + '</td>' +
                        '<td>' + n.articleTitle + '</td>' +
                        '<td class="commentContent">' + _content + '</td>' +
                        '<td>' + n.createTime + '</td>' +
                        '<td class="comment_count">' + _type + '</td>' +
                        '<td>' + _view + '<a class="re_comment btn btn-sm btn-primary" href="javascript:void(0);">回复</a>&nbsp;<a class="remove_comment btn btn-sm btn-primary" href="javascript:void(0);"><i class="fa fa-trash"></i></a> </td>' +
                        '</tr>';
                });
            }
            $('.comment_list tbody').html(tr);
        }
    }

    $('.comment_list').on('click', '.re_comment', function () {
        var _tr = $(this).parents('tr');
        var _id = _tr.attr('data-id');
        $('.comment_parent_id').val(_id);
        $('.comment_add').fadeIn();
    });
    $('.comment_add_submit').click(function () {
        var formData = new FormData($("#comment_add_form")[0]);
        $.ajax({
            url: re_comment,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function () {
                if ($('.comment_content').val().trim() == '') {
                    $modal({
                        type: 'alert',
                        icon: 'error',
                        timeout: 3000,
                        title: '提交失败',
                        content: '不能空回复！',
                        top: 300,
                        center: true,
                        transition: 300,
                        closable: true,
                        mask: true,
                        pageScroll: true,
                        width: 300,
                        maskClose: true,
                        callBack: function () {
                            $('.comment_content').focus();
                        }
                    });
                    console.log($('.comment_content').val().trim());
                    return false;
                }
            },
            success: function (data) {
                console.log(data);
                if (data.code == '0') {
                    $modal({
                        type: 'alert',
                        icon: 'success',
                        timeout: 3000,
                        title: '提交成功',
                        content: '回复成功！',
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
                    $('.comment_add').fadeOut();
                } else {
                    _alert_error(data.msg);
                    return false;
                }
            },
            error: function (data) {
                console.log(data);
                _alert_error(data.msg);
            }
        }, "json");
    });

    $('.comment_add_cancel').click(function () {
        $('.re_comment').val('');
        $('.comment_add').fadeOut();
    });



    $('.comment_list').on('click', '.remove_comment', function () {
        var _tr = $(this).parents('tr');
        var _id = _tr.attr('data-id');
        var arr_remove = {
            comment_id: _id
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '确认操作',
            content: '确定删除?',
            transition: 300,
            closable: true,
            mask: true,
            top: 400,
            center: true,
            pageScroll: false,
            width: 500,
            maskClose: true,
            cancelText: '取消',
            confirmText: '确认',
            cancel: function (close) {
                close();
            },
            confirm: function (close) {
                close();
                $.ajax({
                    url: remove_comment,
                    type: 'POST',
                    data: arr_remove,
                    async: false,
                    cache: false,
                    dataType: "json",
                    timeout: 50000,
                    beforeSend: function () {

                    },
                    success: function (data) {
                        if (data.code == '0') {
                            $modal({
                                type: 'alert',
                                icon: 'success',
                                timeout: 3000,
                                title: '成功',
                                content: '删除成功',
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
                            return false;
                        } else {
                            console.log(data);
                            _alert_error(data.msg);
                        }
                    },
                    error: function (data) {
                        _alert_error(data.msg);
                    }
                }, "json");
            }
        });
    });
    function _alert_error(msg) {
        $modal({
            type: 'alert',
            icon: 'error',
            timeout: 3000,
            title: '错误',
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