<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./visitchart.php");


?>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Freelancer - 강지윤</title>

    <!-- Custom fonts for this theme -->
    <link href="./vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">

    <!-- Theme CSS -->
    <link href="./css/freelancer.css" rel="stylesheet">

</head>

<body id="page-top">

<!-- Navigation -->
<nav class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top" id="mainNav">
    <div class="container">
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                    <?php
                    if (!isset($_SESSION['mb_id'])) {
                        $nowid = 'GUEST';
                        echo $nowid;
                    } else {
                        $nowid = $_SESSION[mb_id];
                        echo $nowid . '님';
                    }
                    ?></span>
            </a>
            <!-- Dropdown - User Information -->
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <?php
                if ($_SESSION['mb_id'] == "gnejfejf2") {
                    $nowid = "주인장";
                    echo $nowid . '님';

                    ?>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="./mypage.php">
                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                        내정보
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="../admin/index.php">
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        관리자페이지
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="./logout.php">
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        Logout
                    </a>
                    <?php
                } else if (isset($_SESSION['mb_id'])) {
                    $nowid = $_SESSION[mb_id];
                    echo $nowid . '님';

                    ?>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="./mypage.php">
                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                        내정보
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="./logout.php">
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        Logout
                    </a>
                    <?php
                } else {
                    ?>
                    <a class="dropdown-item" href="./login.php"
                    >
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        로그인
                    </a>
                    <?php
                }
                ?>
            </div>
        </li>


        <button class="navbar-toggler navbar-toggler-right text-uppercase font-weight-bold bg-primary text-white rounded"
                type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive"
                aria-expanded="false" aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item mx-0 mx-lg-1">
                    <a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="#portfolio">포트폴리오</a>
                </li>
                <li class="nav-item mx-0 mx-lg-1">
                    <a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="#about">내 실력</a>
                </li>
                <li class="nav-item mx-0 mx-lg-1">
                    <a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="#contact">메일보내기</a>
                </li>
                <li class="nav-item mx-0 mx-lg-1">
                    <a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="./board.php">게시판</a>
                </li>
                <li class="nav-item mx-0 mx-lg-1">
                    <a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="./chating.php">채팅</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Masthead -->
<header class="masthead bg-primary text-white text-center">
    <div class="container d-flex align-items-center flex-column">

        <!-- Masthead Avatar Image -->
        <img class="masthead-avatar mb-5" src="uploads/1582258879jpeg" alt="">

        <!-- Masthead Heading -->
        <h1 class="masthead-heading text-uppercase mb-0"><?php echo $nowid; ?>님 환영합니다.</h1>

        <!-- Icon Divider -->
        <div class="divider-custom divider-light">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <!-- Masthead Subheading -->
        <p class="masthead-subheading font-weight-light mb-0">팀노바 5기 강지윤 기초~ing</p>

    </div>
</header>

<!-- Portfolio Section -->
<section class="page-section portfolio" id="portfolio">
    <div class="container">

        <!-- Portfolio Section Heading -->
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">작품소개</h2>

        <!-- Icon Divider -->
        <div class="divider-custom">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <!-- Portfolio Grid Items -->
        <div class="row">

            <!-- Portfolio Item 1 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal1">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/java.png" alt="">
                </div>
            </div>

            <!-- Portfolio Item 2 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal2">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/android.jpg" alt="">
                </div>
            </div>

            <!-- Portfolio Item 3 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal3">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/circus.png" alt="">
                </div>
            </div>

            <!-- Portfolio Item 4 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal4">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/game.png" alt="">
                </div>
            </div>

            <!-- Portfolio Item 5 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal5">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/safe.png" alt="">
                </div>
            </div>

            <!-- Portfolio Item 6 -->
            <div class="col-md-6 col-lg-4">
                <div class="portfolio-item mx-auto" data-toggle="modal" data-target="#portfolioModal6">
                    <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                        <div class="portfolio-item-caption-content text-center text-white">
                            <i class="fas fa-plus fa-3x"></i>
                        </div>
                    </div>
                    <img class="img-fluid" src="img/portfolio/submarine.png" alt="">
                </div>
            </div>

        </div>
        <!-- /.row -->

    </div>
</section>

