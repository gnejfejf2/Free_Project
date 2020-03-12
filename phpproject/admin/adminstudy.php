<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>column chart</title>
</head>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<body>
<!-- chart가 생성될 공간 -->
<div id="bar_chart_div"></div>


<button onclick="adddata()">테스트</button>
</body>

<script type="text/javascript">
    google.charts.load('current', {'packages':['bar','corechart']});

    adddata();
    function adddata() {
        var data = google.visualization.arrayToDataTable([
            ["Title","성공", {role:'annotation'}, "실패", {role:'annotation'}],
            [""
                ,413, 413 //성공데이터
                ,24, 24] //실패데이터
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


</script>
</html>

