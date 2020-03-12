<?php
$mysql_host = "127.0.0.1";
$mysql_user = "test";
$mysql_password = "0496";
$mysql_db = "test";
$conn = mysqli_connect($mysql_host, $mysql_user, $mysql_password, $mysql_db); // MySQL 데이터베이스 연결

if (!$conn) { // 연결 오류 발생 시 스크립트 종료
    die("연결 실패: " . mysqli_connect_error());
}
include("./nologin.php");


$nowid = $_SESSION[mb_id];
$ip = $_SERVER['REMOTE_ADDR']; //아이피
$mb_ip = preg_replace("/[^0-9]*/s", "", $ip);
$URL = 'http://192.168.254.151:3000';                   //return URL

$deletesql = " DELETE FROM chatuser
                WHERE ip = '$mb_ip'";
$deleteresult = mysqli_query($conn, $deletesql);




$insertsql = " INSERT INTO chatuser
                SET user = '$nowid',
                     ip = '$mb_ip'";
$insertresult = mysqli_query($conn, $insertsql);


if ($insertresult) {
    ?>

    <script>
        location.replace("<?php echo $URL?>");
    </script>
    <?php
} else {
    echo "FAIL";
}


?>
