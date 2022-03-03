
function addAnswer(){
    var param = $("#answerForm").serialize();
    $.ajax({
        type : 'POST',
        url : '/api/qna/addAnswer',
        data : param,
        dataType : 'json',
        error : onError,
        success : onSuccess,
    });

    function onSuccess(json,status) {
        console.log(json.writer);
        var answerTemplate = $("#answerTemplate").html();
        var date = new Date(json.createDate);
        var dateToString = date.getFullYear()+"-"
                            +(date.getMonth() > 10 ? date.getMonth() : "0"+date.getMonth())+"-"
                            +(date.getDate() > 10 ? date.getDate() : "0"+date.getDate());
        var template = answerTemplate.format(json.writer, dateToString,json.content, json.answerId);
        $("#answerForm").before(template);
        var count = $(".qna-comment-count > strong").text();
        $(".qna-comment-count > strong").text(parseInt(count)+1);
        $("textarea[name=content]").val("");
    }
    function onError(response) {
        console.log(response.responseText);
    }
}
$(document).on("click",".link-delete-article",answerDelete);
function answerDelete() {
    var _this = $(this);
    var answerId = $(this).data("answer-id");
    var questionId = $(this).data("question-id");
    var obj = new Object();
    obj["answerId"] = answerId;
    obj["questionId"] = questionId;

    $.ajax({
        type : 'POST',
        url : '/api/qna/deleteAnswer',
        data : obj,
        dataType : 'json',
        error : onDelError,
        success : onDelSuccess,
    });
    function onDelSuccess(data,status){
        if(data.status == true){
            _this.closest(".article").remove();
            var count = $(".qna-comment-count > strong").text();
            $(".qna-comment-count > strong").text(parseInt(count)-1);
        }
    }
    function onDelError(response) {
        alert(response.message);
        console.log(response.responseText);
    }
}


$(document).ready(function(){/* jQuery toggle layout */
$('#btnToggle').click(function(){
  if ($(this).hasClass('on')) {
    $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
    $(this).removeClass('on');
  }
  else {
    $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
    $(this).addClass('on');
  }
});
});
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
