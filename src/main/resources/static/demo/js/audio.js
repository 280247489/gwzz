$(document).ready(function () {
    $('.content').on('click', '.audioPlayer', function () {
        var msg = $(this).parent().parent();
        audio = msg.find('audio');
        //msg.parent().find('.p').removeClass('r');
        initAudioEvent(audio);
    });
});



function play_next(curr) {
    var total = $('.content').find('audio').length;
    //播放下一条音频
    if (curr >= total - 1) {
        console.log('没了');
        return false;
    }
    next = $('.content').find('audio').eq(curr + 1);
    next.parent().find('.audioPlayer').trigger('click');
}
function initAudioEvent(curr_audio) {
    var this_offset = curr_audio.parent().parent().parent().offset().top;
    var this_scroll = $(window).scrollTop();
    var this_ht = $(window).height()
    audio = curr_audio.get(0);
    var curr = $('audio').index(audio); //当前序号
    //console.log(curr_audio);
    // 点击播放/暂停图片时，控制音乐的播放与暂停
    // 改变播放/暂停图片
    if (audio.paused) {
        // 开始播放当前点击的音频
        audio.play();
        if(audio.paused){
        }else{
            curr_audio.parent().find('.audioPlayer').attr('src', './images/pause.png');
            curr_audio.parent().parent().find('.p').removeClass('r');
        }
    } else {
        audio.pause();
        curr_audio.parent().find('.audioPlayer').attr('src', './images/play.png');
    }
    if (this_scroll + this_ht < this_offset + 77) {
        var _offset = this_offset + 180 - this_ht;
        $('body,html').animate({
            scrollTop: _offset
        }, 300);
    } else if (this_scroll > this_offset) {
        console.log('在屏幕上方');
    }

    // function play_next(curr) {
    //     var total = $('.content').find('audio').length;
    //     //播放下一条音频
    //     if (curr >= total - 1) {
    //         console.log('没了');
    //         return false;
    //     }
    //     //alert(curr);

    //     next = $('.content').find('audio').eq(curr + 1);
    //     next_audio = next.get(0);
    //     initAudioEvent(next);
    //     next.parent().parent().find('.p').removeClass('r');
    // }

    $('.content').find('audio').not(curr_audio).each(function () {
        //播放时初始化其他音频
        var _audio = $(this).get(0);
        _audio.currentTime = 0;
        updateProgress(_audio);
        audioEnded(_audio);
        if (_audio.paused) {} else {
            _audio.pause();
            $(this).parent().find('.audioPlayer').attr('src', './images/play.png');
        }
    });
    
    // 监听音频播放时间并更新进度条
    audio.addEventListener('timeupdate', function () {
        updateProgress(audio);
    }, false);
    // 监听播放完成事件
    audio.addEventListener('ended', function () {
        audioEnded(audio);
        play_next(curr);
    }, false);


    $('.progressBarBg').on('mousedown', function (e) {
        if (!audio.paused || audio.currentTime != 0) {
            var pgsWidth = $('.progress-bar-bg').width();
            var rate = e.offsetX / pgsWidth;
            audio.currentTime = audio.duration * rate;
            updateProgress(audio);
        }
    });
}


/**
 * 更新进度条与当前播放时间
 * @param {object} audio - audio对象
 */
function updateProgress(audio) {
    var JQaudio = $(audio);
    var value = audio.currentTime / audio.duration;
    JQaudio.parent().find('.progressBar').css('width', value * 100 + '%');
    JQaudio.parent().find('.progressDot').css('left', value * 100 + '%');
    //JQaudio.parent().find('.audioCurTime').html(transTime(audio.currentTime));
}

/**
 * 播放完成时把进度调回开始的位置
 */
function audioEnded(audio) {
    var JQaudio = $(audio);
    JQaudio.parent().find('.progressBar').css('width', 0);
    JQaudio.parent().find('.progressDot').css('left', 0);
    //JQaudio.parent().find('.audioCurTime').html(transTime(0));
    JQaudio.parent().find('.audioPlayer').attr('src', './images/play.png');
}