<! DOCTYPE html>
<html lang="en">
<head>
<title>Hello Admin</title>
<meta charset="utf-8">
<style type="text/css">
body{
	background-color: grey;
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

#container{
	margin:auto;
	width: 950;
	height: 700px;
	background-color: #fff; 
	border: thick solid #cccccc;
	border-radius: 10px;
}
.imagetext{
	margin:auto;
	width: 210px;
	height: 230px;
	background-color: #fff; 
	border: thick solid #cccccc;
	border-radius: 10px;
	float: left;
	display: inline;
	margin-left:10px;
	margin-right: 5px;
	margin-top: 10px;
	margin-bottom: 10px; 
}
.imagetext img{
	padding: 1px;
	border: thick solid #cccccc;
	border-radius: 3px;
	margin-top:12px;
	margin-right: 12px;
	margin-left: 12px
}
.imagetext button{
	margin: 3px;
	border-radius: 50%;
	cursor: pointer;
	background-color: #33cc33;
	float: right;
}



</style>
</head>
<body> 
<div class="nav">
<ul>
  <?php
  include 'init.php';
  if(isset($_SESSION['officeNumber'])){
  	echo '<li>'.'<a href ="logout.php"> Logout</a>'.'</li>';
  }
  ?>
</ul>
  
  
</div>
	<div id="container">
		<?php
			if(isset($_GET['county'])&&isset($_SESSION['officeNumber'])){
			$county = $_GET['county'];

			$query  = "SELECT * FROM municiple_report WHERE county='$county'";
			$run = mysqli_query($con,$query);
			if($run){
				
				while($image = mysqli_fetch_assoc($run)){
					$photo_url =$image['photo_url'];
					echo '<div class="imagetext">';
						//echo '<div class="image"';
							echo "<img width='170px' height='170px'src='$photo_url'/>";
						//echo '</div>'; 

						echo '<div class="text">';
							echo "Description: ".$activity = $image['activity'];
						echo '</div>'; 

						echo '<div class="button">';
							echo "<td>"."<a href=ViewMap.php?photo_url=$image[photo_url]>"."<button>View Map</button>"."</a>"."</td>";
						echo '</div>'; 
					echo '</div>'; 					
				}
				

			}
	//$first_name = $_SESSION['first_name'];
		}
?>
	</div>
<!--Here goes the table-->
</body>
</html>


