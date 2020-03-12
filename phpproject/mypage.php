<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.

if (!isset($_SESSION['mb_id'])) { // 로그인 세션이 없을 경우 로그인 화면
    echo "<script>alert('로그인이 안되어있습니다 로그인 페이지로 넘어갑니다.');</script>";

    echo("<script>location.replace('./login.php');</script>");

}
else{
    $mb_id = $_SESSION['mb_id'];

    $sql = " select * from member where mb_id = TRIM('$mb_id') ";
    $result = mysqli_query($conn, $sql);
    $mb = mysqli_fetch_assoc($result);

    mysqli_close($conn); // 데이터베이스 접속 종료
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset = 'utf-8'>
    <link href="./css/freelancer.css" rel="stylesheet">
</head>
<body>

<h2 align=center>회원 정보</h2>
                <table class="table table-striped" align = "center" width = "50">
                    <tr width = "50" align="center">
                        <td>아이디</td>
                        <td><?php echo $mb['mb_id'] ?></td>
                    </tr>

                    <tr width = "50" align="center">
                        <td>이름</td>
                        <td><?php echo $mb['mb_name'] ?></td>
                    </tr>

                    <tr width = "50" align="center">
                        <td>이메일</td>
                        <td><?php echo $mb['mb_email'] ?></td>
                    </tr>

                    <tr width = "50" align="center">
                        <td>관심분야</td>
                        <td><?php echo $mb['mb_language'] ?></td>
                    </tr>

                    <tr width = "50" align="center">
                        <td>직업</td>
                        <td><?php echo $mb['mb_job'] ?></td>
                    </tr width = "50" align="center">

                    <tr width = "50" align="center">
                        <td>성별</td>
                        <td><?php echo $mb['mb_gender'] ?></td>
                    </tr>

                    <tr width = "50" align="center">
                        <td>게시글 수</td>
                        <td>추후 작업예정</td>
                    </tr>

                    <tr>
                        <td class="text-center" colspan="2">
                            <button onclick="location.replace('./register.php?mode=modify')" class="btn btn-primary">암호변경</button>
                            <button onclick="location.replace('./myboard.php')" class="btn btn-danger">내가쓴글</button>
                            <button onclick="location.replace('./logout.php?mode=logout')" class="btn btn-warning">로그아웃</button>
                            <button onclick="location.replace('./main.php')" class="btn btn-success">메인으로</button>

                        </td>
                    </tr>


                </table>

</body>
</html>