var app = require('express')();
var http = require('http').createServer(app);
var io = require('socket.io')(http);
var ejs = require('ejs');
2.
var moment = require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");
var bodyParser = require('body-parser');
var nowNickName = "";
var num = "";
var nickName = "";
var mysql = require('mysql');
var connection = mysql.createConnection({
    host: '127.0.0.1',
    user: 'root',
    password: '0496',
    port: 3306,
    database: 'test'
});


connection.connect();

app.use(bodyParser.urlencoded({
    extended: true
}));

app.engine('html', ejs.renderFile);
app.get('/', function (req, res) {
    var ip = req.headers['x-forwarded-for'] ||
        req.connection.remoteAddress ||
        req.socket.remoteAddress ||
        req.connection.socket.remoteAddress;
    num = ip.replace(/[^0-9]/g, "");


    var query = "SELECT user FROM chatuser WHERE ip='" + num + "';";
    connection.query(query, (err, rows, filds) => {

        if (!err) {

            if(rows.length==0){
                console.log("골로간다..");
                res.send('정상적인 루트가 아닙니다');
            }
            else {
                console.log(rows[0].user);
                nowNickName = rows[0].user;
                res.render(__dirname + '/index.html');
            }

        } else {
            console.log("이거나오냐");
            console.log(err);
        }

    });



});



var whoIsTyping = [];
var whoIsOn = [];
var socket_ids = [];


