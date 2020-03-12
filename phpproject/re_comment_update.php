<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

include("./nologin.php");




$boardnumber=$_POST[boardnumber];
$id = $_POST[commentnumber];                      //Writer
$content = $_POST[content];              //Content


if ($id == $_SESSION['mb_id']) { // 아무런 모드가 없다면 중단
    echo "<script>alert('허용되지 않은 접근입니다.');</script>";
    echo "<script>location.replace('./board.php');</script>";
    exit;
}


$URL = './view.php?number='.$boardnumber;                   //return URL

$sql = " UPDATE re_reply
                SET content = '$content'
                WHERE id = '$id' ";
$result = mysqli_query($conn, $sql);


echo $id,$content;


if($result){
    ?>                  <script>
        alert("<?php echo "답글이 수정되었습니다."?>");
        location.replace("<?php echo $URL?>");
    </script>
    <?php
}
else{
    echo "FAIL";
}


?>