<!-- About Section -->
<section class="page-section bg-primary text-white mb-0" id="about">
    <div class="container">

        <!-- About Section Heading -->
        <h2 class="page-section-heading text-center text-uppercase text-white">자기 소개</h2>

        <!-- Icon Divider -->
        <div class="divider-custom divider-light">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <!-- About Section Content -->
        <div class="row">
            <div class="col-lg-4 ml-auto">
                <p class="lead">팀노바 5기 강지윤입니다 <br> 명지대학교 4학년 정보통신공학과 휴학중에 있으며 <br> 좋은 프로그래머가 되기 위해 11월 4일부터 팀노바에서 공부중에
                    있습니다 <br> 현재 기초 java와 android 을 공부 하였고 <br> 현재 PHP를 이용해 포트 폴리오 사이트를 만들고 있습니다 <br> 아직은 많이 부족하지만 긍정적으로
                    봐주시면 좋겠습니다</p>
            </div>
            <div class="col-lg-4 mr-auto">
                <p class="lead">기초 자바 : class 와 thread 를 이용하여 턴제 유희왕 게임을 만들었습니다. (2주만에 만든 작품이라 많이 엉성 합니다) <br>
                    안드로이드 : 커플통장 + 커플 커뮤니케이션 어플을 만들었습니다 구글맵등 여러 api를 활용하여 최대한 기능을 담아봤습니다
                    ex)구글맵 장바구니 가게등록 등등..
                    <br> 아직 영상과 소스 파일을 업로드 하지 못하였습니다 추후 업로드 예정입니다.
                    PHP : 현재 보시고있는 홈페이지입니다. 로그인부터 게시판까지 모두 서버를 직접 구축하였고 아직 완성단계전이라 많이 미흡합니다.
                    현재 추가 예정기능은 저와 1대1 대화를 할수있는 채팅창과 관리자 페이지를 추가할 예정입니다.
                </p>
            </div>
        </div>

        <!-- About Section Button -->
        <div class="text-center mt-4">
            <a class="btn btn-xl btn-outline-light" href="./projectfile/newcardgame.zip">
                <i class="fas fa-download mr-2"></i>
                작품소스 다운받기
            </a>
        </div>

    </div>
</section>

<!-- Contact Section -->
<section class="page-section" id="contact">
    <div class="container">

        <!-- Contact Section Heading -->
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">스카웃 제의</h2>

        <!-- Icon Divider -->
        <div class="divider-custom">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <!-- Contact Section Form -->
        <div class="row">
            <div class="col-lg-8 mx-auto">
                <!-- To configure the contact form email address, go to mail/contact_me.php and update the email address in the PHP file on line 19. -->
                <form name="sentMessage" id="contactForm" novalidate="novalidate">
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls mb-0 pb-2">
                            <label>Name</label>
                            <input class="form-control" id="name" type="text" placeholder="성함" required="required"
                                   data-validation-required-message="당신의 이름을 넣어주세요">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls mb-0 pb-2">
                            <label>Email Address</label>
                            <input class="form-control" id="email" type="email" placeholder="이메일" required="required"
                                   data-validation-required-message="당신의 메일주소를 넣어주세요">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls mb-0 pb-2">
                            <label>Phone Number</label>
                            <input class="form-control" id="phone" type="tel" placeholder="휴대전화" required="required"
                                   data-validation-required-message="연락할수있는 번호를 넣어주세요">
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="form-group floating-label-form-group controls mb-0 pb-2">
                            <label>Message</label>
                            <textarea class="form-control" id="message" rows="5" placeholder="남기고싶은말"
                                      required="required"
                                      data-validation-required-message="저한테 전할 메세지를 넣어주세요"></textarea>
                            <p class="help-block text-danger"></p>
                        </div>
                    </div>
                    <br>
                    <div id="success"></div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-xl" id="sendMessageButton">전송</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</section>

