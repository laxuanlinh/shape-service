$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).on("click", "#addConditions", function(){
        var categoryName = $("#otherCategoryName").val();
        var conditionValue = $("#conditions").val().trim();
        if(conditionValue && conditionValue.trim()!==""){
            appendToConditionsGroup(categoryName, conditionValue);
        }
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
        var conditionsOtherCategories={};
        $("#conditionsGroup").find(".keyPair").each(function(){
            var category = $(this).find(".categoryName").text();
            var condition = $(this).find(".conditionValue").text();
            conditionsOtherCategories[category] = condition;
        })
        console.log(conditionsOtherCategories);
        var data = {
            shapeCategoryName : shapeCategoryName,
            dimensions : dimensions,
            formula : formula,
            rules : rules,
            conditionsOtherCategories: conditionsOtherCategories
        }

        $.ajax({
            url: "/admin/categories",
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

    $(document).on("click", ".edit", function(){
        var element = jQuery(this).closest("tr");
        $("#shapeCategoryName").val(element.find(".shapeCategoryName").text());
        var dimensions = "";
        element.find(".dimensions").find("p").each(function(){
            dimensions += $(this).text().trim()+", ";
        });
        $("#dimensions").val(dimensions.trim().slice(0, -1));
        $("#formula").val(element.find(".formula").text());
        $("#rules").val(element.find(".rules").text());
        element.find(".conditionList").find(".condition").each(function(){
            var categoryName = $(this).find("b").text();
            var conditionValue = $(this).find("i").text();
            if(categoryName && conditionValue && categoryName.trim()!=="" && conditionValue!=="")
                appendToConditionsGroup(categoryName, conditionValue);
        })
    })

    $(document).on("click", ".deleteCondition", function(){
        $(this).closest("span").remove();
    })

    $('#formModal').on('hidden.bs.modal', function () {
        $("input[type='text']").val("");
        $("#otherCategoryName").prop("selectedIndex", 0);
        $("#conditionsGroup").find(".keyPair").remove();

    });

    function appendToConditionsGroup(categoryName, conditionValue){
        $("#conditionsGroup").append("<span class='keyPair'>" +
                                         "<b class='categoryName float-left'>"+categoryName +
                                         "</b> <span class='float-left'> : </span>"+
                                         "<i class='conditionValue float-left'>" +  conditionValue + "</i>"+
                                         "<button type='button' class='deleteCondition close float-left pl-2' aria-label='Close'>"+
                                              "<span aria-hidden='true'>&times;</span>"+
                                         "</button>"+
                                     "</span><br>");
    }

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