<?php
include("../dbconn.php");
include("../admin/admincheck.php");


$ym = date('Y-m') . '-%';
$studyquery = "select date_format(date,'%Y-%m-%d') m,count(*) from membervisit where date like '2020-03-%' group by m;";
$studyresult = mysqli_query($conn, $studyquery);
$lasttest = mysqli_fetch_assoc($studyresult);

$membervisitquery = "select date_format(date,'%Y-%m') m,count(*) from membervisit where date like '2020-03-%' group by m;";
$membervisitresult = mysqli_query($conn, $membervisitquery);
$membervisitrow = mysqli_fetch_assoc($membervisitresult);

$guestvisitquery = "select date_format(date,'%Y-%m') m,count(*) from guestvisit where date like '2020-03-%' group by m;";
$guestvisitresult = mysqli_query($conn, $guestvisitquery);
$guestvisitrow = mysqli_fetch_assoc($guestvisitresult);



$member = array();
$R = array();
while ($studyrows = mysqli_fetch_assoc($studyresult)) { //DB에 저장된 데이터 수 (열 기준)
//    $ticks.append($rows['date_format(date,\'%d\')']);
//    $ticks=$rows['date_format(date,\'%d\')'];
    $member[] = (int)$studyrows['count(*)'];
    $R[] = $studyrows['m'];
}


$weekquery = "select date_format(date,'%Y-%m-%d') m,count(*) from guestvisit where date between date_add(now(),interval -1 week ) And now() group by m;";
$weekresult = mysqli_query($conn, $weekquery);

$week = array();

while ($rows = mysqli_fetch_assoc($weekresult)) { //DB에 저장된 데이터 수 (열 기준)
//    $ticks.append($rows['date_format(date,\'%d\')']);
//    $ticks=$rows['date_format(date,\'%d\')'];
    $date = $rows['m'];
    $count = (int)$rows['count(*)'];

    $week[] = [$date, $count];

}


//$b = new statsClass;
//////$R = $b->extract_YM(); // DB에서 년월 추출


?>


<!DOCTYPE html>
<head>


    <meta charset=UTF-8"/>
    <meta name="robots" content="noindex,nofollow"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA Compatible" control="IE=edge,chrome=1"/>
    <title>관리자 페이지</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="../jquery/jquery.jqplot.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.dateAxisRenderer.js"></script>

    <link rel="stylesheet" type="text/css" href="../jquery/jquery.jqplot.css"/>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <!-- http://www.jqplot.com 제공 자바스크립트 include -->
    <script type="text/javascript" src="../jquery/plugins/jqplot.barRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.pointLabels.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.canvasAxisTickRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.categoryAxisRenderer.js"></script>
    <script>
        google.charts.load('current', {'packages':['bar','corechart']});
        google.charts.setOnLoadCallback(schedulerSuccessAndFailChart);


        function schedulerSuccessAndFailChart() {
            var membercount=<?php echo $membervisitrow['count(*)'];?>;
            var guestcount=<?php echo $guestvisitrow['count(*)'];?>;
            var data = google.visualization.arrayToDataTable([
                ["Title","로그인방문자수", {role:'annotation'}, "GUEST방문자수", {role:'annotation'}],
                [""
                    ,membercount,membercount //성공데이터
                    ,guestcount, guestcount] //실패데이터
            ]);

            var barChartOption = {
                bars: 'vertical',
                height :260,
                width :'100%',
                legend: { position: "top" },
                isStacked: false,
                tooltip:{textStyle : {fontSize:12}, showColorCode : true},
                animation: { //차트가 뿌려질때 실행될 애니메이션 효과
                    startup: true,
                    duration: 1000,
                    easing: 'linear' },
                annotations: {
                    textStyle: {
                        fontSize: 15,
                        bold: true,
                        italic: true,
                        color: '#871b47',
                        auraColor: '#d799ae',
                        opacity: 0.8
                    }
                }
            };

            var chart = new google.visualization.BarChart(document.getElementById('bar_chart_div'));

            chart.draw(data, barChartOption);
            //반응형 그래프 출력 - 반응형 그래프를 원하지 않을 시 제거하거나 주석처리 하세요.
            window.addEventListener('resize', function() { chart.draw(data, barChartOption); }, false);
        }
        function test() {
            chart.destroy();
        }



    </script>

    <script type="text/javascript">
        $(document).ready(function () {

            let line1 = <?php echo json_encode($member);?>;

            let line2 = [10, 8, 22, 15, 10];  // 막대그래프를 2개 그리고자 할 경우
            let xAxis = <?php echo json_encode($R);?>;
            renderChart(line1, xAxis);

            let line5 = <?php echo json_encode($week);?>;

            let plot1 = $.jqplot('chart1', [line5], {
                title: '이번주 로그인한 방문수',
                axes: {
                    xaxis: {
                        renderer: $.jqplot.DateAxisRenderer
                    }
                },
                series: [{lineWidth: 4, markerOptions: {style: 'square'}}]
            });


            $('#selectmonth').on('change', function () {
                if (this.value !== "") {
                    var optVal = $(this).find(":selected").val();
                    //var year = optVal.substr(0,4); // 년
                    //var month = optVal.substr(4,2); // 월
                    $.post('../admin/ajaxdb.php', {optVal: optVal}, function (msg) {

                        var jsonObj = $.parseJSON(msg); // JSON 문자열을 JavaScript object 로 반환
                        data = jsonObj.data; // 문자열을 배열로 변환
                        xAxis = jsonObj.ticks;
                        updatePlot(data, xAxis);

                    });

                }
            });

        });


        function renderChart(data, xAxis) {
            plot1 = $.jqplot('chartDiv', [data], CreateBarChartOptions(xAxis));
        }

        function updatePlot(data, xAxis) {
            plot1.destroy();
            plot1 = $.jqplot('chartDiv', [data], CreateBarChartOptions(xAxis));
        }

        function CreateBarChartOptions(xAxis) {
            var optionsObj = {
                title: '일자별 접속 통계',
                seriesDefaults: {
                    renderer: $.jqplot.BarRenderer, // 막대 그래프
                    rendererOptions: {barWidth: 15}, // bar의 너비 수동으로 설정
                    pointLabels: {show: true} // 레이블 값
                },
                axes: {
                    xaxis: {
                        renderer: $.jqplot.CategoryAxisRenderer,
                        ticks: xAxis,
                        tickOptions: {
                            formatString: '%d' // 정수형으로 표시
                        }
                        //label:'일자'
                    }
                },
                highlighter: {show: false}
            };
            return optionsObj;
        }
    </script>


