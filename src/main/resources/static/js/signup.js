$(function(){

    $(document).on("click", "#submit", function(){
        var username = $("#username").val();
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        if(!validateForm(username, password, confirmPassword)){
            return;
        }
        var userDetailsDTO = {
            username: username,
            password: password
        }

        $.ajax({
            url: "/signup",
            method: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(userDetailsDTO),
            success: function(result){
                location.reload(true);
            },
            error: function(e){
                alert(e.responseJSON.message);
            }
        })
    })

    function validateForm(username, password, confirmPassword){
        if(!username && username===""){
            alert("Username is required");
            return false;
        }
        if(!password && password===""){
            alert("Password is required");
            return false;
        }

        if(password !== confirmPassword){
            alert("Confirm password and password don't match");
            return false;
        }

        return true;

    }

})