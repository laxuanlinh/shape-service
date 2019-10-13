$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var conditionsOtherCategories = {};

    $(document).on("click", "#addConditions", function(){
        var categoryName = $("#otherCategoryName").val();
        var conditions = $("#conditions").val().trim();
        if($("#conditions").val().trim()!==""){
            conditionsOtherCategories[categoryName] = conditions;
        }
        $("#conditionsGroup").append(categoryName + ": " +  conditions);
        $("#conditions").val("");
    });

    $(document).on("click", "#submit", function(){
        var shapeCategoryName = $("#shapeCategoryName").val();
        var dimensions = $("#dimensions").val().split(",").map(item => item.trim());
        var formula = $("#formula").val();
        var rules = $("#rules").val();
        if(!formIsValid()){
            return;
        }
        var data = {
            shapeCategoryName : shapeCategoryName,
            dimensions : dimensions,
            formula : formula,
            rules : rules,
            conditionsOtherCategories: conditionsOtherCategories
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
               location.reload(true);
            },
            error: function(e){
                alert(e.responseJSON.message);
            }
        })

    });

    function formIsValid(){
        if(empty($("#shapeCategoryName").val())){
            alert("Category name is required");
            return false;
        }
        if(empty($("#dimensions").val())){
            alert("Dimensions is required");
            return false;
        }
        if(empty($("#dimensions").val())){
            alert("Formula is required");
            return false;
        }

        if(empty($("#rules").val())){
            alert("Rules are required");
            return false;
        } else if(evaluateMath($("#rules").val())){
            alert("Rules are invalid;");
            return false;
        }
        if(empty($("#formula").val())){
            alert("Formula is required");
            return false;
        }

        return true;

    }

    function empty(string){
        if(!string && string==="")
            return true;
        return false;
    }

    function evaluateMath(expression){
        try{
            return eval(expression);
        } catch (e){
            return false;
        }
    }

})