io.on('connection', function (socket) {


    var nickName = nowNickName || socket.id;
    var ipnum = num || socket.id;
    socket.emit('selfData', {nickName: nickName});

    socket_ids[nickName] = socket.id;

    console.log('내아이디는?' + nickName);

    whoIsOn.push(nickName);


    console.log("ip:" + num);

    if (whoIsTyping.length != 0) {
        io.emit('typing', whoIsTyping);
    }

    connection.query('SELECT pk, user, content FROM chat WHERE sideuser="broadcast" ORDER BY pk DESC LIMIT 50;', (err, rows, filds) => {
        if (!err) {


            for (let i = rows.length - 1; i >= 0; i--) {
                var msg = rows[i].user + ' : ' + rows[i].content;
                io.to(socket_ids[nickName]).emit('mySaying', msg);
            }
        } else console.log(err);
        io.emit('login', whoIsOn);


    });

    connection.query('DELETE FROM chatuser WHERE ip = ?', num, function (error, result) {
        if (error) {
            throw error;
        }
    });


    socket.on('setNickName', function (_nickName) {
        var pastNickName = nickName;	//past nickname
        nickName = _nickName;
        socket_ids[nickName] = socket.id;
        if (whoIsTyping.indexOf(pastNickName) != -1) {
            //if he was typing
            console.log('setNickName debug1');
            whoIsTyping.splice(whoIsTyping.indexOf(pastNickName), 1, nickName);
            io.emit('typing', whoIsTyping);
        }

        if (whoIsOn.indexOf(pastNickName) != -1) {
            console.log('setNickName debug2');
            whoIsOn.splice(whoIsOn.indexOf(pastNickName), 1, nickName);
        }
        io.emit('setNickName', {past: pastNickName, current: nickName, whoIsOn: whoIsOn});
        console.log(socket.id + '  to  ' + nickName);
    });

    socket.on('say', function (msg) {
        console.log('message: ' + msg);
        var date = moment().format('YYYY-MM-DD HH:mm:ss');
        connection.query('INSERT INTO chat(USER, CONTENT,SIDEUSER,IP,DATE) VALUES(\'' + nickName + '\',\'' + msg + '\',\'' + 'broadcast' + '\',\'' + ipnum + '\',\'' + date + '\')', (err, rows, filds) => {
            if (!err) {
                console.log(filds);
            } else {
                console.log(err);
            }
        });


        socket.broadcast.emit('chat message', nickName + '  :  ' + msg);
        socket.emit('mySaying', nickName + '  :  ' + msg);
    });

    socket.on('secretsay', function (name, msg) {
        console.log('message: ' + msg);
        if (name == "gnejfejf2") {
            if (whoIsOn.indexOf(name) == -1) {
                var date = moment().format('YYYY-MM-DD HH:mm:ss');
                console.log(date);
                connection.query('INSERT INTO chat(USER, CONTENT,SIDEUSER,IP,DATE) VALUES(\'' + nickName + '\',\'' + msg + '\',\'' + name + '\',\'' + ipnum + '\',\'' + date + '\')', (err, rows, filds) => {
                    if (!err) {
                        console.log(filds);
                    } else {
                        console.log(err);
                    }
                });

                socket.emit('mySaying', '관리자에게 개인톡을 보냈습니다 오프라인중이라 추후 확인후 답변이 올 예정입니다.');
            } else {
                var date = moment().format('YYYY-MM-DD HH:mm:ss');
                console.log(date);
                connection.query('INSERT INTO chat(USER, CONTENT,SIDEUSER,IP,DATE) VALUES(\'' + nickName + '\',\'' + msg + '\',\'' + name + '\',\'' + ipnum + '\',\'' + date + '\')', (err, rows, filds) => {
                    if (!err) {
                        console.log(filds);
                    } else {
                        console.log(err);
                    }
                });


                io.to(socket_ids[name]).emit('chat message', nickName + '으로부터 개인톡이 왔습니다.\n' + msg);
                socket.emit('mySaying', name + '에게 개인톡을 보냈습니다  :  ' + msg);
            }

        } else {
            if (whoIsOn.indexOf(name) == -1) {
                socket.emit('mySaying', '접속중인 유저가 아닙니다.');
            } else {
                var date = moment().format('YYYY-MM-DD HH:mm:ss');
                console.log(date);
                connection.query('INSERT INTO chat(USER, CONTENT,SIDEUSER,IP,DATE) VALUES(\'' + nickName + '\',\'' + msg + '\',\'' + name + '\',\'' + ipnum + '\',\'' + date + '\')', (err, rows, filds) => {
                    if (!err) {
                        console.log(filds);
                    } else {
                        console.log(err);
                    }
                });


                io.to(socket_ids[name]).emit('chat message', nickName + '으로부터 개인톡이 왔습니다.\n' + msg);
                socket.emit('mySaying', name + '에게 개인톡을 보냈습니다  :  ' + msg);
            }
        }
    });


    socket.on('missedmessage', function () {
        connection.query('SELECT pk, user, content FROM chat WHERE sideuser="broadcast" ORDER BY pk DESC LIMIT 50;', (err, rows, filds) => {
            if (!err) {


                for (let i = rows.length - 1; i >= 0; i--) {
                    var msg = rows[i].user + ' : ' + rows[i].content;
                    io.to(socket_ids[nickName]).emit('mySaying', msg);
                }
            } else console.log(err);
            io.emit('login', whoIsOn);


        });

        connection.query('DELETE FROM chatuser WHERE ip = ?', num, function (error, result) {
            if (error) {
                throw error;
            }
        });
    });

    socket.on('typing', function () {
        if (!whoIsTyping.includes(nickName)) {
            whoIsTyping.push(nickName);
            console.log('who is typing now');
            console.log(whoIsTyping);
            io.emit('typing', whoIsTyping);
        }
    });


    socket.on('quitTyping', function () {
        if (whoIsTyping.length == 0) {
            //if it's empty
            console.log('emit endTyping');
            io.emit('endTyping');
        } else {
            //if someone else is typing
            var index = whoIsTyping.indexOf(nickName);
            console.log(index);
            if (index != -1) {
                whoIsTyping.splice(index, 1);
                if (whoIsTyping.length == 0) {

                    console.log('emit endTyping');
                    io.emit('endTyping');
                } else {
                    io.emit('typing', whoIsTyping);
                    console.log('emit quitTyping');
                    console.log('whoIsTyping after quit');
                    console.log(whoIsTyping);
                }

            }


        }
    });


    //disconnect is in socket
    socket.on('disconnect', function () {
        console.log(nickName + ' : DISCONNECTED');

        whoIsOn.splice(whoIsOn.indexOf(nickName), 1);
        io.emit('logout', {nickNameArr: whoIsOn, disconnected: nickName});


        if (whoIsTyping.length == 0) {
            //if it's empty
            io.emit('endTyping');
        } else {
            //if someone was typing
            var index = whoIsTyping.indexOf(nickName);
            if (index != -1) {
                whoIsTyping.splice(index, 1);

                //if no one is typing now
                if (whoIsTyping.length == 0) {
                    io.emit('endTyping');
                }

                //if someone else is still typing
                else {
                    io.emit('typing', whoIsTyping);
                    console.log('emit popTyping');
                    console.log(whoIsTyping);
                }

            }


        }
    });

});

http.listen(3000, function () {
    console.log('listening on *:3000');
});



