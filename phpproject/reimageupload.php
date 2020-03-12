<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");
include("./visitchart.php");

$number=$_POST['boardnumber'];
$file = $_POST['fileToUpload'];
$imagecontents = $_POST['imagecontents'];
$imagenum =$_POST['imagenumber'];


$filetest=substr( $file, 0, 6 );


if($imagenum==0) {
    $firstsql = " DELETE from board_image where imageboardnumber=$number";
    $firstresult = mysqli_query($conn, $firstsql);
}



if($filetest=="upload") {

    $pathImage=$file;


    $insertsql = " INSERT INTO board_image
                SET
                     imagecontent = '$imagecontents',
                     imagepath = '$pathImage',
                     imagenumber = '$imagenum',
                     imageboardnumber = '$number'
                     ";
    $no1result = mysqli_query($conn, $insertsql);



}
else {
    $pos = strpos($file, ';');
    $type = explode(':', substr($file, 0, $pos))[1];
    $mime = explode('/', $type);

    $pathImage = "uploads/" . time() . $mime[1];
    $file = substr($file, strpos($file, ',') + 1, strlen($file));
    $dataBase64 = base64_decode($file);
    file_put_contents($pathImage, $dataBase64);


    $insert2sql = " INSERT INTO board_image
                SET  
                     imagecontent = '$imagecontents',
                     imagepath = '$pathImage',
                     imagenumber = '$imagenum',
                     imageboardnumber = '$number'   
                     ";
    $no2result = mysqli_query($conn, $insert2sql);

}






?>