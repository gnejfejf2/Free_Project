<?php
include("../dbconn.php");
$today = date("m");
$options = array(
    'title' => $today,
    'width' => 400, 'height' => 500
);
?>
<script src="//www.google.com/jsapi"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script>





    var options = <?= json_encode($options) ?>;
    google.load('visualization', '1.0', {'packages': ['corechart']});

</script>
<script>
    $(document).ready(function () {



        $('#selectmonth').on('change', function () {
            if (this.value !== "") {
                var optVal = $(this).find(":selected").val();

                //var year = optVal.substr(0,4); // 년
                //var month = optVal.substr(4,2); // 월

                $.post('../admin/chartajax.php', {optVal: optVal}, function (msg) {

                    var jsonObj = $.parseJSON(msg); // JSON 문자열을 JavaScript object 로 반환

                    var data3 = jsonObj.dat; // 문자열을 배열로 변환
                    alert(data3);

                    var chart = new google.visualization.ColumnChart(document.querySelector('#chart_div2'));
                    chart.draw(google.visualization.arrayToDataTable(data3), options);


                });



            }
        });

    });

</script>
<div id="chart_div"></div>
<div id="chart_div2"></div>
<button id="test" onclick="test()">테스트</button>
<select name="month" id="selectmonth">
    <option value="">년월 선택</option>
    <?php
    $optionquery = "select date_format(date,'%Y-%m-%d') m,count(*) from membervisit where date like '2020-$today-%' group by m;";
    $optionresult = mysqli_query($conn, $optionquery);

    while ($optionrows = mysqli_fetch_assoc($optionresult)) {
        echo "<option value='" . $optionrows['m'] . "' ";
        echo ">" . $optionrows['m'] . "</option>\n";
    }
    ?>
</select>