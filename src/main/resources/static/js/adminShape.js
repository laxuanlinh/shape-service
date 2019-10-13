$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var edit = false;
    var dimensions=[];

    showDimensions();

    $(document).on("change", "#shapeCategoryName", function(e){
        showDimensions();
    })

    function showDimensions(){
        $("#sizes").html("");
        var selectData = $("#shapeCategoryName").find(':selected').data('dimensions')
        dimensions = selectData.substring(1, selectData.length-1).replace(" ","").split(",");
        for(dimension of dimensions){
            $("#sizes").append(" " + dimension + ": <input type='number' name='"+dimension.trim()+"' class='dimension form-control' id='" + dimension.trim() + "'/>");
        }
    }

    $(document).on("click", "#submit", function(e){
        var shapeName = $("#shapeName").val();
        var shapeCategoryName = $("#shapeCategoryName").val();
        var username = $("#username").val();
        var sizes = {};
        var id = $("#id").val();
        if(!formIsValid()){
            return;
        };

        for(dimension of dimensions){
            var sizeValue = $('.dimension#' + dimension.trim()).val();
            if(sizeValue)
                sizes[dimension] = sizeValue;
        }
        var data = {
            id:id,
            shapeName: shapeName,
            sizes: sizes,
            shapeCategory: {
                shapeCategoryName: shapeCategoryName
            },
            user: {
                username: username
            }
        }

        submitShape(data);
    });

    $(document).on("click", ".edit", function(){
        var element = jQuery(this).closest(".shape");
        $("#id").val(element.data("id"));
        $("#shapeName").val(element.find(".shapeName").text());
        $("#shapeCategoryName").val(element.find(".shapeCategoryName").text());
        $("#username").val(element.find(".username").text());
        $("#shapeCategoryName").trigger("change");

        var sizes = {};
        element.find(".sizes").find("p").each(function(){
            sizes[$(this).find(".sizeKey").text()] = $(this).find(".sizeValue").text();
        });
        for(var size in sizes){
            $("#"+size).val(sizes[size]);
        }
        edit = true;
    })

    $(document).on("click", ".delete", function(){
        var element = jQuery(this).closest(".shape");
        $.ajax({
            url: "/admin/shapes/" + element.data("id"),
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

    function formIsValid(){
        if(!$("#shapeName").val() || $("#shapeName").val().trim("")===""){
            alert("Shape name is required");
            return false;
        }
        for(dimension of dimensions){
            var sizeValue = $('.dimension#' + dimension.trim()).val();
            if(!sizeValue){
                alert(""+dimension+" is required");
                return false;
            }
        }
        return true;
    }

    function submitShape(data){
        $.ajax({
            url: "/admin/shapes",
            method: edit?"PUT" : "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
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

})