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

    var action = $_GET['action'];
    var s_id = $_GET['id'];
    var API_shezhen = sessionStorage.getItem('API_shezhen');
    if (action == '' || action == undefined) {
        return false;
    } else if (action == 'add') {
        var url_1 = API_shezhen + '/szMaster/add';
        console.log(url_1);
        $('.symptom_add_submit').click(function () {
            var symptom_add_name = $('.symptom_add_name').val().trim();
            var symptom_add_explain = $('.symptom_add_explain').val().trim();
            var symptom_add_remark = $('.symptom_add_remark').val().trim();
            if (symptom_add_name == '') {
                alert('症状名称不能为空！');
                $('.symptom_add_name').focus();
            } else if (symptom_add_explain == '') {
                alert('症状描述不能为空！');
                $('.symptom_add_explain').focus();
            } else {
                arr = {
                    zybzName: symptom_add_name,
                    zybzExplain: symptom_add_explain,
                    remark: symptom_add_remark
                }
                $.post(url_1, arr, function (data) {
                    var code = data['code'];
                    if (code == 0) {
                        alert('添加成功！');
                        window.location.href = "./symptom.html";
                        return false;
                    } else {
                        alert('添加错误！');
                        return false;
                    }
                });

            }
        });

    } else if (action == 'edit') {
        $('.symptom_add_submit').html('修改');
        var url_1 = API_shezhen + '/szMaster/upd';
        var symptom_list = sessionStorage.getItem('symptom_list');
        symptom_list = JSON.parse(symptom_list);
        $.each(symptom_list, function (k, v) {
            if (v.id == s_id) {
                $('.symptom_add_name').val(v.zybzName);
                $('.symptom_add_explain').val(v.zybzExplain);
                $('.symptom_add_remark').val(v.remark);
            } else {}
        });
        $('.symptom_add_submit').click(function () {
            var symptom_add_name = $('.symptom_add_name').val().trim();
            var symptom_add_explain = $('.symptom_add_explain').val().trim();
            var symptom_add_remark = $('.symptom_add_remark').val().trim();
            if (symptom_add_name == '') {
                alert('症状名称不能为空！');
                $('.symptom_add_name').focus();
            } else if (symptom_add_explain == '') {
                alert('症状描述不能为空！');
                $('.symptom_add_explain').focus();
            } else {
                arr = {
                    id: s_id,
                    zybzName: symptom_add_name,
                    zybzExplain: symptom_add_explain,
                    remark: symptom_add_remark
                }
                $.post(url_1, arr, function (data) {
                    var code = data['code'];
                    if (code == 0) {
                        alert('修改成功！');
                        window.location.href = "./symptom.html";
                        return false;
                    } else {
                        alert('修改错误！');
                        return false;
                    }
                });
            }
        });
    } else {
        return false;
    }



});