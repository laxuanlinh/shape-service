$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $("#submit").click(function(){
        let username = $("#username").val();
        let password = $("#password").val();
        let role = $("#role").val();
        let postedUserDTO = {
                                username: username,
                                password: password,
                                role: role
                            };

        $.ajax({
            url: "/users",
            method: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postedUserDTO),
            beforeSend: function(xhr){
                console.log(header);
                console.log(token);
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log(result);
            },
            error: function(){
                console.log("Error");
            }
        })
    });
})
