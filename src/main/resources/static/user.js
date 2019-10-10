$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var edit = false;

    $("#submit").click(function(){
        var username = $("#username").val();
        var password = $("#password").val();
        var role = $("#role").val();
        var postedUserDTO = { username: username, password: password, role: role };

        if(edit){
            editUser(postedUserDTO);
        } else {
            createUser(postedUserDTO);
        }
    });

    function createUser(postedUserDTO){
        $.ajax({
            url: "/users",
            method: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postedUserDTO),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log(result);
                edit = false;
                $("#username").val("");
                $("#role").val("");
                $("#password").val("");
            },
            error: function(){
                console.log("Error");
            }
        })
    }

    function editUser(postedUserDTO){
        $.ajax({
            url: "/users",
            method: "PUT",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postedUserDTO),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log(result);
                edit = false;
                $("#username").val("");
                $("#role").val("");
                $("#password").val("");
            },
            error: function(){
                console.log("Error");
            }
        })
    }

    $(".edit").click(function(){
        var element = jQuery(this).closest("tr");
        var username = element.find(".username").text();
        var role = element.find(".role").text();

        $("#username").val(username);
        $("#role").val(role);
        edit=true;
    });

    $(".delete").click(function(){
        var element = jQuery(this).closest("tr");
        var username = element.find(".username").text();

        $.ajax({
            url: "/users/"+username,
            method: "DELETE",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                element.hide();
            },
            error: function(){
                console.log("Error");
            }
        })
    })


})