<!-- Footer -->
<footer class="footer text-center">
    <div class="container">
        <div class="row">

            <!-- Footer Location -->
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">선호지역</h4>
                <p class="lead mb-0">서울,수원

            </div>

            <!-- Footer Social Icons -->
            <div class="col-lg-4 mb-5 mb-lg-0">
                <h4 class="text-uppercase mb-4">저를 만나보세요</h4>
                <a class="btn btn-outline-light btn-social mx-1"
                   href="https://www.facebook.com/profile.php?id=100003545956865">
                    <i class="fab fa-fw fa-facebook-f"></i>
                </a>
                <a class="btn btn-outline-light btn-social mx-1" href="https://open.kakao.com/o/sampvMFb">
                    <i class="fab fa-fw fa-twitter"></i>
                </a>
                <a class="btn btn-outline-light btn-social mx-1" href="#">
                    <i class="fab fa-fw fa-linkedin-in"></i>
                </a>
                <a class="btn btn-outline-light btn-social mx-1" href="#">
                    <i class="fab fa-fw fa-dribbble"></i>
                </a>
            </div>

            <!-- Footer About Text -->
            <div class="col-lg-4">
                <h4 class="text-uppercase mb-4">Bootstarp Information</h4>
                <p class="lead mb-0">Freelance is a free to use, MIT licensed Bootstrap theme created by
                    <a href="http://startbootstrap.com">Start Bootstrap</a>.</p>
            </div>

        </div>
    </div>
</footer>

<!-- Copyright Section -->
<section class="copyright py-4 text-center text-white">
    <div class="container">
        <small>Copyright &copy; Your Website 2019</small>
    </div>
</section>

<!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
<div class="scroll-to-top d-lg-none position-fixed ">
    <a class="js-scroll-trigger d-block text-center text-white rounded" href="#page-top">
        <i class="fa fa-chevron-up"></i>
    </a>
</div>

<!-- Portfolio Modals -->

<!-- Portfolio Modal 1 -->
<div class="portfolio-modal modal fade" id="portfolioModal1" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal1Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">기초 자바</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <video class="img-fluid rounded mb-5" oncontextmenu="return false;" id="myVideo" width="640"
                                   controls >

                                <source src="img/video/port1.mp4" type="video/mp4">

                            </video>
                            <p class="mb-5">[작품 이름]

                                유!희!왕! <br>

                                [작품 소개]<br>

                                유희왕 세계관을 인용한 몬스터배틀 게임(카드게임 아님)<br>

                                [주요 기능]<br>
                                쓰레드<br>
                                1. 오프닝,음악쓰레드<br>
                                2. 라이벌대결 :어둠의듀얼 쓰레드<br>
                                3. 보스 대결 :날씨,시간제한 ,보스자동공격 쓰레드<br>
                                4. 메인 화면 :힐링의 샘쓰레드(50미만일시동작)<br>
                                5. 메인 화면 :이벤트쓰레드(랜덤하게 이벤트가 실행)<br>
                                6. 특수 전투 :자동전투 쓰레드<br>


                                [후기 & 하고 싶은 말]<br>

                                처음으로 자바로 코딩을 해보고 하나의 프로그램까지 손수 직접 계획하고 기능을 구현하고 해봤습니다.<br>
                                막히는 부분도 많았고 파트장님과 맨토님들에게 많은 조언을 받아가면서 혼자 스스로 만들었다는 제 자신이 약간<br>
                                뿌듯합니다. 이제 시작이지만 추후의 프로젝트도 항상 최선을 다해서 만들겠습니다.</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Portfolio Modal 2 -->
<div class="portfolio-modal modal fade" id="portfolioModal2" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal2Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">안드로이드</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <video class="img-fluid rounded mb-5" oncontextmenu="return false;" id="myVideo2" width="640"
                            controls >

                                <source src="img/video/port2.mp4"  type="video/mp4">

                            </video>
                            <!-- Portfolio Modal - Text -->
                            <p class="mb-5">[작품 이름] :
                                데이트팝<br>

                                [작품 소개] :
                                데이트 통장 커플 커뮤니케이션 어플입니다.

                                [주요 기능]<br>
                                <br>
                                ■ php를 이용한 로그인 회원가입<br>
                                <br>
                                ■ 프로필 편집<br>
                                - 나와 상대방의 프로필을 수정할수있고 사귄날짜를 선택해서 변경가능<br>
                                <br>
                                ■ SQL양식을 기반으로 한 게시판 메모장<br>
                                <br>
                                -글과 여러가지 사진을 올릴 수 있는 게시판<br>
                                -SQL양식의 내부저장소에 이미지(URI)와 내용들을 저장후 보여줌<br>
                                -게시글 수정,삭제 가능<br>
                                -댓글 입력, 수정 , 삭제 가능<br>
                                -유저에 따라 권한 부여<br>
                                <br>
                                ■ 사업자 공간<br>
                                <br>
                                -사업자 인증을 한 유저만 자신의 가게를 등록가능<br>
                                -자신의 물품을 올려서 판매가능<br>
                                -회사위치와 로고를 구글맵을 이용하여 구현 (클러스터링기능 구현)<br>
                                <br>
                                ■ 알림기능<br>
                                -다른유저가 자신의 게시글에 댓글을 남길시 알람이 울림<br>
                                -fcm을 통해 메세지가 감<br>
                                <br>
                                ■ 장바구니 기능<br>
                                -사업자공간에서 올린 물품을 살수있음<br>
                                -한가지의 상품을 여러개 구매가능 (갯수기능 구현)<br>
                                <br>
                                ■화폐기능
                                -내부 어플리케이션에서만 통하는 머니를 만들어 결제 API를 이용하여 충전할수있음<br>
                                -불건을 구매할때 휴대폰결제 카카오페이결제 페이코 결제 기능을 구현함(API사용)<br>
                                -구매내역을 확일할수있는 공간이있음<br>
                                -제가 자주사용하는 은행의 문자를 파싱하여 자동으로 결제기록을 볼수있도록 구현하였음 (영상에서 공기계라 스스로에게 문자를 보낼수가없어서 시현하지못했음)</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Portfolio Modal 3 -->
