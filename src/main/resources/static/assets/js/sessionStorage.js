$(document).ready(function () {
    var user = sessionStorage.getItem('user');
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
