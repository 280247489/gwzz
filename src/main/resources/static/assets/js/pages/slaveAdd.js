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
    var url_0 = API_shezhen + '/szMaster/list';
    arr_0 = {
        page: 0,
        size: 50,
        direction: 'desc',
        sorts: '',
        zybzName: ''
    }
    $.post(url_0, arr_0, function (data) {
        var option = '<option value="" data-id="">—请选择—</option>';
        var code = data['code'];
        var content = data['data']['content'];
        if (code == 0) {
            sessionStorage.setItem('symptom_list', JSON.stringify(content));
            $.each(content, function (i, n) {
                option += '<option value="' + n.zybzName + '" data-id="' + n.id + '">' + n.zybzName + '</option>';
            });
            $('.symptom_select').html(option);
        }
    });
    $('.symptom_select').change(function () {
        data = $('.symptom_select option:selected').attr('data-id');
        $(this).attr("data-select-id", data);
        $('#szZybzMasterId').val(data);
    });

    if (action == '' || action == undefined) {
        return false;
    } else if (action == 'add') {
        var url_1 = API_shezhen + '/szSlave/add';
        $('#slaveId').remove();
        $('.slave_add_submit').click(function () {
            var img = $('#slave_add_img')[0].files[0];
            var szZybzMasterId = $('#szZybzMasterId').val();
            if (szZybzMasterId == '') {
                alert('症状未选择！');
            } else if (img == undefined) {
                alert('图片未选取！');
            } else {
                tj(url_1);
            }
        });

    } else if (action == 'edit') {
        $('.slave_add_submit').html('修改');
        var slave_list = sessionStorage.getItem('slave_list');
        slave_list = JSON.parse(slave_list);
        var symptom_list = sessionStorage.getItem('symptom_list');
        symptom_list = JSON.parse(symptom_list);
        $.each(slave_list, function (k, v) {
            if (v.id == s_id) {
                setTimeout(function () {
                    $('.symptom_select').find('option').each(function () {
                        var this_id = $(this).attr('data-id');
                        if (this_id == v.szZybzMasterId) {
                            $(this).attr("selected", "selected");
                        }
                    });
                }, 200);
                $('.symptom_select').attr("disabled", "disabled");
                $('#slaveId').val(v.id);
                $('.imgRemarks').val(v.imgRemarks);
            } else {}
        });
        var url_2 = API_shezhen + '/szSlave/upd';
        $('.slave_add_submit').click(function () {
            var img = $('#slave_add_img')[0].files[0];
            var szZybzMasterId = $('#szZybzMasterId').val();
            if (szZybzMasterId == '') {
                alert('症状未选择！');
            } else if (img == undefined) {
                alert('图片未选取！');
            } else {
                tj(url_2);
            }
        });
    } else {
        return false;
    }

    function tj(url) {
        var formData = new FormData($("#slave_add_form")[0]);
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            beforeSend: function () {},
            success: function (data) {
                var recode = data.code;
                if (recode == 0) {
                    alert('提交成功！');
                    window.location.href = "./slave.html";
                } else {
                    alert('提交错误！');
                    console.log(data);
                    return false;
                }
            },
            error: function (data) {
                alert('error！');
                //console.log(data);
            }
        }, "json");
    }







});