<?php
$mysql_host = "127.0.0.1";
$mysql_user = "test";
$mysql_password = "0496";
$mysql_db = "test";

$conn = mysqli_connect($mysql_host, $mysql_user, $mysql_password, $mysql_db); // MySQL 데이터베이스 연결

if (!$conn) { // 연결 오류 발생 시 스크립트 종료
    die("연결 실패: " . mysqli_connect_error());
}

header('P3P: CP="ALL CURa ADMa DEVa TAIa OUR BUS IND PHY ONL UNI PUR FIN COM NAV INT DEM CNT STA POL HEA PRE LOC OTC"');
// 세션 파일을 저장할 경로 설정. 설정하지 않으면 php.ini에 기본 경로에 저장.
ini_set('session.save_path',$_SERVER['DOCUMENT_ROOT']."/session");
// 세션 ID를 저장한 쿠키의 유효 시간 설정. 설정하지 않거나 0으로 설정하면 브라우저가 종료되면 쿠키는 파기. (초 단위)
ini_set('session.cookie_lifetime', 0) ;
// 사용되지 않는 세션 파기 유효 시간 설정. (초단위)
ini_set('session.gc_maxlifetime', 1440);
// 서버에 저장된 세션 ID 활성화 시간. (분단위)
ini_set('session.cache_expire', 180);
// 세션 시작
session_start();




?>