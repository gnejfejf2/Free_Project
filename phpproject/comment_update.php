<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

include("./nologin.php");


$boardnumber=$_POST[boardnumber];
$id = $_POST[commentnumber];                      //Writer
$content = $_POST[content];              //Content



$URL = './view.php?number='.$boardnumber;                   //return URL

$sql = " UPDATE reply
                SET content = '$content'
                WHERE id = '$id' ";
$result = mysqli_query($conn, $sql);








if($result){
    ?>                  <script>
        alert("<?php echo "댓글이 수정되었습니다."?>");
        location.replace("<?php echo $URL?>");
    </script>
    <?php
}
else{
    echo "FAIL";
}


?>