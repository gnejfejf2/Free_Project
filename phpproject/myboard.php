<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

$id=$_SESSION['mb_id'];
$query ="select * from board where hit=0 AND id='$id' order by number desc";
$result = $conn->query($query);
$total = mysqli_num_rows($result);

?>


<!DOCTYPE html>

<html>
<head>
    <meta charset = 'utf-8'>
    <link href="./css/board.css" rel="stylesheet">
</head>

<body>

<h1 align=right>
    <form id="slick-login" method="post">
        <input type="submit" value="메인화면" onclick="javascript: form.action='./main.php';">
    </form>
</h1>
<h2 align=center>게시판</h2>
<table align = center>
    <thead align = "center">
    <tr>
        <td width = "50" align="center">번호</td>
        <td width = "500" align = "center">제목</td>
        <td width = "100" align = "center">작성자</td>
        <td width = "200" align = "center">날짜</td>
    </tr>
    </thead>

    <tbody>
    <?php
    while($rows = mysqli_fetch_assoc($result)){ //DB에 저장된 데이터 수 (열 기준)
        if($total%2==0){
            ?>                      <tr class = "even">
        <?php   }
        else{
            ?>                      <tr>
        <?php } ?>
        <td width = "50" align = "center"><?php echo $total?></td>
        <td width = "500" align = "center">
            <a href = "view.php?number=<?php echo $rows['number']?>">
            <?php echo $rows['title']?></td>
        <td width = "100" align = "center"><?php echo $rows['id']?></td>
        <td width = "200" align = "center"><?php echo $rows['date']?></td>
        </tr>
        <?php
        $total--;
    }
    ?>
    </tbody>
</table>

<div class = text>
    <font style="cursor: hand"onClick="location.href='./write.php'">글쓰기</font>
</div>






</body>
</html>
