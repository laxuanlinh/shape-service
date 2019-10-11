$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).on("click", "#submit", function(){
        var shapeCategoryName = $("#shapeCategoryName").val();
        var dimensions = $("#dimensions").val().split(",").map(item => item.trim());
        var formula = $("#formula").val();
        var rules = $("#rules").val();
        var data = {
            shapeCategoryName : shapeCategoryName,
            dimensions : dimensions,
            formula : formula,
            rules : rules
        }

        $.ajax({
            url: "/admin/shapes/categories",
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

    });

})