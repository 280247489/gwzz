jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var url_list = api_back + '/userHelp/cms/listUserHelp';
    var url_add = api_back + '/userHelp/cms/addUserHelp';
    var url_upd = api_back + '/userHelp/cms/updUserHelp';
    var url_online = api_back + '/userHelp/cms/updUseYn';
    var url_del = api_back + 'wzz/userHelp/cms/delUserHelp';
    var server_course = sessionStorage.getItem('server_course');
    var option_cache;
    var lk = 0;
    var page = 1;
    var per_page = '30';
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    $('.help_createId').val(user.id);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);

    var arr_list = {
        page: page - 1,
        size: per_page,
        direction: 'desc',
        sorts: '',
        heleTitle: '',
        helpType: '',
        useYn: ''
    }
    $.post(url_list, arr_list, function (data) {
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
            getPage: function (page) {
                arr_list = {
                    page: page - 1,
                    size: per_page,
                    direction: 'desc',
                    sorts: '',
                    heleTitle: '',
                    helpType: '',
                    useYn: ''
                }
                $.post(url_list, arr_list, function (data) {
                    to_page(data, page);
                });

            }
        });
    });
    $('.help_search_submit').click(function () {
        var _heleTitle = $('#help_search_title').val();
        var _helpType = $('#help_search_type').val();
        var _useYn = $('#help_isonline').val();

        var arr_search_list = {
            page: page - 1,
            size: per_page,
            direction: 'desc',
            sorts: '',
            heleTitle: _heleTitle,
            helpType: _helpType,
            useYn: _useYn
        }
        $.post(url_list, arr_search_list, function (data) {
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
                getPage: function (page) {
                    var arr_search_list = {
                        page: page - 1,
                        size: per_page,
                        direction: 'desc',
                        sorts: '',
                        heleTitle: _heleTitle,
                        helpType: _helpType,
                        useYn: _useYn
                    }
                    $.post(url_list, arr_search_list, function (data) {
                        to_page(data, page);
                    });

                }
            });
        });
    });

    function to_page(data, page) {
        console.log(data);
        var tr = '';
        if (data.recode == '0') {
            var content = data.data.content;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="11">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    var u_name = '';
                    var _online = '';
                    var _type = '';
                    var _change_online = '';
                    var this_no = this_page_start + i + 1;
                    if (n.useYn == '0') {
                        _change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-info help_online">下线</a>';
                        _online = '<span class="text-green">上线</span>';
                    } else if (n.useYn == '1') {
                        _change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-default help_online">上线</a>';
                        _online = '<span class="text-gray">下线</span>';
                    }

                    if (n.helpType == '1') {
                        _type = '常见问题';
                    } else if (n.helpType == '2') {
                        _type = '模块问题';
                    }
                    $.each(user_list, function (k, v) {
                        if (n.helpUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td class="_help_logo_preview"><img src="' + server_course + n.helpLogo + '" class="article_thumb" ></td>' +
                        '<td class="_help_sort" data-val="' + n.helpSort + '">' + n.helpSort + '</td>' +
                        '<td class="_help_title">' + n.helpTitle + '</td>' +
                        '<td class="_help_subtitle">' + n.helpSubtitle + '</td>' +
                        '<td class="_help_content_preview"><img src="' + server_course + n.helpContent + '" class="article_thumb" ></td>' +
                        '<td class="_help_type" data-val="' + n.helpType + '">' + _type + '</td>' +
                        '<td class="_help_online" data-val="' + n.useYn + '">' + _online + '</td>' +
                        '<td class="_help_updater" data-val="' + user.id + '">' + u_name + '</td>' +
                        '<td>' + n.helpUpdateTime + '</td>' +
                        '<td><a href="javascript:void(0);" class="btn btn-sm btn-primary help_edit">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;' + _change_online + '&nbsp;&nbsp;&nbsp;&nbsp;<ahref="javascript:void(0);" class="btn btn-sm btn-primary help_del"><i class="fa fa-trash"></i></a></td>' +
                        '</tr>';
                });
            }
            $('.help_list tbody').html(tr);
        }
    }
    $('.help_list').on('click', '.help_online', function () {
        var _tr = $(this).parents('tr');
        var _arr_slide_online = {
            id: _tr.attr('data-id'),
            operator_id: user.id
        }
        $.ajax({
            url: url_online,
            type: 'POST',
            data: _arr_slide_online,
            async: true,
            cache: false,
            dataType: "json",
            timeout: 50000,
            beforeSend: function (data) {},
            success: function (data) {
                console.log(data);
                if (data.recode == '0') {
                    if (data.data == '0') {
                        _tr.find('.help_online').removeClass('btn-default').addClass('btn-info').html('下线');
                        _tr.find('._help_online span').removeClass('text-gray').addClass('text-green').html('上线');
                    } else if (data.data == '1') {
                        _tr.find('.help_online').removeClass('btn-info').addClass('btn-default').html('上线');
                        _tr.find('._help_online span').removeClass('text-green').addClass('text-gray').html('下线');
                    }
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
    $('.help_list_add').click(function () {
        $('#help_add_choose').attr('data-action', 'add');
        $('.new_help_add').html('添加');
        $('.help_add_box_title').html('问题添加');
        $('.help_add').fadeIn();
    });

    $('.help_list').on('click', '.help_edit', function () {
        var _tr = $(this).parents('tr');
        $('#help_add_choose').attr('data-action', 'edit');
        $('.new_help_submit').html('提交');
        $('.help_add_box_title').html('问题编辑');
        $('.help_upd_id').val(_tr.attr('data-id'));
        $('.helpType').val(_tr.find('._help_type').attr('data-val'));
        $('.helpTitle').val(_tr.find('._help_title').html());
        $('.helpSubtitle').val(_tr.find('._help_subtitle').html());
        $(".helpSort").val(_tr.find('._help_sort').attr('data-val'));
        $(".help_file_btn").html('<i class="fa fa-check"></i>&nbsp;已选择');
        $('.help_add').fadeIn();
    });

    $('.new_help_submit').click(function () {
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 3000);
            var _this_url;
            if ($('#help_add_choose').attr('data-action') == 'edit') {
                _this_url = url_upd;
            } else {
                _this_url = url_add;
            }
            var formData = new FormData($("#help_add_choose")[0]);
            console.log(_this_url);
            $.ajax({
                url: _this_url,
                type: 'POST',
                data: formData,
                async: true,
                cache: false,
                contentType: false,
                processData: false,
                dataType: "json",
                timeout: 50000,
                beforeSend: function (data) {
                    if ($('.helpTitle').val().trim() == '') {
                        _alert_warning('标题不能为空！');
                        $('.helpTitle').focus();
                        return false;
                    } else if ($('.helpSubtitle').val().trim() == '') {
                        _alert_warning('副标题不能为空！');
                        $('.helpSubtitle').focus();
                        return false;
                    } else if ($('.helpSort').val() == '' || $('.helpSort').val() == null) {
                        _alert_warning('顺序不能为空！');
                        $('.helpSort').focus();
                        return false;
                    }
                    if ($('#help_add_choose').attr('data-action') == 'add') {
                        if ($('.helpLogo').val().trim() == '') {
                            _alert_warning('请添加封面图片！');
                            return false;
                        } else if ($('.helpContent').val().trim() == '') {
                            _alert_warning('请添加详情图片！');
                            return false;
                        }
                    }
                },
                success: function (data) {
                    console.log(data);
                    if (data.recode == '0') {
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

        }
    });
    $('.new_help_cancel').click(function () {
        cancel();
    });

    $('.help_file_btn').click(function () {
        $(this).parent().find('.help_file').trigger('click');
    });

    $('.help_file').change(function () {
        var _this = $(this);
        var _done = '<i class="fa fa-check"></i>&nbsp;已选择';
        var _null = '<i class="fa fa-cloud-upload"></i>&nbsp;上传图片';
        if ($('#help_add_choose').attr('data-action') == 'edit') {
            if (_this.val() == '') {
                _this.parent().parent().find('.help_file_btn').html(_done);
            } else {
                _this.parent().parent().find('.help_file_btn').html(_done);
            }
        } else {
            if (_this.val() == '') {
                _this.parent().parent().find('.help_file_btn').html(_null);
            } else {
                _this.parent().parent().find('.help_file_btn').html(_done);
            }
        }
    });

    function cancel() {
        $('.help_add').hide();
        img_cache = '';
        $("#help_add_choose")[0].reset();
        $('.help_file_btn').html('<i class="fa fa-cloud-upload"></i>&nbsp;上传图片');
    }

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