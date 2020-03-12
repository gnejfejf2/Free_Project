<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

include("./nologin.php");

sleep(1);

$id = $_POST[name];                      //Writer
$title = $_POST[title];                  //Title
$content = $_POST[content];              //Content
$date = date('Y-m-d H:i:s',strtotime ("+540 minutes"));            //Date

$URL = './board.php';                   //return URL




$sql = " INSERT INTO board
                SET id = '$id',
                     title = '$title',
                     content = '$content',
                     date = '$date'   ";
$result = mysqli_query($conn, $sql);






if($result){
    ?>                  <script>
        alert("<?php echo "글이 등록되었습니다."?>");
        location.replace("<?php echo $URL?>");
    </script>
    <?php
}
else{
    echo "FAIL";
}


?>
