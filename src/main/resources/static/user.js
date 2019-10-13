$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var edit = false;

    $(document).on("click", "#submit", function(){
    console.log("submitting");
        var username = $("#username").val();
        var password = $("#password").val();
        var role = $("#role").val();
        var postedUserDTO = { username: username, password: password, role: role };
        if(!formIsValid()){
            return;
        }
        console.log("something here");
        if(edit){
            editUser(postedUserDTO);
        } else {
            createUser(postedUserDTO);
        }
    });

    function createUser(postedUserDTO){
        console.log("creating");
        $.ajax({
            url: "/admin/users",
            method: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postedUserDTO),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                location.reload(true);
            },
            error: function(e){
                alert(e.responseJSON.message);
            }
        })
    }

    function formIsValid(){
        if(!$("#username").val() || $("#username").val().trim()===""){
            alert("Username is required");
            return false;
        }
        if(!$("#password").val() || $("#password").val().trim()===""){
            alert("Password is required");
            return false;
        }
        if(!$("#role").val() || $("#role").val().trim()===""){
            alert("Role is required");
            return false;
        }

        return true;
    }

    function editUser(postedUserDTO){
        $.ajax({
            url: "/admin/users",
            method: "PUT",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postedUserDTO),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                location.reload(true);
            },
            error: function(e){
                alert(e.responseJSON.message);
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
            url: "/admin/users/"+username,
            method: "DELETE",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                location.reload(true);
            },
            error: function(e){
                alert(e.responseJSON.message);
            }
        })
    })


})
