jQuery(document).ready(function () {
    $('.search_form .form-control').focus(function () {
        $(this).bind('keypress', function (event) {
            if (event.keyCode == "13") {
                $(this).parents('form').find(".search_submit").trigger("click");
                return false;
            }
        })
    });
    var Version = '0.1.3'; //版本号
    var Copyright = '<strong>Copyright &copy; 吉林省后爱电子商务有限公司';
    $('.main-footer').html('<div class="pull-right hidden-xs"><b>Version</b> '+ Version +'</div>' + Copyright);
    $.ajax({
        type: "get",
        url: "_sidebar.html", //需要获取的页面内容
        async: true,
        success: function (data) {
            $('.sidebar').html(data);
        },
        complete: function () {
            if($('body').attr('data-tag') !== ''){
                $('.sidebar-menu').find('.'+$('body').attr('data-tag')).addClass('active');
            }
            $('.sidebar-menu').find('.'+$('body').attr('data-bar')).addClass('active');
        },
        error: function (data) {
        }
    });
    $.ajax({
        type: "get",
        url: "_header.html", //需要获取的页面内容
        async: true,
        success: function (data) {
            $('.main-header').html(data);
        },
        complete: function () {
            $('.sidebar-menu').tree();
        },
        error: function (data) {
        }
    });
    $('.table,.load_table,.opinion_img').on('mouseover mouseout', '.img_prev', function (event) {
        if (event.type == "mouseover") {
            var bigUrl = $(this).attr("src");
            $(this).parent().append("<div id='pic'><img src='" + bigUrl +
                "' id='pic1'></div>");
            $('.table,.load_table,.opinion_img').on('mousemove', '.img_prev', function (e) {
                var wH = document.documentElement.clientHeight
                var wW = document.documentElement.clientWidth
                var imgW = $("#pic1").width()
                var imgH = $("#pic1").height()
                var cssArr = {
                    "top": "",
                    "left": "",
                    "bottom": "",
                    "right": ""
                }

                if (e.clientX + imgW > wW) {
                    if (wW - e.clientX < imgW) {
                        cssArr.left = (e.clientX - imgW - 10) + "px";;

                    } else {
                        cssArr.right = 0;
                    }

                } else {
                    cssArr.left = (e.clientX + 10) + "px";
                }

                if (e.clientY + imgH > wH) {
                    cssArr.bottom = 0;
                } else {
                    cssArr.top = (e.clientY + 10) + "px";
                }
                $("#pic").css(cssArr).fadeIn("fast");
                //console.log($("#pic1").height(),wH);
            });
        } else if (event.type == "mouseout") {
            //鼠标离开
            $("#pic").remove();
        }
    });

});










function loading() {
    var l = '<div class="loading"><div class="loading_mask"></div><div class="loading_box"><div class="square-move"><i class="move"></i><i class="move"></i><i class="move"></i></div></div></div>';
    $('.content-wrapper').append(l);
}

function loading_end() {
    $('.loading').remove();
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