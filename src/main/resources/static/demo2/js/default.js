$(document).ready(function () {
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
    var url = 'http://manager.houaihome.com/courseExt/web/list';
    var server_course = 'http://image.houaihome.com/cms/';
    var id = $_GET['id'];
    if (id == '' || id == undefined) {
        alert('错误的地址');
        return false;
    }
    var ht = document.documentElement.clientHeight;
    $('.popupimg').css('max-height', ht);
    var view = sessionStorage.getItem('view');
    if (view == '1') {
        $('.bg').hide();
    }

    function _change() {
        sessionStorage.setItem("view", "1");
        $('.bg').animate({
            marginTop: "-240%"
        }, 2048);
        enable_scroll();
    }
    var arr = {
        courseId: id
    }
    $.ajax({
        url: url,
        type: 'POST',
        data: arr,
        async: false,
        cache: false,
        dataType: "json",
        timeout: 50000,
        beforeSend: function () {},
        success: function (data) {
            if (data.recode == '0') {
                if (data.data !== null) {
                    $('.block h3').html(data.data.course);
                    var _content = data.data.courseExt;
                    var avatar = '';
                    //var int_first = 1;
                    $.each(_content, function (i, n) {
                        var _img_url = n.courseExtImgUrl.substr(0, 4);
                        if (_img_url == 'http') {
                            var this_img_url = n.courseExtImgUrl;
                        } else {
                            var this_img_url = server_course + n.courseExtImgUrl;
                        }
                        var _ado_url = n.courseExtAudio.substr(0, 4);
                        if (_ado_url == 'http') {
                            var this_ado_url = n.courseExtAudio;
                        } else {
                            var this_ado_url = server_course + n.courseExtAudio;
                        }
                        if (n.courseExtNickname == '李老师') {
                            avatar = './images/avatar.png';
                        } else if (n.courseExtNickname == '曦老师') {
                            avatar = './images/logo.png';
                        }
                        var block = n.courseExtAudioTimes * 2;
                        if (n.courseExtType == '1') {
                            $('.content').append('<div  class="msg mw"><div class="h"><img src="' + avatar + '" /></div><div class="fl"><h3 class="n">' + n.courseExtNickname + '</h3><div class="c w">' + n.courseExtWords + '<span class="t"></span></div></div></div>');
                        } else if (n.courseExtType == '2') {
                            $('.content').append('<div  class="msg ma"><div class="h"><img src="' + avatar + '" /></div><div class="fl"><h3 class="n">' + n.courseExtNickname + '</h3><div class="c a"><audio><source src="' + this_ado_url + '" type="audio/mp3"></audio><div class="audio-left"><img src="./images/sd.png" class="audioPlayer" /></div><div class="audio-right"><span class="audio_times">' + n.courseExtAudioTimes + ' "</span><span class="time_block" style="width:'+ block +'px"></span></div><span class="t"></span></div><span class="r p"></span></div></div>');
                        } else if (n.courseExtType == '3') {
                            $('.content').append('<div class="msg mi "><div class="h"><img src="' + avatar + '" /></div><div class="fl"><h3 class="n">' + n.courseExtNickname + '</h3><div class="c i"><a href="javascript:void(0);" rel="popuprel"  title="" class="popup"><img class="" src="' + this_img_url + '" ></a><span class="t"></span></div></div></div>');
                        } else {

                        }
                    });
                }
            } else {
                console.log('出错了');
            }
        },
        complete: function (data) {
            if (view == null || view == '' || view == undefined) {
                disable_scroll();
                setTimeout(_change, 1024);
            } else if (view == '1') {
                $('.bg').hide();
            }else{
                $('.bg').hide();
            }
        },
        error: function (data) {
            console.log('4');
            console.log(data);
        }
    }, "json");




    function show_time() {
        var myDate = new Date();
        var mytime = myDate.toLocaleTimeString();
        $('.content').append('<div class="time_show normal">' + mytime + '</div>');
        //scroll_bottom();
    }

    function scroll_bottom() {
        $('html,body').animate({
            scrollTop: $('.bottom').offset().top
        }, 500);
    }


    $('.content').on('click', 'a.popup', function () {
        var popupid = $(this).attr('rel');
        var img = $(this).find('img').attr('src');
        $('.popupimg').attr("src", img);
        $('#' + popupid).fadeIn();
        $('#fade').css({
            'filter': 'alpha(opacity=70)'
        }).fadeIn(100);
        var popuptopmargin = ($('#' + popupid).height()) / 2;
        var popupleftmargin = ($('#' + popupid).width()) / 2;
        $('#' + popupid).css({
            'margin-top': -popuptopmargin,
            'margin-left': -popupleftmargin
        });
    });
    $('#fade, #popuprel').click(function () {
        $('#fade , #popuprel , #popuprel2 , #popuprel3').fadeOut(100);
        return false;
    });

});

var keys = [37, 38, 39, 40];

function preventDefault(e) {
    e = e || window.event;
    if (e.preventDefault)
        e.preventDefault();
    e.returnValue = false;
}

function keydown(e) {
    for (var i = keys.length; i--;) {
        if (e.keyCode === keys[i]) {
            preventDefault(e);
            return;
        }
    }
}

function wheel(e) {
    preventDefault(e);
}

function disable_scroll() {
    if (window.addEventListener) {
        window.addEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = wheel;
    document.onkeydown = keydown;
}

function enable_scroll() {
    if (window.removeEventListener) {
        window.removeEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = document.onkeydown = null;
}