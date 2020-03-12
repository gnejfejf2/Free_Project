<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.
include("./nologin.php");
include("./visitchart.php");

$mode = $_POST['mode'];

if ($mode != 'insert' && $mode != 'modify') { // 아무런 모드가 없다면 중단
    echo "<script>alert('mode 값이 제대로 넘어오지 않았습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}

switch ($mode) {
    case 'insert' :
        $mb_id = trim($_POST['mb_id']);
        $title = "회원가입";
        break;
    case 'modify' :
        $mb_id = trim($_SESSION['mb_id']);
        $title = "회원수정";
        break;
}

$mb_password = trim($_POST['mb_password']); // 첫번째 입력 패스워드
$mb_password_re = trim($_POST['mb_password_re']); // 두번째 입력 패스워드
$mb_name = trim($_POST['mb_name']); // 이름
$mb_email = trim($_POST['mb_email']); // 이메일
$mb_gender = $_POST['mb_gender']; // 성별
$mb_job = $_POST['mb_job']; // 직업
$mb_ip = $_SERVER['REMOTE_ADDR']; // 접속 아이피
$mb_language = implode(",", $_POST['mb_language']); // 관심언어 (,) 구분으로 저장
$mb_datetime = date('Y-m-d H:i:s', time()); // 가입일
$mb_modify_datetime = date('Y-m-d H:i:s', time()); // 수정일

if (!$mb_id) {
    echo "<script>alert('아이디가 넘어오지 않았습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}

if (!$mb_password) {
    echo "<script>alert('비밀번호가 넘어오지 않았습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}

if ($mb_password != $mb_password_re) {
    echo "<script>alert('비밀번호가 일치하지 않습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}

if (!$mb_name) {
    echo "<script>alert('이름이 넘어오지 않았습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}

if (!$mb_email) {
    echo "<script>alert('이메일이 넘어오지 않았습니다.');</script>";
    echo "<script>location.replace('./register.php');</script>";
    exit;
}
$mb_password = password_hash($mb_password, PASSWORD_DEFAULT);


if ($mode == "insert") { // 신규 등록 상태

    $sql = " SELECT * FROM member WHERE mb_id = '$mb_id' "; // 회원가입을 시도하는 아이디가 사용중인 아이디인지 체크
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) { // 만약 사용중인 아이디라면 알림창을 띄우고 회원가입 페이지로 이동
        echo "<script>alert('이미 사용중인 회원아이디 입니다.');</script>";
        echo "<script>location.replace('./register.php');</script>";
        exit;
    }

    $sql = " INSERT INTO member
                SET mb_id = '$mb_id',
                     mb_password = '$mb_password',
                     mb_name = '$mb_name',
                     mb_email = '$mb_email',
                     mb_gender = '$mb_gender',
                     mb_job = '$mb_job',
                     mb_ip = '$mb_ip',
                     mb_language = '$mb_language',
                     mb_datetime = '$mb_datetime' ";
    $result = mysqli_query($conn, $sql);

} else if ($mode == "modify") { // 회원 수정 상태

    $sql = " UPDATE member
                SET mb_password = '$mb_password',
                     mb_email = '$mb_email',
                     mb_gender = '$mb_gender',
                     mb_job = '$mb_job',
                     mb_language = '$mb_language',
                     mb_modify_datetime = '$mb_modify_datetime'
             WHERE mb_id = '$mb_id' ";
    $result = mysqli_query($conn, $sql);
}

if ($result) {

    if ($mode == "insert") { // 신규 가입의 경우 무조건 메일 인증확인 메일 발송
        include_once('./test.php'); // 메일 전송을 위한 파일을 인클루드합니다.

        $mb_md5 = md5(pack('V*', rand(), rand(), rand(), rand())); // 어떠한 회원정보도 포함되지 않은 일회용 난수를 생성하여 인증에 사용

        $sql = " UPDATE member SET mb_email_certify2 = '$mb_md5' WHERE mb_id = '$mb_id' "; // 회원가입을 시도하는 아이디에 메일 인증을 위한 일회용 난수를 업데이트
        $result = mysqli_query($conn, $sql);
        mysqli_close($conn); // 데이터베이스 접속 종료


        mailer("지윤", "gnejfejf3@naver.com", $mb_email, "인증번호 입니다.", "인증번호는 :" . $mb_md5 . "입니다.");

        echo "<script>alert('" . $title . "이 완료 되었습니다.\\n신규가입의 경우 메일인증을 받으셔야 로그인 가능합니다.');</script>";

        echo "<form action=\"email_certify.php\" method=\"POST\" name=form>

          <input type=\"hidden\" name=\"mb_id\"  value=\"{$mb_id}\">

          <script>document.form.submit();</script>

          </form>";


    }
    else{
        echo "<script>alert('수정이 완료 되었습니다.');</script>";

        echo "<script>location.replace('./main.php');</script>";

    }

    exit;
} else {
    echo "생성 실패: " . mysqli_error($conn);
    mysqli_close($conn); // 데이터베이스 접속 종료
}
?>
