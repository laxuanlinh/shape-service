$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var dimensions=[];

    showDimensions();

    $(document).on("change", "#shapeCategoryName", function(e){
        showDimensions();
    })

    function showDimensions(){
        $("#sizes").html("");
        var selectData = $("#shapeCategoryName").find(':selected').data('dimensions')
        dimensions = selectData.substring(1, selectData.length-1).split(",");
        for(dimension of dimensions){
            $("#sizes").append(" " + dimension + ": <input type='text' name='"+dimension.trim()+"' class='dimension form-control' id='" + dimension.trim() + "'/>");
        }
    }

    $(document).on("click", "#submit", function(e){
        var shapeName = $("#shapeName").val();
        var shapeCategoryName = $("#shapeCategoryName").val();
        var username = $("#username").val();
        var sizes = {};

        for(dimension of dimensions){
            sizes[dimension] = $('.dimension#' + dimension.trim()).val();
        }
        var data = {
            shapeName: shapeName,
            sizes: sizes,
            shapeCategory: {
                shapeCategoryName: shapeCategoryName
            },
            user: {
                username: username
            }
        }

        createShape(data);
    })

    function createShape(data){
        $.ajax({
            url: "/admin/shapes",
            method: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                console.log(result);

            },
            error: function(){
                console.log("Error");
            }
        })

    }

})