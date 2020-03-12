<?php

if(!isset($_SESSION['mb_id']) OR $_SESSION['mb_id']!="gnejfejf2"){
    echo "<script>alert('허용되지 않은 접근입니다.');</script>";
    echo("<script>location.replace('../main.php');</script>");
}
else{
   
}


?>