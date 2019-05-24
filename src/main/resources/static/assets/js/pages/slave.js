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
    var API_shezhen = sessionStorage.getItem('API_shezhen');
    var server_shezhen = sessionStorage.getItem('server_shezhen');
    var url_1 = API_shezhen + '/szSlave/listBySzMasterId';
    var symptom_list = sessionStorage.getItem('symptom_list');
    symptom_list = JSON.parse(symptom_list);
    //console.log(symptom_list);
    if (symptom_list == null || symptom_list == '') {
        var url_0 = API_shezhen + 'szMaster/list';
        arr_0 = {
            page: 0,
            size: 50,
            direction: 'desc',
            sorts: '',
            zybzName: ''
        }
        $.post(url_0, arr_0, function (data) {
            var code = data['code'];
            if (code == 0) {
                var content = data['data']['content'];
                sessionStorage.setItem('symptom_list', JSON.stringify(content));
                symptom_list = content;
            }else{
                console.log('症状列表异常！');
            }
        });
    }

    var page = parseInt($_GET['page']);
    var per_page = '30';
    var list_no = '';
    if (page == '' || page == undefined || isNaN(page)) {
        page = 1;
    } else {}

    arr = {
        page: page - 1,
        size: per_page,
        direction: 'desc',
        sorts: '',
        szZybzMasterId: ''
    }
    $.post(url_1, arr, function (data) {
        function this_page(data) {
            console.log(data);
            var tr = '';
            var code = data['code'];
            if (code == 0) {
                var content = data['data']['content'];
                sessionStorage.setItem('slave_list', JSON.stringify(content));
                var pageNumber = data['data']['pageable']['pageNumber'];
                if (data['data']['content']['0'] == '' || data['data']['content']['0'] == undefined) {
                    tr = '<tr><td colspan="4">暂无数据</td></tr>';
                } else {
                    list_no = (per_page * pageNumber) + 1;
                    $.each(content, function (i, n) {
                        var m = n.szZybzMasterId;
                        var m_name;
                        $.each(symptom_list,function(k,v){
                            if(v.id == m){
                                m_name = v.zybzName;
                            }
                        });
                        this_no = list_no + i;tr += '<tr data-id="'+ n.id +'"><td data-master-id="'+ n.szZybzMasterId +'">' + this_no + '</td><td>'+ m_name +'</td><td><img class="slave_img" src="'+ server_shezhen + n.img1 +'" alt="'+m_name+'" /></td><td>'+ n.addTime +'</td><td>'+ n.imgRemarks +'</td><td><button class="btn btn-primary btn-xs slave_edit"><i class="icon-pencil"></i></button>&nbsp;&nbsp;<button class="btn btn-danger btn-xs slave_del"><i class="icon-trash"></i></button></td></tr>';
                    });
                    $('.slave_list tbody').html(tr);
                }
            }
        }
        this_page(data);
        new Page({
            id: 'pagination',
            pageTotal: data['data']['totalPages'], //必填,总页数
            pageAmount: per_page, //每页多少条
            dataTotal: data['data']['totalElements'], //总共多少条数据
            curPage: 1, //初始页码,不填默认为1
            pageSize: 5, //分页个数,不填默认为5
            showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
            showSkipInputFlag: true, //是否支持跳转,不填默认不显示
            getPage: function (page) {
                arr = {
                    page: page - 1,
                    size: per_page,
                    direction: 'desc',
                    sorts: '',
                    szZybzMasterId: ''
                }
                $.post(url_1, arr, function (data) {
                    this_page(data);
                });

            }
        });
    });
    $('.slave_list').on('click', '.slave_edit', function () {
        var id = $(this).parent().parent().attr('data-id');
        window.location.href = "./slaveAdd.html?action=edit&id="+id;
    });



});