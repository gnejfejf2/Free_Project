<?php
include("./nologin.php");
include_once('./test.php'); // 메일 전송을 위한 파일을 인클루드합니다.


$name = strip_tags(htmlspecialchars($_POST['name']));
$email = strip_tags(htmlspecialchars($_POST['email']));
$phone = strip_tags(htmlspecialchars($_POST['phone']));
$message = strip_tags(htmlspecialchars($_POST['message']));

// Create the email and send the message

$body = "새로운 메세지가 왔습니다..\n\n"."자세한 내용은:\n\n이름: $name\n\n이메일: $email\n\n전화번호: $phone\n\n내용:\n$message";







mailer("지윤", "gnejfejf3@naver.com","gnejfejf3@naver.com", "당신의 포트폴리오 사이트에서 메세지가 왔습니다..", $body);





?>
