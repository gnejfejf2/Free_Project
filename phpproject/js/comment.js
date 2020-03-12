$(document).ready(function () {
    $(".dat_edit_bt").click(function () {
        /* dat_edit_bt클래스 클릭시 동작(댓글 수정) */
        var obj = $(this).closest(".dap_lo").find(".dat_edit");
        obj.dialog({
            modal: true,
            width: 650,
            height: 200,
            title: "댓글 수정"
        });
    });

    $(".dat_delete_bt").click(function () {
        /* dat_delete_bt클래스 클릭시 동작(댓글 삭제) */
        var obj = $(this).closest(".dap_lo").find(".dat_delete");
        obj.dialog({
            modal: true,
            width: 400,
            title: "댓글 삭제확인"
        });
    });

    $(".dat_plus_bt").click(function () {
        /* dat_plus_bt클래스 클릭시 동작(답글추가) */
        var obj = $(this).closest(".dap_lo").find(".dat_reply");
        obj.dialog({
            modal: true,
            width: 650,
            height: 200,
            title: "답글 쓰기"
        });
    });
    $(".dat_edit_bt2").click(function () {
        /* dat_edit_bt클래스 클릭시 동작(댓글 수정) */
        var obj = $(this).closest(".dap_lo2").find(".dat_edit2");
        obj.dialog({
            modal: true,
            width: 650,
            height: 200,
            title: "댓글 수정"
        });
    });

    $(".dat_delete_bt2").click(function () {
        /* dat_delete_bt클래스 클릭시 동작(댓글 삭제) */
        var obj = $(this).closest(".dap_lo2").find(".dat_delete2");
        obj.dialog({
            modal: true,
            width: 400,
            title: "댓글 삭제확인"
        });
    });
    $(".dat_viewplus_bt").click(function () {
        /* dat_delete_bt클래스 클릭시 동작(댓글 삭제) */


        if($(this).closest(".dap_lo").find(".recomment").css("display") == "none"){
            $(this).closest(".dap_lo").find(".recomment").show();
        } else {
            $(this).closest(".dap_lo").find(".recomment").hide();
        }

    });

});

