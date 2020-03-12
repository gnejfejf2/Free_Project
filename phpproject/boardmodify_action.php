<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

include("./nologin.php");



sleep(1);

$id = $_POST[name];                      //Writer
$title = $_POST[title];                  //Title
$content = $_POST[content];              //Content
$boardnumber = $_POST[boardnumber];        //Date


if ($id == $_SESSION['mb_id']) { // 아무런 모드가 없다면 중단
    echo "<script>alert('허용되지 않은 접근입니다.');</script>";
    echo "<script>location.replace('./board.php');</script>";
    exit;
}


$URL = './board.php';                   //return URL




$sql = " UPDATE board
                SET  id = '$id',
                     title = '$title',
                     content = '$content'
             WHERE number = '$boardnumber' ";
$result = mysqli_query($conn, $sql);






if($result){
    ?>                  <script>
        alert("<?php echo "글이 수정되었습니다."?>");
        location.replace("<?php echo $URL?>");
    </script>
    <?php
}
else{
    echo "FAIL";
}


?>
