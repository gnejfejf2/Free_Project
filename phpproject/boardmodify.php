<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");

$number = $_GET['number'];
$query = "select title, content, date, hit, id from board where number =$number";
$result = mysqli_query($conn, $query);
$rows = mysqli_fetch_assoc($result);
if (empty($rows['title'])) {

    echo "<script>alert('삭제된 게시물 입니다.');</script>";
    echo "<script>location.replace('./board.php');</script>";
    exit;
} else {

    $imagequery = "select * from board_image where imageboardnumber = $number";
    $imageresult = mysqli_query($conn, $imagequery);
}


?>


<!DOCTYPE>

<?php
include("./nologin.php");
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
                    createItem(e.target.result, content);
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</head>


<body>
<form method="post" action="boardmodify_action.php" onsubmit="return fregisterform_submit(this);">
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
                            <input type="hidden" name=boardnumber value="<?php
                            echo $number ?>">
                            <input type=text name=name size=20 readonly value="<?php
                            echo $_SESSION[mb_id];
                            ?>">
                        </td>
                    </tr>

                    <tr>
                        <td>제목</td>
                        <td><input type=text name=title size=60 value=<?php echo $rows['title'] ?>></td>
                    </tr>

                    <tr>

                        <td>내용</td>
                        <td>
                            <input type=text name=content contentEditable="true" style='width:800px;' value=<?php
                            echo $rows['content'] ?>>


                            <div id="itemBoxWrap"></div>
                            <?php

                            while ($imagerows = mysqli_fetch_assoc($imageresult)) {

                                $image = $imagerows['imagepath'];
                                $imagecontent = $imagerows['imagecontent'];
                                echo "<script>createItem('".$image."','".$imagecontent."');</script>";


                            }


                            ?>

                        </td>
                    </tr>



                </table>

                <center>
                    <input type="submit" value="수정" onclick='resubmitItem(<?php echo $number?>)'>
                </center>
                <form id="form1" runat="server">
                    <input type='file' onchange="readURL(this);"/>
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










