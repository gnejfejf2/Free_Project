<?php
if (isset($_POST['optVal'])) {
    include("../dbconn.php");

    $ym = $_POST['optVal'] . '%';
    $query = "select date_format(date,'%H') m,count(*) from membervisit where date like '$ym' group by m;";
    $result = mysqli_query($conn, $query);
    $total = mysqli_num_rows($result);

    $member = array(array("날짜","방문자수"));
    $R = array();
    while ($rows = mysqli_fetch_assoc($result)) { //DB에 저장된 데이터 수 (열 기준)
//    $ticks.append($rows['date_format(date,\'%d\')']);
//    $ticks=$rows['date_format(date,\'%d\')'];
//       $test[] = array('data'=> $rows['count(*)'], 'ticks'=>$rows['m']);
        $member[] = array($rows['m'],(int)$rows['count(*)']);
    }
    $test = array('dat' => $member); // X축 좌표값과 일별 통계 값을 DB에서 추출하여 배열화 처리




    echo json_encode($test);


}


?>


