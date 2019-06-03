var api_back = sessionStorage.getItem('api_back');
var server_course = sessionStorage.getItem('server_course');
var url_options = api_back + '/course/cms/options';
var url_findAll = api_back + '/courseMemoryLoad/findAll';
var url_add = api_back + '/courseMemory/add';
var url_remove = api_back + '/courseMemory/remove';
var lk = 0;
var creater = '<option value="">--更新人--</option>';
var user_list = sessionStorage.getItem('user_list');
user_list = JSON.parse(user_list);

to_page();
$.ajax({
    url: url_options,
    type: 'POST',
    data: '',
    async: false,
    cache: false,
    contentType: false,
    processData: false,
    dataType: "json",
    timeout: 50000,
    beforeSend: function () {},
    success: function (data) {
        if (data.code == '0') {
            sessionStorage.setItem('course_options', JSON.stringify(data.data));
        }
    },
    complete: function (data) {
        setTimeout(function(){},100);
        course_options = sessionStorage.getItem('course_options');
        course_options = JSON.parse(course_options);
        if (course_options == '' || course_options == null || course_options == undefined) {
            alert('获取不到列表');
        } else {
            var _option = '<option value="">请选择</option>';
            $.each(course_options, function (k, v) {
                _option += '<option data-value=' + v.courseTitle + ' value="' + v.id + '">' + v.courseTitle + '</option>';
            });
            $('.new_memory_title').html(_option);
        }

    },
    error: function (data) {
        console.log(data);
    }
}, "json");


function to_page() {
    $.post(url_findAll, '', function (data) {
        var this_no;
        var tr;
        var this_title;
        //console.log(data);
        if (data.code == '0') {
            var content = data.data;
            if (content['0'] == '' || content['0'] == undefined) {
                tr += '<tr><td colspan="3">暂无数据</td></tr>';
            } else {
                $.each(content, function (i, n) {
                    this_no = i + 1;
                    if(n.titleName == null || n.titleName == 'null'){
                        this_title = '未获取到标题';
                    }else{
                        this_title = n.titleName
                    }
                    tr += '<tr data-id="' + n.courseId + '"><td>' + this_no + '</td><td>' + this_title + '</td><td><a data-id="' + n.courseId + '" href="javascript:void(0);" class="courseMemoryRemove"><i class="icon-trash"></i></a></td></tr>';
                });
            }
            $('.course_memory_list tbody').html(tr);
        }
    });
}
$('.course_memory_add').click(function () {
    $('.memory_add').fadeIn();
});
$('.new_memory_cancel').click(function () {
    $('.memory_add').hide();
});
$('.memory_title_search').click(function () {
    var key_words = $('.memory_course_title ').val();
    var reg = new RegExp(key_words);
    var option = '';
    if (key_words !== '') {
        $.each(course_options, function (k, v) {
            if (reg.test(v.courseTitle)) {
                option += '<option data-value=' + v.courseTitle + ' value="' + v.id + '">' + v.courseTitle + '</option>';
            }
        });
    } else {
        $.each(course_options, function (k, v) {
            option += '<option data-value=' + v.courseTitle + ' value="' + v.id + '">' + v.courseTitle + '</option>';
        });
    }
    $('.new_memory_title').html(option);
});
$('.new_memory_add').click(function () {
    if ($('.new_memory_title').val() == '' || $('.new_memory_title').val() == null) {
        alert('请选择正确的文章');
    } else {
        console.log($('.new_memory_title').val());
        var arr_add = {
            courseId: $('.new_memory_title').val()
        }
        $.post(url_add, arr_add, function (data) {
            if (data.code == '0') {
                alert('添加成功');
                window.location.href = "./courseMemory.html";
            } else {
                alert('添加出错了');
            }
        });
        $('.memory_add').hide();
    }
});


$(".course_memory_list").on('click', '.courseMemoryRemove', function () {
    if (confirm('确认删除？')) {
        console.log($(this).attr('data-id'));
        var arr_remove = {
            courseId: $(this).attr('data-id')
        }
        $.post(url_remove, arr_remove, function (data) {
            if (data.code == '0') {
                window.location.href = "./courseMemory.html";
            } else {
                alert('删除出错');
            }
        });
    } else {}
});