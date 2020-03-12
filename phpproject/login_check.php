<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.


if (!isset($_POST['mb_id']) || !isset($_POST['mb_password'])) exit;

$mb_id = $_POST["mb_id"];
$mb_password = $_POST["mb_password"];

if (($mb_id == '') || ($mb_password == '')) {
    echo "<script>alert('아이디 또는 패스워드를 입력하여 주세요.');history.back();</script>";
    exit;
}

$sql = " SELECT * FROM member WHERE mb_id = '$mb_id' ";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_array($result);

$language = $row['mb_language'];
// $user_passwd 는 로그인시 입력받은 비밀번호
// $db_passwd 는 DB에서 읽어온 암호화된 비밀번호

if (password_verify($mb_password, $row['mb_password'])) {
    if ($row['mb_email_certify2'] == '') {
        $_SESSION['mb_id'] = $mb_id;


        $login_ip = $_SERVER['REMOTE_ADDR']; // 접속 아이피
        $date = date('Y-m-d h:i:s', strtotime("+540 minutes"));            //Date

        $firstsql = "select id from membervisit where id='$mb_id' and date > DATE_ADD(now(),INTERVAL -1 DAY)";
        $firstresult = mysqli_query($conn, $firstsql);


        if (mysqli_num_rows($firstresult) >= 0) {

            $insertsql = " INSERT INTO membervisit
                SET ip = '$login_ip',
                    id = '$mb_id',
                    id_language ='$language',
                     date = '$date' ";
            $insertresult = mysqli_query($conn, $insertsql);


            if (empty($insertresult)) {

                ?>

                <script>

                    alert('오류가 발생했습니다.');

                    history.back();

                </script>

                <?php

            } else {

            }


        }

        if ($mb_id == "gnejfejf2") {
            echo "<script>location.replace('../admin/index.php');</script>";

        } else {
            $URL = './main.php';                   //return URL


            ?>
            <script>
                location.replace("<?php echo $URL;?>");
            </script>
            <?php


            echo "<form action=\"main.php\" method=\"get\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

        </form>";
        }

    } else {

        echo "<script>alert('이메일 인증이 필요합니다 이메일 인증 사이트로 넘어갑니다.');</script>";
        echo "<form action=\"email_certify.php\" method=\"POST\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

        </form>";
    }


} else {

    echo "<script>alert('아이디 또는 비밀번호가 잘못되었습니다.'); history.back(); </script>";

}


?>