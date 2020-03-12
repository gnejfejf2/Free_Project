//<![CDATA[
/** 아이템을 등록한다. */
function submitItem() {

        var content = $("input[type='text'][name='item']");
        var items = $("input[type='hidden'][name='base64']");


        for (var i = 0; i < items.length; i++) {

            alert(i+1+'번사진 등록되었습니다.');

            var imagecontent = $(content.get(i)).val().trim();

            var image = $(items.get(i)).val().trim();

            photoupload(image, imagecontent, i);

        }


}


function resubmitItem(number) {

        var content = $("input[type='text'][name='item']");
        var items = $("input[type='hidden'][name='base64']");


        for (var i = 0; i < items.length; i++) {
            alert($(items.get(i)).val().trim());
            alert(i+1+'번사진 등록되었습니다.');

            var imagecontent = $(content.get(i)).val().trim();

            var image = $(items.get(i)).val().trim();

            rephotoupload(image, imagecontent, i,number);

        }


}


/** 아이템 체크 */
function validateItem() {
    var items = $("input[type='text'][name='item']");
    if (items.length == 0) {
        alert("작성된 아이템이 없습니다.");
        return false;
    }

    var flag = true;
    for (var i = 0; i < items.length; i++) {
        if ($(items.get(i)).val().trim() == "") {
            flag = false;

        }
    }

    return flag;
}

/** UI 설정 */
$(function () {
    $("#itemBoxWrap").sortable({
        placeholder: "itemBoxHighlight",
        start: function (event, ui) {
            ui.item.data('start_pos', ui.item.index());
        },
        stop: function (event, ui) {
            var spos = ui.item.data('start_pos');
            var epos = ui.item.index();
            reorder();
        }
    });
    //$("#itemBoxWrap").disableSelection();

    $("#sortable").sortable();
    $("#sortable").disableSelection();
});

/** 아이템 순서 조정 */
function reorder() {
    $(".itemBox").each(function (i, box) {
        $(box).find(".itemNum").html(i + 1);
    });
}

/** 아이템 추가 */
function createItem(result,content) {
    $(createBox(result,content))
        .appendTo("#itemBoxWrap")
        .hover(
            function () {
                $(this).find('.deleteBox').show();
            },
            function () {
                $(this).find('.deleteBox').hide();
            }
        )
        .append("<div class='deleteBox'>[삭제]</div>")
        .find(".deleteBox").click(function () {
        var valueCheck = false;
        $(this).parent().find('input').each(function () {
            if ($(this).attr("name") != "type" && $(this).val() != '') {
                valueCheck = true;
            }
        });

        if (valueCheck) {
            var delCheck = confirm('입력하신 내용이 있습니다.\n삭제하시겠습니까?');
        }
        if (!valueCheck || delCheck == true) {
            $(this).parent().remove();
            reorder();
        }
    });
    // 숫자를 다시 붙인다.
    reorder();

}

/** 아이템 박스 작성 */

function createBox(result,content) {
    var imagecontent=content;
    var contents = "<div class='itemBox'>"
        + "<img id='blah' src='" + result + "' width='800' height='600' />"
        + "<div style='float:left;'>"
        + "<input type='hidden' id='base64' name='base64' value='" + result + "'/>"
        + "<input type='text' id='item' name='item' contentEditable='true'style='width:800px' value='" + imagecontent + "'/>"
        + "</div>"
        + "</div>";

    return contents;
}




function photoupload(data,imagecontent,imagenum){

    $.ajax({
        type: "POST"
        ,url: "./imageupload.php"
        ,data: {
            fileToUpload : data
            ,imagecontents : imagecontent
            ,imagenumber : imagenum
        }
        ,success:function(){

        }
        ,error:function(){
            alert("error");
        }
    });

}

function rephotoupload(data,imagecontent,imagenum,number){

    $.ajax({
        type: "POST"
        ,url: "./reimageupload.php"
        ,data: {
            fileToUpload : data
            ,imagecontents : imagecontent
            ,imagenumber : imagenum
            ,boardnumber : number
        }
        ,success:function(){

        }
        ,error:function(){
            alert("error");
        }
    });

}