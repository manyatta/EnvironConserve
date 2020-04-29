<! DOCTYPE html>
<html lang="en">
<head>
<title>Hello Admin</title>
<meta charset="utf-8">
<style type="text/css">
body{
	background-color: grey;
}
ol{
  margin:0 auto;
  float: left;
  background: linear-gradient(#006400,#228b22);
  background: -moz-linear-gradient(#006400,#228b22);
  background: -webkit-linear-gradient(#006400,#228b22);
  width:100%;
  height: 100px;
}
ol li {
  list-style: none;
  margin: 0;
  float:left;
  display: inline;
  position: relative;

}
#container{
	margin:auto;
	width: 1000;
	height: 700px;
	background-color: #fff; 
	border: thick solid #cccccc;
	border-radius: 10px;
}
.nav{
  width:97%;
  margin; auto;
  font-size: 10px;
  font-family: Helvetica;
}
ul{
  margin:0 auto;
  float: left;
  background: linear-gradient(#006400,#228b22);
  background: -moz-linear-gradient(#006400,#228b22);
  background: -webkit-linear-gradient(#006400,#228b22);
  width:100%;
  height: 40px;
}
ul li {
  list-style: none;
  margin: 0;
  float:left;
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
	<ol>
  	<li><h1>ENVIRON CONSERVE</h1></li>
	</ol>
</div>
<div class="nav">
	<ul>
  		<?php
  		include 'init.php';
  		if(isset($_SESSION['officeNumber'])){
  			echo '<li >'.'<a href ="logout.php"> Logout</a>'.'</li>';
  			}
  		?>
  	<li><a href ="NAdminViewCount.php"> NEMA</a></li>
  	<li><a href ="MAdminViewCount.php"> MUNICIPLE</a></li>
  	<li><a href ="KAdminViewCount.php"> KFS</a></li>
	</ul>
	
  	
	
</div>
<div id="container">


</div>
</body>
</html>