</head>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.php">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Home</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="../main.php ">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>메인화면</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            커뮤니티
        </div>

        <!-- Nav Item - Pages Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>게시판</span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">게시판:</h6>
                    <a class="collapse-item" href="../board.php">게시판</a>
                    <a class="collapse-item" href="../admin/adminboard.php">삭제된 게시글</a>
                </div>
            </div>
        </li>

        <!-- Nav Item - Utilities Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
               aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fas fa-fw fa-wrench"></i>
                <span>채팅</span>
            </a>
            <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
                 data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">채팅 :</h6>
                    <a class="collapse-item" href="../chating.php">채팅</a>
                    <a class="collapse-item" href="../admin/adminchattingbroadcast.php">전체 채팅 기록보기</a>
                    <!--채팅기록보는 사이트 만들기 간단함-->
                    <a class="collapse-item" href="../admin/adminchattingforme.php">나한테온 채팅 보기</a>
                </div>
            </div>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            방문자정보
        </div>

        <!-- Nav Item - Pages Collapse Menu -->

        <!-- Nav Item - Charts -->
        <li class="nav-item">
            <a class="nav-link" href="charts.php">
                <i class="fas fa-fw fa-chart-area"></i>
                <span>Charts</span></a>
        </li>

        <!-- Nav Item - Tables -->
        <li class="nav-item">
            <a class="nav-link" href="tables.php">
                <i class="fas fa-fw fa-table"></i>
                <span>방문자 기록</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>


                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>


                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">강지윤</span>
                            <img class="img-profile rounded-circle" src="../uploads/1582258879jpeg">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <h1 class="h3 mb-2 text-gray-800">차트</h1>
                <p class="mb-4">제 사이트 방문자수를 차트로 보기쉽게 표현하였습니다.</p>

                <!-- Content Row -->
                <div class="row">

                    <div class="col-xl-8 col-lg-7">

                        <!-- Area Chart -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">이번주 방문자 기록</h6>
                            </div>
                            <div class="card-body">
                                <div id="chart1"></div>
                                <hr>

                            </div>
                        </div>

                        <!-- Bar Chart -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Bar Chart</h6>
                            </div>
                            <div class="card-body">
                                <div id="bar_chart_div">

                                </div>
                                <BUTTON onclick="test()">테스트</BUTTON>
                                <hr>
                                Styling for the bar chart can be found in the <code>/js/demo/chart-bar-demo.js</code>
                                file.
                            </div>
                        </div>

                    </div>


                    <div class="col-xl-4 col-lg-5">
                        <div class="card shadow mb-4">
                            <!-- Card Header - Dropdown -->
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">월별 차트</h6>
                            </div>
                            <!-- Card Body -->
                            <div class="card-body">
                                <div class="chart-pie pt-4">
                                    <div id="chartDiv"></div>
                                    <select name="month" id="selectmonth">
                                        <option value="">년월 선택</option>
                                        <?php
                                        $optionquery = "select date_format(date,'%Y-%m') m,count(*) from membervisit where date like '2020-%-%' group by m;";
                                        $optionresult = mysqli_query($conn, $optionquery);

                                        while ($optionrows = mysqli_fetch_assoc($optionresult)) {
                                            echo "<option value='" . $optionrows['m'] . "' ";
                                            echo ">" . $optionrows['m'] . "</option>\n";
                                        }
                                        ?>
                                    </select>
                                </div>
                                <hr>
                                <br>
                                <br>
                                일일 별 로그인한 회원수를 볼수있습니다
                            </div>
                        </div>
                    </div>                    <!-- Donut Chart -->


                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Your Website 2019</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="../logout.php">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="../admin/js/demo/chart-area-demo.js"></script>
    <script src="../admin/js/demo/chart-pie-demo.js"></script>
    <script src="../admin/js/demo/chart-bar-demo.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

    <!--    <script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>-->

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="../jquery/jquery.jqplot.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.dateAxisRenderer.js"></script>


    <!-- http://www.jqplot.com 제공 자바스크립트 include -->
    <script type="text/javascript" src="../jquery/plugins/jqplot.barRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.pointLabels.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.canvasAxisTickRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.categoryAxisRenderer.js"></script>

    <!--    구글차트-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

</body>

</html>