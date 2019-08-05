jQuery(document).ready(function () {
    var api_back = sessionStorage.getItem('api_back');
    var url_list = api_back + '/advertise/cms/list';
    var url_add = api_back + '/advertise/cms/add';
    var url_upd = api_back + '/advertise/cms/upd';
    var url_online = api_back + '/advertise/cms/updOnline';
    var url_list_course = api_back + '/course/cms/list';
    var url_list_article = api_back + '/article/cms/list';
    var url_list_live = api_back + '/liveMaster/cms/list';
    var server_course = sessionStorage.getItem('server_course');
    var option_cache;
    var lk = 0;
    var img_cache = '';
    var page = parseInt(sessionStorage.getItem('page'));
    var per_page = '30';
    if (page == '' || page == undefined || isNaN(page)) {
        page = 1;
    } else {}
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    $('.ad_createId').val(user.id);
    var user_list = sessionStorage.getItem('user_list');
    user_list = JSON.parse(user_list);
    var option_updater = '<option value="">更新人</option>';
    $.each(user_list, function (i, n) {
        option_updater += '<option value="' + n.id + '">' + n.name + '</option>';
    });
    $('.ad_search_updater').html(option_updater);
    sessionStorage.removeItem("select_course_list");
    sessionStorage.removeItem("select_live_list");
    sessionStorage.removeItem("select_article_list");


    var arr_list = {
        page: page - 1,
        size: per_page,
        direction: '',
        sorts: '',
        aName: '',
        aType: ''
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
                    direction: '',
                    sorts: '',
                    aName: '',
                    aType: ''
                }
                $.post(url_list, arr_list, function (data) {
                    to_page(data, page);
                });

            }
        });
    });


    $('.ad_search_submit').click(function () {
        var _aName = $('.ad_search_keywords').val();
        var _aType = $('.ad_search_type').val();
        var arr_list_search = {
            page: page - 1,
            size: per_page,
            direction: '',
            sorts: '',
            aName: _aName,
            aType: _aType
        }
        $.post(url_list, arr_list_search, function (data) {
            to_page_search(data, page);
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
                        direction: '',
                        sorts: '',
                        aName: _aName,
                        aType: _aType
                    }
                    $.post(url_list, arr_list_search, function (data) {
                        to_page_search(data, page);
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
                tr += '<tr><td colspan="9">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    var _H5Type = '';
                    var u_name = '';
                    var _online = '';
                    var _style = '';
                    var change_online = '';
                    var this_no = this_page_start + i + 1;
                    this_no = this_page_start + i + 1;
                    if (n.advertiseType == '1') {
                        var _type = '启动页';
                    } else if (n.advertiseType == '2') {
                        var _type = '首页弹层';
                    }
                    if (n.advertiseOnline == '1') {
                        _online = '上线';
                        change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-info ad_online">下线</a>';
                        _style = 'text-green';
                    } else {
                        _online = '下线';
                        change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-default ad_online">上线</a>'
                        _style = 'text-gray';
                    }
                    if (n.advertiseH5Type == 'Url') {
                        _H5Type = '外链'
                    } else if (n.advertiseH5Type == 'Course') {
                        _H5Type = '课程'
                    } else if (n.advertiseH5Type == 'Live') {
                        _H5Type = '直播'
                    } else if (n.advertiseH5Type == 'Article') {
                        _H5Type = '文章'
                    } else if (n.advertiseH5Type == 'Goods') {
                        _H5Type = '商品'
                    }

                    $.each(user_list, function (k, v) {
                        if (n.advertiseUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td class="_ad_type" data-val="' + n.advertiseType + '">' + _type + '</td>' +
                        '<td class="_ad_preview"><img src="' + server_course + n.advertiseLogo + '" class="article_thumb img_prev" ></td>' +
                        '<td class="_ad_title" data-val="' + n.advertiseH5Url + '">' + n.advertiseName + '</td>' +
                        '<td class="_ad_H5type" data-val="' + n.advertiseH5Type + '">' + _H5Type + '</td>' +
                        '<td class="_ad_online  ' + _style + '" data-val="' + n.advertiseOnline + '">' + _online + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td>' + n.advertiseUpdateTime + '</td>' +
                        '<td><a href="javascript:void(0);" class="btn btn-sm btn-primary ad_edit">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;' + change_online + '</td>' +
                        '</tr>'
                });
                $('.ad_list tbody').html(tr);
            }
        }
    }

    function to_page_search(data, page) {
        console.log(data);
        var tr = '';
        if (data.recode == '0') {
            var content = data.data.content;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="9">暂无数据</td></tr>';
            } else {
                this_page_start = per_page * (page - 1);
                $.each(content, function (i, n) {
                    var _H5Type = '';
                    var _online = '';
                    var u_name = '';
                    var _style = '';
                    var change_online = '';
                    var this_no = this_page_start + i + 1;
                    if (n.advertiseType == '1') {
                        var _type = '启动页';
                    } else if (n.advertiseType == '2') {
                        var _type = '首页弹层';
                    }
                    if (n.advertiseOnline == '1') {
                        _online = '上线';
                        change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-info ad_online">下线</a>';
                        _style = 'text-green';
                    } else if (n.advertiseOnline == '0') {
                        _online = '下线';
                        change_online = '<a href="javascript:void(0);" class="btn btn-sm btn-default ad_online">上线</a>'
                        _style = 'text-gray';
                    }
                    if (n.advertiseH5Type == 'Url') {
                        _H5Type = '外链'
                    } else if (n.advertiseH5Type == 'Course') {
                        _H5Type = '课程'
                    } else if (n.advertiseH5Type == 'Live') {
                        _H5Type = '直播'
                    } else if (n.advertiseH5Type == 'Article') {
                        _H5Type = '文章'
                    } else if (n.advertiseH5Type == 'Goods') {
                        _H5Type = '商品'
                    }
                    $.each(user_list, function (k, v) {
                        if (n.advertiseUpdateId == v.id) {
                            u_name = v.name;
                        }
                    });
                    tr += '<tr data-id="' + n.id + '">' +
                        '<td>' + this_no + '</td>' +
                        '<td class="_ad_type" data-val="' + n.advertiseType + '">' + _type + '</td>' +
                        '<td class="_ad_preview"><img src="' + server_course + n.advertiseLogo + '" class="article_thumb img_prev" ></td>' +
                        '<td class="_ad_title" data-val="' + n.advertiseH5Url + '">' + n.advertiseName + '</td>' +
                        '<td class="_ad_H5type" data-val="' + n.advertiseH5Type + '">' + _H5Type + '</td>' +
                        '<td class="_ad_online  ' + _style + '" data-val="' + n.advertiseOnline + '">' + _online + '</td>' +
                        '<td>' + u_name + '</td>' +
                        '<td>' + n.advertiseUpdateTime + '</td>' +
                        '<td><a href="javascript:void(0);" class="btn btn-sm btn-primary ad_edit">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;' + change_online + '</td>' +
                        '</tr>'
                });
                $('.ad_list tbody').html(tr);
            }
        }
    }



    $('.ad_file_btn').click(function () {
        $('.ad_file').trigger('click');
    });
    $('.ad_file').change(function () {
        if ($('.ad_file')[0].files[0] !== undefined) {
            var _img = $('.ad_file').val();
            var suffix_img = _img.substr(_img.lastIndexOf("."));
            console.log(suffix_img);
            if (suffix_img !== '.jpg' && suffix_img !== '.JPG' && suffix_img !== '.png' && suffix_img !== '.PNG' && suffix_img !== '.gif' && suffix_img !== '.GIF' && suffix_img !== '.jpeg' && suffix_img !== '.JPEG') {
                _alert_warning('请添加正确的图片！');
                return false;
            } else {
                $(".ad_preview").attr("src", URL.createObjectURL($(this)[0].files[0]));
            }
        } else {
            if (img_cache == '' || img_cache == null) {
                $(".ad_preview").attr("src", 'dist/img/add.png');
            } else {
                $(".ad_preview").attr("src", img_cache);
            }
        }
    });


    $('.ad_list_add').click(function () {
        $('#ad_add_choose').attr('data-action', 'add');
        $('.new_ad_add').html('添加');
        $('.ad_add_box_title').html('广告添加');
        $('.ad_type_radio').eq('0').trigger('click');
        $('.ad_add').fadeIn();
    });
    $('.ad_list').on('click', '.ad_edit', function () {
        var _tr = $(this).parents('tr');
        img_cache = _tr.find('.article_thumb').attr('src');
        var target_id = _tr.find('._ad_title').attr('data-val');
        $('#ad_add_choose').attr('data-action', 'edit');
        $('.new_ad_add').html('提交');
        $('.ad_add_box_title').html('广告编辑');
        $('.ad_upd_id').val(_tr.attr('data-id'));
        $('.ad_position').val(_tr.find('._ad_type').attr('data-val'));
        $('.ad_title').val(_tr.find('._ad_title').html());
        $('.ad_preview').attr('src', _tr.find('._ad_preview img').attr('src'));
        $('.type_' + $(".ad_type_radio[name='aH5Type'][value='" + _tr.find('._ad_H5type').attr('data-val') + "']").index()).attr('data-val', target_id);
        $(".ad_type_radio[name='aH5Type'][value='" + _tr.find('._ad_H5type').attr('data-val') + "']").trigger('click');
        $(".aH5Url[name='aH5Url']").val(_tr.find('._ad_title').attr('data-val'));
        $('.ad_add').fadeIn();
    });


    $('.ad_list').on('click', '.ad_online', function () {
        var _tr = $(this).parents('tr');
        var _arr_ad_online = {
            id: _tr.attr('data-id'),
            operator_id: user.id
        }
        $modal({
            type: 'confirm',
            icon: 'info',
            title: '确认操作',
            content: '确定修改?',
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
                    url: url_online,
                    type: 'POST',
                    data: _arr_ad_online,
                    async: true,
                    cache: false,
                    dataType: "json",
                    timeout: 50000,
                    beforeSend: function (data) {},
                    success: function (data) {
                        console.log(data);
                        if (data.recode == '0') {
                            if (data.data.advertiseOnline == '0') {
                                _tr.find('.ad_online').removeClass('btn-info').addClass('btn-default').html('上线');
                                _tr.find('._ad_online').removeClass('text-green').addClass('text-gray').html('下线');
                            } else if (data.data.advertiseOnline == '1') {
                                _tr.find('.ad_online').removeClass('btn-default').addClass('btn-info').html('下线');
                                _tr.find('._ad_online').removeClass('text-gray').addClass('text-green').html('上线');
                            }
                        } else {
                            _alert_warning(data.msg);
                        }
                    },
                    complete: function () {
                        window.location.reload();
                    },
                    error: function (data) {
                        _alert_warning(data.msg);
                    }
                }, "json");
            }
        });
    });
    $('.ad_type_radio').change(function () {
        var _eq = $(this).index();
        $('.type_choose').hide();
        $('.aH5Url').attr('name', '');
        $('.type_' + _eq).show();
        $('.type_' + _eq).find('.aH5Url').attr('name', 'aH5Url');
        var _target = $('.type_' + _eq).attr('data-val');
        option_cache = '';
        if ($('.type_' + _eq).attr('data-action') == 'Course') {
            var _option = '';
            var arr_list_course = {
                page: '',
                size: '99999',
                course_title: '',
                course_update_id: '',
                course_online: '',
                sort_status: 'desc',
                course_type_id: ''
            }
            var select_course_list = sessionStorage.getItem('select_course_list');
            select_course_list = JSON.parse(select_course_list);
            if (select_course_list == '' || select_course_list == null) {
                $.post(url_list_course, arr_list_course, function (data) {
                    console.log(data);
                    if (data.code == '0') {
                        sessionStorage.setItem('select_course_list', JSON.stringify(data.data.data));
                        $.each(data.data.data, function (i, n) {
                            if (_target == n.id) {
                                is_selected = 'selected';
                            } else {
                                is_selected = '';
                            }
                            _option += '<option value="' + n.id + '" ' + is_selected + '>' + n.courseTitle + '</option>';
                        });
                        $('.type_' + _eq).find('.ajax_search_list').html(_option);
                        option_cache = $('.type_' + _eq).find('.ajax_search_list option');
                    } else {
                        _alert_warning(data.msg);
                    }
                });
            } else {
                console.log('cache_course');
                $.each(select_course_list, function (i, n) {
                    if (_target == n.id) {
                        is_selected = 'selected';
                    } else {
                        is_selected = '';
                    }
                    _option += '<option value="' + n.id + '" ' + is_selected + '>' + n.courseTitle + '</option>';
                });
                $('.type_' + _eq).find('.ajax_search_list').html(_option);
                option_cache = $('.type_' + _eq).find('.ajax_search_list option');
            }
        } else if ($('.type_' + _eq).attr('data-action') == 'Live') {
            var _option = '';
            arr_list_live = {
                page: page - 1,
                size: '99999',
                live_master_name: '',
                operator_id: '',
                status: ''
            }
            var select_live_list = sessionStorage.getItem('select_live_list');
            select_live_list = JSON.parse(select_live_list);
            if (select_live_list == '' || select_live_list == null) {
                $.post(url_list_live, arr_list_live, function (data) {
                    console.log(data);
                    if (data.code == '0') {
                        sessionStorage.setItem('select_live_list', JSON.stringify(data.data.data));
                        $.each(data.data.data, function (i, n) {
                            if (_target == n.id) {
                                is_selected = 'selected';
                            } else {
                                is_selected = '';
                            }
                            _option += '<option value="' + n.id + '" ' + is_selected + ' >' + n.liveMasterName + '</option>'
                        });
                        $('.type_' + _eq).find('.ajax_search_list').html(_option);
                        option_cache = $('.type_' + _eq).find('.ajax_search_list option');
                    } else {
                        _alert_warning(data.msg);
                    }
                });
            } else {
                $.each(select_live_list, function (i, n) {
                    if (_target == n.id) {
                        is_selected = 'selected';
                    } else {
                        is_selected = '';
                    }
                    _option += '<option value="' + n.id + '" ' + is_selected + ' >' + n.liveMasterName + '</option>'
                });
                $('.type_' + _eq).find('.ajax_search_list').html(_option);
                option_cache = $('.type_' + _eq).find('.ajax_search_list option');
            }
        } else if ($('.type_' + _eq).attr('data-action') == 'Article') {
            var _option = '';
            var arr_list_article = {
                page: '',
                size: '99999',
                article_title: '',
                article_update_id: '',
                article_online: '',
                sort_status: 'desc',
                type_id: ''
            }
            var select_article_list = sessionStorage.getItem('select_article_list');
            select_article_list = JSON.parse(select_article_list);
            if (select_article_list == '' || select_article_list == null) {
                $.post(url_list_article, arr_list_article, function (data) {
                    console.log(data);
                    if (data.code == '0') {
                        sessionStorage.setItem('select_article_list', JSON.stringify(data.data.data));
                        $.each(data.data.data, function (i, n) {
                            if (_target == n.id) {
                                is_selected = 'selected';
                            } else {
                                is_selected = '';
                            }
                            _option += '<option value="' + n.id + '" ' + is_selected + '>' + n.articleTitle + '</option>'
                        });
                        $('.type_' + _eq).find('.ajax_search_list').html(_option);
                        option_cache = $('.type_' + _eq).find('.ajax_search_list option');
                    } else {
                        _alert_warning(data.msg);
                    }
                });
            } else {
                $.each(select_article_list, function (i, n) {
                    if (_target == n.id) {
                        is_selected = 'selected';
                    } else {
                        is_selected = '';
                    }
                    _option += '<option value="' + n.id + '" ' + is_selected + '>' + n.articleTitle + '</option>'
                });
                $('.type_' + _eq).find('.ajax_search_list').html(_option);
                option_cache = $('.type_' + _eq).find('.ajax_search_list option');
            }
        } else if ($('.type_' + _eq).attr('data-action') == 'Goods') {
            var _option = '';
            console.log('goods');
            /////////////////////////
        } else {}
    });

    $('.new_ad_add').click(function () {
        if (lk > 0) {
            return false;
        } else {
            lk = 1;
            setTimeout(function () {
                lk = 0;
            }, 3000);
            var _this_url;
            if ($('#ad_add_choose').attr('data-action') == 'edit') {
                _this_url = url_upd;
            } else {
                _this_url = url_add;
            }
            var formData = new FormData($("#ad_add_choose")[0]);
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
                    if ($('.ad_title').val().trim() == '') {
                        _alert_warning('标题不能为空！');
                        $('.ad_title').focus();
                        return false;
                    }
                    if ($('.ad_file').val().trim() == '') {
                        if ($('#ad_add_choose').attr('data-action') == 'add') {
                            _alert_warning('请添加图片！');
                            return false;
                        }
                    }
                    if ($('.aH5Url[name=aH5Url]').val() == '' || $('.aH5Url[name=aH5Url]').val() == null || $('.aH5Url[name=aH5Url]').val() == undefined) {
                        _alert_warning('链接不能为空！');
                        return false;
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
                },
                error: function (data) {
                    _alert_warning(data.msg);
                }
            }, "json");
        }
    });
    $('.ajax_search').keyup(function () {
        var _div = $(this).parents('.type_choose');
        var reg = new RegExp($(this).val());
        var _new_option = '';
        if ($(this).val().trim() !== '') {
            $.each(option_cache, function (k, v) {
                if (reg.test($(v).html())) {
                    _new_option += '<option  value="' + $(v).attr('value') + '">' + $(v).html() + '</option>';
                }
            });
        } else {
            $.each(option_cache, function (k, v) {
                _new_option += '<option  value="' + $(v).attr('value') + '">' + $(v).html() + '</option>';
            });
        }
        _div.find('.ajax_search_list').html(_new_option);
    });
    $('.new_ad_cancel').click(function () {
        cancel();
    });

    function cancel() {
        $('.ad_add').hide();
        img_cache = '';
        $('.type_choose').attr('data-val', '');
        $("#ad_add_choose")[0].reset();
        $('.ad_preview').attr('src', 'dist/img/add.png');
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