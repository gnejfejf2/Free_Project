<?php



include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");
$option = trim($_GET['option']);
$search = ($_GET['wanted']);

echo $search;

$query ="select * from board where title LIKE '%$search%' and hit=0 order by number desc";
echo $query;
$result = $conn->query($query);
$total = mysqli_num_rows($result);
/* 페이징 시작 */

//페이지 get 변수가 있다면 받아오고, 없다면 1페이지를 보여준다.

if(isset($_GET['page'])) {

    $page = $_GET['page'];

} else {

    $page = 1;

}



$pagesql = "select count(*) as cnt from board where title LIKE '%$search%' and hit=0 order by number desc";

$pageresult = $conn->query($pagesql);

$row = $pageresult->fetch_assoc();



$allPost = $row['cnt']; //전체 게시글의 수



$onePage = 15; // 한 페이지에 보여줄 게시글의 수.

$allPage = ceil($allPost / $onePage); //전체 페이지의 수



if($page < 1 || ($allPage && $page > $allPage)) {

    ?>

    <script>

        alert("존재하지 않는 페이지입니다.");

        history.back();

    </script>

    <?php

    exit;

}



$oneSection = 5; //한번에 보여줄 총 페이지 개수(1 ~ 10, 11 ~ 20 ...)

$currentSection = ceil($page / $oneSection); //현재 섹션



$allSection = ceil($allPage / $oneSection); //전체 섹션의 수



$firstPage = ($currentSection * $oneSection) - ($oneSection - 1); //현재 섹션의 처음 페이지



if($currentSection == $allSection) {

    $lastPage = $allPage; //현재 섹션이 마지막 섹션이라면 $allPage가 마지막 페이지가 된다.

} else {

    $lastPage = $currentSection * $oneSection; //현재 섹션의 마지막 페이지

}



$prevPage = (($currentSection - 1) * $oneSection); //이전 페이지, 11~20일 때 이전을 누르면 10 페이지로 이동.

$nextPage = (($currentSection + 1) * $oneSection) - ($oneSection - 1); //다음 페이지, 11~20일 때 다음을 누르면 21 페이지로 이동.



$paging = '<ul>'; // 페이징을 저장할 변수



//첫 페이지가 아니라면 처음 버튼을 생성

if($page != 1) {

    $paging .= '<li class="page page_start"><a href="./list_actor.php?page=1&wanted='.$search.'&option='.$option.'?">처음</a></li>';

}

//첫 섹션이 아니라면 이전 버튼을 생성

if($currentSection != 1) {

    $paging .= '<li class="page page_prev"><a href="./list_actor.php?page=' . $prevPage . '&wanted='.$search.'&option='.$option.'?"">이전</a></li>';

}



for($i = $firstPage; $i <= $lastPage; $i++) {

    if($i == $page) {

        $paging .= '<li class="page current">' . $i . '</li>';

    } else {

        $paging .= '<li class="page"><a href="./list_actor.php?page=' . $i . '&wanted='.$search.'&option='.$option.'?"">' . $i . '</a></li>';

    }

}



//마지막 섹션이 아니라면 다음 버튼을 생성

if($currentSection != $allSection) {

    $paging .= '<li class="page page_next"><a href="./list_actor.php?page=' . $nextPage . '&wanted='.$search.'&option='.$option.'?"">다음</a></li>';

}



//마지막 페이지가 아니라면 끝 버튼을 생성

if($page != $allPage) {

    $paging .= '<li class="page page_end"><a href="./list_actor.php?page=' . $allPage . '&wanted='.$search.'&option='.$option.'?"">끝</a></li>';

}

$paging .= '</ul>';



/* 페이징 끝 */

$currentLimit = ($onePage * $page) - $onePage; //몇 번째의 글부터 가져오는지

$sqlLimit = ' limit ' . $currentLimit . ', ' . $onePage; //limit sql 구문




$sql = "select * from board where title LIKE '%$search%' and hit=0 order by number desc" . $sqlLimit; //원하는 개수만큼 가져온다. (0번째부터 20번째까지

$result = $conn->query($sql);

?>


<!DOCTYPE html>

<html>
<head>
    <meta charset = 'utf-8'>
    <link href="./css/board.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/normalize.css" />

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
        <td width = "50" align = "center"><?php echo $rows['number']?></td>
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


<div class="paging" id="paging">

    <?php echo $paging ?>

</div>

<form style = "text-align : center" class="form-inline" action=list_actor.php method="get">

    <select id="search" class="search" name="option">
        <option value="제목">제목</option>
        <option value="작성자">작성자</option>
    </select>

    <input type="text" class="form-control" name="wanted" placeholder="검색">

    <button type="summit" class="btn btn-danger">검색</button>

</form>


</body>
</html>
