<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

$mb_id = $_POST["mb_id"];
if (($mb_id == '')) {
    echo "<script>alert('허용되지 않은 접근입니다.');location.replace('./login.php');</script>";
}

$sql = " SELECT * FROM member WHERE mb_id = '$mb_id' "; // 회원가입을 시도하는 아이디가 사용중인 아이디인지 체크
$result = mysqli_query($conn, $sql);


//$mb = mysqli_fetch_assoc($result);
//
//
//if ($mb[mb_email_certify2] ='yes') { // 만약 사용중인 아이디라면 알림창을 띄우고 회원가입 페이지로 이동
//    echo "<script>alert('이미 인증된 아이디 입니다.');</script>";
//    echo "<script>location.replace('./main.php');</script>";
//    exit;
//}



?>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Slick Login</title>
    <meta name="description" content="slick Login">
    <meta name="author" content="Webdesigntuts+">
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="http://www.modernizr.com/downloads/modernizr-latest.js"></script>
    <script type="text/javascript" src="placeholder.js"></script>
</head>
<body>
<form id="slick-login" method="post">
    <label for="username">username</label><input type="text" name="mb_id" class="placeholder" placeholder="userid"
                                                 value="<?php echo $_POST["mb_id"]; ?>" readonly>
    <label for="password">password</label><input type="password" name="mb_email_certify2" class="placeholder"
                                                 placeholder="이메일 인증키">
    <input type="submit" value="메일인증" onclick="javascript: form.action='./email_certify_check.php';">
    <input type="submit" value="메일 다시보내기" name="test" onclick="">

</form>

<?php
if(isset($_POST['test'])){
    include_once('./test.php'); // 메일 전송을 위한 파일을 인클   루드합니다.

    $mb_md5 = md5(pack('V*', rand(), rand(), rand(), rand())); // 어떠한 회원정보도 포함되지 않은 일회용 난수를 생성하여 인증에 사용

    $sql = " UPDATE member SET mb_email_certify2 = '$mb_md5' WHERE mb_id = '$mb_id' "; // 회원가입을 시도하는 아이디에 메일 인증을 위한 일회용 난수를 업데이트
    $result = mysqli_query($conn, $sql);
    mysqli_close($conn); // 데이터베이스 접속 종료


    mailer("지윤", "gnejfejf3@naver.com",$mb['mb_email'], "인증번호 입니다.", "인증번호는 :" . $mb_md5 . "입니다.");
}
?>



</body>
</html>