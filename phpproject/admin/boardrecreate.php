<?php
include("../dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("../admin/admincheck.php");

$number = $_GET['number'];


if ($_SESSION[mb_id] != "gnejfejf2") {
    echo "<script>
                alert('허용되지 않은 접근입니다..');
                history.back();
          </script>";
} else {

    $deletesql = " UPDATE board
                SET  hit = '0'
             WHERE number = '$number' ";
    $deleteresult = mysqli_query($conn, $deletesql);
    echo "<script>
                alert('게시글이 복구되었습니다.');
                location.replace('../admin/adminboard.php');

          </script>";

}

?>