<div class="portfolio-modal modal fade" id="portfolioModal3" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal3Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">Circus Tent</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <img class="img-fluid rounded mb-5" src="img/portfolio/circus.png" alt="">
                            <!-- Portfolio Modal - Text -->
                            <p class="mb-5">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Mollitia neque
                                assumenda ipsam nihil, molestias magnam, recusandae quos quis inventore quisquam velit
                                asperiores, vitae? Reprehenderit soluta, eos quod consequuntur itaque. Nam.</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Portfolio Modal 4 -->
<div class="portfolio-modal modal fade" id="portfolioModal4" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal4Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">Controller</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <img class="img-fluid rounded mb-5" src="img/portfolio/game.png" alt="">
                            <!-- Portfolio Modal - Text -->
                            <p class="mb-5">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Mollitia neque
                                assumenda ipsam nihil, molestias magnam, recusandae quos quis inventore quisquam velit
                                asperiores, vitae? Reprehenderit soluta, eos quod consequuntur itaque. Nam.</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Portfolio Modal 5 -->
<div class="portfolio-modal modal fade" id="portfolioModal5" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal5Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">Locked Safe</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <img class="img-fluid rounded mb-5" src="img/portfolio/safe.png" alt="">
                            <!-- Portfolio Modal - Text -->
                            <p class="mb-5">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Mollitia neque
                                assumenda ipsam nihil, molestias magnam, recusandae quos quis inventore quisquam velit
                                asperiores, vitae? Reprehenderit soluta, eos quod consequuntur itaque. Nam.</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Portfolio Modal 6 -->
<div class="portfolio-modal modal fade" id="portfolioModal6" tabindex="-1" role="dialog"
     aria-labelledby="portfolioModal6Label" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">
            <i class="fas fa-times"></i>
          </span>
            </button>
            <div class="modal-body text-center">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8">
                            <!-- Portfolio Modal - Title -->
                            <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">Submarine</h2>
                            <!-- Icon Divider -->
                            <div class="divider-custom">
                                <div class="divider-custom-line"></div>
                                <div class="divider-custom-icon">
                                    <i class="fas fa-star"></i>
                                </div>
                                <div class="divider-custom-line"></div>
                            </div>
                            <!-- Portfolio Modal - Image -->
                            <img class="img-fluid rounded mb-5" src="img/portfolio/submarine.png" alt="">
                            <!-- Portfolio Modal - Text -->
                            <p class="mb-5">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Mollitia neque
                                assumenda ipsam nihil, molestias magnam, recusandae quos quis inventore quisquam velit
                                asperiores, vitae? Reprehenderit soluta, eos quod consequuntur itaque. Nam.</p>
                            <button class="btn btn-primary" href="#" data-dismiss="modal">
                                <i class="fas fa-times fa-fw"></i>
                                Close Window
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Contact Form JavaScript -->
<script src="js/jqBootstrapValidation.js"></script>
<script src="js/contact_me.min.js"></script>

<!-- Custom scripts for this template -->
<script src="js/freelancer.js"></script>

</body>

</html>