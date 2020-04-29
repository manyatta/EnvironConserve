<?php
include "init.php";
if(isset($_SESSION['officeNumber'])){
$dbhandle = new mysqli('localhost','root','','environment');
echo $dbhandle->connect_error;

$qr = "SELECT county,count(1) as number FROM kfs_report GROUP BY county";
$res = $dbhandle->query($qr);
}
?>


<! DOCTYPE html>
<html lang="en">
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
<title>Hello Admin</title>
<meta charset="utf-8">
<style type="text/css">
body{
	background-color: grey;
}
#container{
	margin:auto;
	width: 1000;
	height: 700px;
	background-color: #fff; 
	border: thick solid #cccccc;
	border-radius: 10px;
}
.chart{
	width: 425px; 
	height: 500px;
	float: left;
	border: thin;
	margin: 10px;
	background-color: #ccffc1;
	border-radius: 10px;
	margin-left: 50px;
	margin-right: 25px;
	margin-top: 50px;
}
.table{
	width: 425px; 
	height: 500px;
	float: left;
	border: thin;
	margin: 10px;
	background-color: #ccffc1;
	border-radius: 10px;
	margin-left: 25px;
	margin-right: 50px;
	margin-top: 50px;
}
.tablee{
	margin-left: 60px;
}
.tablee a button{
	padding-left: 5px;
	padding-right: 5px;
	font-size: 16px;
}

.nav{
  width:97%;
  margin; auto;
  font-size: 15px;
  font-family: Helvetica;
}
ul{
  margin:0 auto;
  float: left;
  background: linear-gradient(#006400,#228b22);
  background: -moz-linear-gradient(#006400,#228b22);
  background: -webkit-linear-gradient(#006400,#228b22);
  width:100%;
  height: 100px;
}
ul li {
  list-style: none;
  margin: 0;
  float:right;
  display: inline;
  position: relative;

}
ul li a {
  border-right: 1px dotted #fff;
  border-radius: 0 30px 0 30px;
  padding:13px 25px;
  color:#fff;
  text-decoration: none;
  display: inline-block;
  background: linear-gradient(#006400,#228b22);
  background: -moz-linear-gradient(#006400,#228b22);
  background: -webkit-linear-gradient(#006400,#228b22);
}
ul li a:hover {
  

  background: linear-gradient(#b22222, #8b0000);
  background: -moz-linear-gradient(#b22222, #8b0000);
  background: -webkit-linear-gradient(#b22222, #8b0000);
}


</style>
</head>




<body> 
<div class="nav">
<ul>
  <?php
  if(isset($_SESSION['officeNumber'])){
  	echo '<li>'.'<a href ="logout.php"> Logout</a>'.'</li>';
  }
  ?>
</ul>
  
  
</div>
	<div id="container">
		<div id="piechart" class="chart"><!--Here goes the pie chart--></div> 

		<!--Here goes the table-->
		<div class="table">
			<?php
			//include "init.php";
			if(isset($_SESSION['officeNumber'])){
				$officeNumber = $_SESSION['officeNumber'];
				$_SESSION['officeNumber']=$officeNumber;
			$qr = "SELECT name FROM admin";
			$run = mysqli_query($con,$qr);
			if($run){
				while($row = mysqli_fetch_assoc($run)){
				$name = $row['name'];
				echo '<h2 style=color:green>Welcome'.'  '.$name.'</h2>';
				}
				}
			$host = "localhost";
			$user = "root";
			$pass = "";
			$db   ="environment";

			$con = mysqli_connect($host,$user,$pass,$db);

			$qr = "SELECT county,count(1) as number FROM kfs_report GROUP BY county";
			$run = mysqli_query($con,$qr);

			if(mysqli_num_rows($run)>0){
			echo '<div class="tablee">';
			if($run){
				echo "<table border = '2'style='width:80%'>
				  <caption><h4 style=color:red>Reported Cases</h4></caption>
			  		<tr>
			    		<th>County</th>
			    		<th>Number of Cases</th>
			    		<th>Number of Cases</th>
			    		<th>Number of Cases</th>  
			    		
			  		</tr>";
				while($row = mysqli_fetch_assoc($run)){
				echo '<tr>';
				//$county = $row['county'];
			    	echo "<td>".$row['county']."</td>";
			    	echo '<td>'.$row['number'].'</td>';
			    	echo "<td>"."<a href=KViewCounty.php?county=$row[county]>"."<button>View</button>"."</a>"."</td>";
			    	echo "<td>"."<a href=delete.php?county=$row[county]>"."<button>Delete</button>"."</a>"."</td>";

			    	//echo '<p>'."<a href='files_student.php?unit=$unit'>$unit</a><br />".'</p>';

			    echo '</tr>';
				}
				echo "</table>";
			}
		}else{
			echo '<h4 style=color:red>No Reports Yet!</h4>';
		}

			echo '</div>';

			}
			?>
		</div>
	</div>
<!--Here goes the table-->
</body>
</html>


