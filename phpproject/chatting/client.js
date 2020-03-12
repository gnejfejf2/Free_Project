<html>
<head>
    <meta charset="utf-8">
    <title>Chat</title>
    <style>
        .chat_log{ width: 95%; height: 200px; }
        .name{ width: 10%; }
        .message{ width: 70%; }
        .chat{ width: 10%; }
    </style>
</head>
<body>
<div>
    <textarea id="chatLog" class="chat_log" readonly></textarea>
</div>
<form id="chatJoinForm">
    대화명 : <input id="nickname" name="nickname" type="text"> <input type="submit" value="입장">
</form>
<form id="chat">
    <input id="name" class="name" type="text" readonly>
    <input id="text" class="message" type="text">
    <input type="submit" class="chat" value="chat"/>
</form>
<div id="box" class="box">
    <script src="/socket.io/socket.io.js"></script> <!-- 1 -->
    <script src="//code.jquery.com/jquery-1.11.1.js"></script>
    <script>
        var socket; //1      var socket; //대화용 소켓생성

        $(function(){
            $('#chat').on('submit', function(e){ //2
                socket = io();
                socket.emit('send message', $('#name').val(), $('#text').val());
                $('#text').val('');
                $('#text').focus();
                e.preventDefault();
            });
            socket.on('receive message', function(msg){ //3
                $('#chatLog').append(msg+'\n');
                $('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
            });
            socket.on('change name', function(name){ //4
                $('#name').val(name);
            });
        });
        //연결되어있는 서버에 메세지 보내기


    </script>
</body>
</html>