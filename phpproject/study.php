<?php

include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.


$guest_ip = $_SERVER['REMOTE_ADDR']; // 접속 아이피



for ($i = 0;$i < 10000;$i++){
    $date = date('Y-m-d H:i:s', strtotime("$i hours"));            //Date

    $sql = " INSERT INTO membervisit
                SET id='testid',
                ip = '$guest_ip',
                     date = '$date' ";
    $result = mysqli_query($conn, $sql);
}

echo "성공";
?>