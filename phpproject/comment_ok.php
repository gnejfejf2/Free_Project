<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

include("./nologin.php");





$boardnumber = $_POST[boardnumber];
$mb_id = $_POST[mb_id];
$content = $_POST[content];
$date = date('Y-m-d h:i:s',strtotime ("+540 minutes"));



$URL = './view.php?number='.$boardnumber;                   //return URL



$sql = " INSERT INTO reply
                SET board_number = '$boardnumber',
                     mb_id = '$mb_id',
                     content = '$content',
                     date = '$date'   ";
$result = mysqli_query($conn, $sql);






if($result){
    ?>                  <script>
        alert("<?php echo "댓글이 등록되었습니다."?>");
        location.replace("<?php echo $URL?>");
    </script>
    <?php
}
else{

    echo mysqli_errno($conn);
}


?>