<?php
if (isset($_SESSION['mb_id'])) { // 세션이 있고 회원수정 mode라면 회원 정보를 가져옴

    session_start();
    session_destroy();
    echo "<script>alert('로그아웃 되었습니다.');</script>";
    echo("<script>location.replace('./login.php');</script>");
}
else {
    echo "<script>alert('허용되지 않은 접근입니다.');</script>";
    echo("<script>location.replace('./login.php');</script>");
}
?>