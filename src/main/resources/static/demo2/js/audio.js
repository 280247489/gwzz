$(document).ready(function () {
    $('.content').on('click', '.ma .a', function () {
        audio = $(this).find('audio');
        initAudioEvent(audio);
    });
});

function initAudioEvent(curr_audio) {
    var this_offset = curr_audio.parent().parent().parent().offset().top;
    var this_scroll = $(window).scrollTop();
    var this_ht = $(window).height()
    audio = curr_audio.get(0);
    var curr = $('audio').index(audio);//当前序号

    if(this_scroll + this_ht < this_offset + 77){
        var _offset = this_offset  + 180  - this_ht;
        $('body,html').animate({scrollTop:_offset},300);
    }else if(this_scroll > this_offset){
        console.log('在屏幕上方');
    }

    var total = $('.content').find('audio').length;
    function play_next(curr) {
        if(curr >= total - 1){
            console.log('没了');
            return false;
        }
        next = $('.content').find('audio').eq(curr + 1);
        next_audio = next.get(0);
        initAudioEvent(next);
    }


    $('.content').find('audio').not(curr_audio).each(function () {
        var _audio = $(this).get(0);
        _audio.currentTime = 0;
        if (_audio.paused) {
        } else {
            _audio.pause();
            $(this).parent().find('.audioPlayer').attr('src', './images/sd.png');
        }
    });

    if (audio.paused) {
        audio.play();
        if(audio.paused){
        }else{
            curr_audio.parent().find('.audioPlayer').attr('src', './images/sd.gif');
            curr_audio.parent().parent().find('.p').removeClass('r');
        }
    }else {
        audio.pause();
        curr_audio.parent().find('.audioPlayer').attr('src', './images/sd.png');
    }

    // 监听播放完成事件
    audio.addEventListener('ended', function () {
        audio.currentTime = 0;
        $(audio).parent().find('.audioPlayer').attr('src', './images/sd.png');
        play_next(curr);
    }, false);

    
    
}





