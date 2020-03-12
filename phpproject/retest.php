
<!doctype html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" href="/css/comment.css" />
    <script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body>

<!-- 글 불러오기 -->
<div id="board_read">
    <h2>테스트</h2>
    <div id="user_info">
       조회:테스트1
        <div id="bo_line"></div>
    </div>
    <div id="bo_content">
       테스트2
    </div>
    <!-- 목록, 수정, 삭제 -->
    <div id="bo_ser">
        <ul>
            <li><a href="/">[목록으로]</a></li>
            <li><a href="modify.php?idx=123">[수정]</a></li>
            <li><a href="delete.php?idx=123">[삭제]</a></li>
        </ul>
    </div>

    <!--- 댓글 불러오기 -->
    <div class="reply_view">
        <h3>댓글목록</h3>

            <div class="dap_lo">
                <div><b>1</b></div>
                <div class="dap_to comt_edit">2</div>
                <div class="rep_me dap_to">3</div>
                <div class="rep_me rep_menu">
                    <a class="dat_edit_bt" href="#">수정</a>
                    <a class="dat_delete_bt" href="#">삭제</a>
                </div>
                <!-- 댓글 수정 폼 dialog -->
                <div class="dat_edit">
                    <form method="post" action="rep_modify_ok.php">
                        <input type="hidden" name="rno" value="123" /><input type="hidden" name="b_no" value="123">
                        <input type="password" name="pw" class="dap_sm" placeholder="비밀번호" />
                        <textarea name="content" class="dap_edit_t">123</textarea>
                        <input type="submit" value="수정하기" class="re_mo_bt">
                    </form>
                </div>
                <!-- 댓글 삭제 비밀번호 확인 -->
                <div class='dat_delete'>
                    <form action="reply_delete.php" method="post">
                        <input type="hidden" name="rno" value="123" /><input type="hidden" name="b_no" value="123">
                        <p>비밀번호<input type="password" name="pw" /> <input type="submit" value="확인"></p>
                    </form>
                </div>
            </div>
       

        <!--- 댓글 입력 폼 -->
        <div class="dap_ins">
            <input type="hidden" name="bno" class="bno" value="123">
            <input type="text" name="dat_user" id="dat_user" class="dat_user" size="15" placeholder="아이디">
            <input type="password" name="dat_pw" id="dat_pw" class="dat_pw" size="15" placeholder="비밀번호">
            <div style="margin-top:10px; ">
                <textarea name="content" class="reply_content" id="re_content" ></textarea>
                <button id="rep_bt" class="re_bt">댓글</button>
            </div>
        </div>
    </div><!--- 댓글 불러오기 끝 -->
    <div id="foot_box"></div>
</div>
</body>
</html>