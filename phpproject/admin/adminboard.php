<?php
include("../dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("../admin/admincheck.php");

$query = "select * from board where hit=1 order by number desc";
$result = $conn->query($query);
$total = mysqli_num_rows($result);

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
    <link href="../css/board.css" rel="stylesheet">
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="../jquery/jquery.jqplot.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.dateAxisRenderer.js"></script>

    <link rel="stylesheet" type="text/css" href="../jquery/jquery.jqplot.css"/>
    <!-- http://www.jqplot.com 제공 자바스크립트 include -->
    <script type="text/javascript" src="../jquery/plugins/jqplot.barRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.pointLabels.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.canvasAxisTickRenderer.js"></script>
    <script type="text/javascript" src="../jquery/plugins/jqplot.categoryAxisRenderer.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            var line1 = <?php echo json_encode($member);?>;
            var line2 = [10, 8, 22, 15, 10];  // 막대그래프를 2개 그리고자 할 경우
            var xAxis = <?php echo json_encode($R);?>;

            renderChart(line2, line2);

            var line5 = <?php echo json_encode($week);?>;

            var plot1 = $.jqplot('chart1', [line5], {
                title: '이번주 방문자수',
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
            <div class="container-fluid" style="overflow: auto;">

                <h2 align=center>게시판</h2>
                <table align=center>
                    <thead align="center">
                    <tr>
                        <td width="50" align="center">번호</td>
                        <td width="500" align="center">제목</td>
                        <td width="100" align="center">작성자</td>
                        <td width="200" align="center">날짜</td>
                    </tr>
                    </thead>

                    <tbody>
                    <?php
                    while ($rows = mysqli_fetch_assoc($result)) { //DB에 저장된 데이터 수 (열 기준)
                        if ($total % 2 == 0) {
                            ?>                      <tr class="even">
                        <?php } else {
                            ?>                      <tr>
                        <?php } ?>
                        <td width="50" align="center"><?php echo $total ?></td>
                        <td width="500" align="center">
                            <a href="view.php?number=<?php echo $rows['number'] ?>">
                            <?php echo $rows['title'] ?></td>
                        <td width="100" align="center"><?php echo $rows['id'] ?></td>
                        <td width="200" align="center"><?php echo $rows['date'] ?></td>
                        </tr>
                        <?php
                        $total--;
                    }
                    ?>
                    </tbody>
                </table>


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


</body>

</html>