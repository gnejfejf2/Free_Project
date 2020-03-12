<?php
include("./nologin.php");
include("./visitchart.php");
?>


<!DOCTYPE>

<html>
<head>
    <meta charset='utf-8'>
    <link href="./css/board.css" rel="stylesheet">
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript"></script>
    <script src="js/sortable.js"></script>
    <script type="text/javascript">
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                var content = "";
                reader.onload = function (e) {
                    createItem(e.target.result,content);
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</head>



<body>
<form method="post" action="write_action.php" onsubmit="return fregisterform_submit(this);" >
    <table style="padding-top:50px" align=center width=700 border=0 cellpadding=2>
        <tr>
            <td height=20 align=center bgcolor=#ccc><font color=white> 글쓰기</font></td>
        </tr>
        <tr>
            <td bgcolor=white>
                <table class="table2">
                    <tr>
                        <td>작성자</td>
                        <td>
                            <input type=text name=name size=20 readonly value="<?php
                            echo $_SESSION[mb_id];
                            ?>">
                        </td>
                    </tr>

                    <tr>
                        <td>제목</td>
                        <td><input type=text name=title size=60></td>
                    </tr>

                    <tr>

                        <td>내용</td>
                        <td>
                            <input type=text name=content contentEditable="true" style='width:800px;'>

                            <div id="itemBoxWrap"></div>
                        </td>
                    </tr>


                </table>

                <center>
                    <input type="submit" value="작성" onclick='submitItem()'>
                </center>
                <form id="form1" runat="server">
                    <input type='file' onchange="readURL(this);" />
                </form>

            </td>
        </tr>
    </table>
</form>



<script>

    function fregisterform_submit(f) { // submit 최종 폼체크

        if (f.name.value.length < 1) { // 회원아이디 검사
            alert("아이디를 입력하십시오.");
            f.name.focus();
            return false;

        }

        if (f.title.value.length < 1) { // 이름 검사
            alert("제목을 입력하십시오.");
            f.title.focus();
            return false;
        }

        if (f.content.value.length < 1) {
            alert("내용을 입력해주세요");
            f.content.focus();
            return false;
        }

        return true;

    }
</script>


</body>
</html>



