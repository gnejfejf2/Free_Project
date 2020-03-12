<?php
if (!isset($_SESSION['mb_id'])) { // 로그인 세션이 없을 경우 로그인 화면
    echo "<script>alert('로그인후 이용해주세요');</script>";

    echo("<script>location.replace('./login.php');</script>");

}

?>