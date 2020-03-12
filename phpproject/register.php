<?php
include("./dbconn.php");  // DB연결을 위한 같은 경로의 dbconn.php를 인클루드합니다.;
include("./visitchart.php");

if (isset($_SESSION['mb_id']) && $_GET['mode'] == 'modify') { // 세션이 있고 회원수정 mode라면 회원 정보를 가져옴
    $mb_id = $_SESSION['mb_id'];

    $sql = " SELECT * FROM member WHERE mb_id = '$mb_id' "; // 회원 정보를 조회
    $result = mysqli_query($conn, $sql);
    $mb = mysqli_fetch_assoc($result);
    mysqli_close($conn); // 데이터베이스 접속 종료

    $mode = "modify";
    $title = "회원수정";
    $modify_mb_info = "readonly";
} else {
    $mode = "insert";
    $title = "회원가입";
    $modify_mb_info = '';
}
?>
<html>
<head>
    
    <meta charset="utf-8">

    <link href="../css/freelancer.css" rel="stylesheet">

</head>
<body>
<article class="container">
    <div class="page-header">
        <div class="col-md-6 col-md-offset-3">
            <h3>회원가입</h3>
        </div>
    </div>
    <div class="col-sm-6 col-md-offset-3">
        <form action="./register_update.php" onsubmit="return fregisterform_submit(this);" method="post">
            <input type="hidden" name="mode" value="<?php echo $mode ?>">

            <table>
                <tr>
                    <th>아이디</th>
                    <td><input type="text" name="mb_id"
                               value="<?php echo $mb['mb_id'] ?>" <?php echo $modify_mb_info ?>></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="password" name="mb_password"></td>
                </tr>
                <tr>
                    <th>비밀번호 확인</th>
                    <td><input type="password" name="mb_password_re"></td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><input type="text" name="mb_name"
                               value="<?php echo $mb['mb_name'] ?>" <?php echo $modify_mb_info ?>></td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><input type="text" name="mb_email" value="<?php echo $mb['mb_email'] ?>"></td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>
                        <label><input type="radio" name="mb_gender"
                                      value="남자" <?php echo ($mb['mb_gender'] == "남자") ? "checked" : ""; ?> >남자</label>
                        <label><input type="radio" name="mb_gender"
                                      value="여자" <?php echo ($mb['mb_gender'] == "여자") ? "checked" : ""; ?> >여자</label>
                    </td>
                </tr>
                <tr>
                    <th>직업</th>
                    <td>
                        <select name="mb_job">
                            <option value="">선택하세요</option>
                            <option value="학생" <?php echo ($mb['mb_job'] == "학생") ? "selected" : ""; ?> >학생</option>
                            <option value="회사원" <?php echo ($mb['mb_job'] == "회사원") ? "selected" : ""; ?> >회사원</option>
                            <option value="공무원" <?php echo ($mb['mb_job'] == "공무원") ? "selected" : ""; ?> >공무원</option>
                            <option value="주부" <?php echo ($mb['mb_job'] == "주부") ? "selected" : ""; ?> >주부</option>
                            <option value="무직" <?php echo ($mb['mb_job'] == "무직") ? "selected" : ""; ?> >무직</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>관심언어(필수 X)</th>
                    <td>
                        <label><input type="checkbox" name="mb_language[]"
                                      value="HTML" <?php echo strpos($mb['mb_language'], 'HTML') !== false ? 'checked' : '' ?>>HTML</label>
                        <label><input type="checkbox" name="mb_language[]"
                                      value="CSS" <?php echo strpos($mb['mb_language'], 'CSS') !== false ? 'checked' : '' ?>>CSS</label>
                        <label><input type="checkbox" name="mb_language[]"
                                      value="PHP" <?php echo strpos($mb['mb_language'], 'PHP') !== false ? 'checked' : '' ?>>PHP</label>
                        <label><input type="checkbox" name="mb_language[]"
                                      value="MySQL" <?php echo strpos($mb['mb_language'], 'MySQL') !== false ? 'checked' : '' ?>>MySQL</label>
                    </td>
                </tr>
            </table>
            <?php if ($mode == "insert") {?>
            <div class="form-group">
                <div data-toggle="buttons">
                    <a href="https:www.naver.com">이용약관</a>에 동의합니다.
                    <input name="agreebox" type="checkbox" value="YES">
                </div>
            </div>
            <?php }?>
            <div class="form-group text-center">
                <button type="submit" id="join-submit" class="btn btn-primary">
                    회원가입<i class="fa fa-check spaceLeft"></i>
                </button>
                <a href="login.php" type="submit" class="btn btn-warning">
                    가입취소<i class="fa fa-times spaceLeft"></i>
                </a>
            </div>
        </form>

    </div>


</article>

<script>
    function fregisterform_submit(f) { // submit 최종 폼체크

        if (f.mb_id.value.length < 1) { // 회원아이디 검사
            alert("아이디를 입력하십시오.");
            f.mb_id.focus();
            return false;

        }

        if (f.mb_name.value.length < 1) { // 이름 검사
            alert("이름을 입력하십시오.");
            f.mb_name.focus();
            return false;
        }

        if (f.mb_password.value.length < 3) {
            alert("비밀번호를 3글자 이상 입력하십시오.");
            f.mb_password.focus();
            return false;
        }

        if (f.mb_password.value != f.mb_password_re.value) {
            alert("비밀번호가 같지 않습니다.");
            f.mb_password_re.focus();
            return false;
        }

        if (f.mb_password.value.length > 0) {
            if (f.mb_password_re.value.length < 3) {
                alert("비밀번호를 3글자 이상 입력하십시오.");
                f.mb_password_re.focus();
                return false;
            }
        }

        if (f.mb_email.value.length < 1) { // 이메일 검사
            alert("이메일을 입력하십시오.");
            f.mb_email.focus();
            return false;
        }

        if (f.mb_email.value.length > 0) { // 이메일 형식 검사
            var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (f.mb_email.value.match(regExp) == null) {
                alert("이메일 주소가 형식에 맞지 않습니다.");
                f.mb_email.focus();
                return false;
            }
        }

        if (f.mb_gender.value.length < 1) {
            alert("성별을 선택해 주세요.");
            return false;
        }

        if (f.mb_job.value.length < 1) {

            alert("직업을 선택해 주세요. ");
            f.mb_job.focus();
            return false;
        }

        if ($mode == "insert") {
        if ($("input:checkbox[name='agreebox']").is(":checked") == false) {


            alert("이용약관을 체크해 주세요.");
            f.agreebox.focus();
            return false;
        }
        }

        return true;

    }
</script>

</body>
</html>