<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./visitchart.php");

$headers = getallheaders();


while (list($header,$value)=each ($headers)){

    echo "<br><br><br><br><br><br><br><br>";
    echo "$header: $value\n";
}

?>
<html>
<head>
    <meta charset="utf-8">
    <title>Slick Login</title>
    <meta name="description" content="slick Login">
    <meta name="author" content="Webdesigntuts+">
    <link rel="stylesheet" type="text/css" href="./css/style.css" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https: //cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.js"></script>
    <script type="text/javascript" src="./js/placeholder.js"></script>


</head>
<body>




<?php if(!isset($_SESSION['mb_id'])) { // 로그인 세션이 없을 경우 로그인 화면 ?>

  
    <form id="slick-login" method="post">
        <label for="username">username</label><input type="text" name="mb_id" class="placeholder" placeholder="userID">
        <label for="password">password</label><input type="password" name="mb_password" class="placeholder" placeholder="password">
        <input type="submit" value="로그인" onclick="javascript: form.action='./login_check.php';">
        <input type="submit" value="회원 가입" onclick="javascript: form.action='./register.php';">
        <input type="submit" value="메인 화면" onclick="javascript: form.action='./main.php';">
    </form>

<?php } else { // 로그인 세션이 있을 경우 로그인 완료 화면 ?>

    <h1>로그인을 환영합니다.</h1>

    <?php
    $mb_id = $_SESSION['mb_id'];

    $sql = " select * from member where mb_id = TRIM('$mb_id') ";
    $result = mysqli_query($conn, $sql);
    $mb = mysqli_fetch_assoc($result);

    mysqli_close($conn); // 데이터베이스 접속 종료

    echo "<script>alert('이미 로그인중입니다.');</script>";

    echo("<script>location.replace('./main.php');</script>");
    ?>

<?php } ?>

</body>
</html>