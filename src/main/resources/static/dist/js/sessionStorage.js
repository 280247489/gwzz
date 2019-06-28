$(document).ready(function () {
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    //$('.curr_admin_name').html(user.name);
    $('.curr_admin_name').html('管理员');
    if(user == null){
        window.location.href="./login.html";
        return false;
    }
    $('#logout').click(function(){
        sessionStorage.clear();
        window.location.href="./login.html";
        return false;
    });
});
