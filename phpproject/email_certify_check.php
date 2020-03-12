<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.


if (!isset($_POST['mb_id']) || !isset($_POST['mb_email_certify2'])) {
    echo "<form action=\"email_certify.php\" method=\"POST\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

</form>";

}
$mb_id = $_POST["mb_id"];
$mb_email_certify2 = $_POST["mb_email_certify2"];

if (($mb_id == '') || ($mb_email_certify2 == '')) {
    echo "<script>alert('아이디,이메일 인증번호를 입력하여 주세요.');</script>";
    echo "<form action=\"email_certify.php\" method=\"POST\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

</form>";

}

$sql = " SELECT * FROM member WHERE mb_id = '$mb_id' ";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_array($result);


// $user_passwd 는 로그인시 입력받은 비밀번호
// $db_passwd 는 DB에서 읽어온 암호화된 비밀번호

if ($mb_email_certify2==$row['mb_email_certify2']) {

    $sql2 = " UPDATE member
                SET mb_email_certify2= ''
             WHERE mb_id = '$mb_id' ";
    $result2 = mysqli_query($conn, $sql2);

    echo "<script>alert('이메일 인증이 완료 되었습니다.');</script>";

    echo("<script>location.replace('./main.php');</script>");


    $_SESSION['mb_id'] = $mb_id;
} else {
    echo "<script>alert('이메일 인증번호가 틀렸습니다.');</script>";
    echo "<form action=\"email_certify.php\" method=\"POST\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

</form>";

}


?>