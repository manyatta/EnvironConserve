<?php
$dbhandle = new mysqli('localhost','root','','environment');
echo $dbhandle->connect_error;

$qr = "SELECT county,count(1) as number FROM nema_image GROUP BY county";
$res = $dbhandle->query($qr);

?>
<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['County', 'Number of Reports'],
          <?php
         while($row=$res->fetch_assoc()){
          echo "['".$row['county']."',".$row['number']."],";
         }
          ?>
          
        ]);

        var options = {
          title: 'County Reports'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
  </body>
</html>