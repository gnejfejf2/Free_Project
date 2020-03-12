<?php

include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.


$ip = $_SERVER['REMOTE_ADDR']; //아이피
$guest_ip = $_SERVER['REMOTE_ADDR']; // 접속 아이피
$date = date('Y-m-d h:i:s', strtotime("+540 minutes"));            //Date

$firstsql = "select ip from guestvisit where ip='$ip' and date > DATE_ADD(now(),INTERVAL -1 DAY)";
$firstresult = mysqli_query($conn, $firstsql);


if (empty($_COOKIE['guestvisit'])) {

    if (mysqli_num_rows($firstresult) > 0) {


        if (empty($result)) {


            ?>


            <?php

        } else {


            setcookie('guestvisit', TRUE, time() + (60 * 60 * 24), '/');

        }

    } else {
        $sql = " INSERT INTO guestvisit
                SET ip = '$guest_ip',
                     date = '$date' ";
        $result = mysqli_query($conn, $sql);
        setcookie('guestvisit', TRUE, time() + (60 * 60 * 24), '/');

    }
}

?>