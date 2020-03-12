<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");

$number = $_GET['number'];
$query = "select id from board where number =$number";
$result = mysqli_query($conn, $query);
$rows = mysqli_fetch_assoc($result);
echo $rows['id'];

if ($_SESSION[mb_id] != $rows['id']) {
    echo "<script>
                alert('허용되지 않은 접근입니다..');
                history.back();
          </script>";
}
else{

    $deletesql = " UPDATE board
                SET  hit = '1'
             WHERE number = '$number' ";
    $deleteresult = mysqli_query($conn, $deletesql);
    echo "<script>
                alert('게시글이 삭제되었습니다.');
                location.replace('../board.php');
          </script>";

}

?>


