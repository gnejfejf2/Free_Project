<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");




$file = $_POST['fileToUpload'];
$imagecontents = $_POST['imagecontents'];
$imagenum =$_POST['imagenumber'];


$pos = strpos($file, ';');
$type = explode(':', substr($file, 0, $pos))[1];
$mime = explode('/', $type);

$pathImage = "uploads/".time().$mime[1];
$file = substr($file, strpos($file, ',') + 1, strlen($file));
$dataBase64 = base64_decode($file);
file_put_contents($pathImage, $dataBase64);

$query = "select * from board order by date desc limit 1";
$boardnumber = mysqli_query($conn, $query);
$row = mysqli_fetch_row($boardnumber);
$count_int = (int)$row[0]+1;


$sql = " INSERT INTO board_image
                SET  
                     imagecontent = '$imagecontents',
                     imagepath = '$pathImage',
                     imagenumber = '$imagenum',
                     imageboardnumber = '$count_int'   
                     ";
$result = mysqli_query($conn, $sql);




?>