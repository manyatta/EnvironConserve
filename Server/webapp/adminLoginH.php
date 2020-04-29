<! doctype html>
<html>
<head>
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

.aa{
  width: 300px;
  height: 260px;
  background-color: rgba(0,0,0,0.5);
  margin: 0 auto;
  margin-top: 40px;
  padding-top: 10px;
  padding-left: 50px;
  -webkit-border-color: 15px;
  -moz-border-color: 15px;
  -ms-border-color: 15px;
  -o-border-color: 15px;
  border-color: 15px;
  color:white;
  font-weight: bolder;
  -webkit-box-shadow: inset -4px -4px rgba(0,0,0,0.5);
  box-shadow: inset -4px -4px rgba(0,0,0,0.5);
  font-size: 18px;
 }
 .aa input[type="text"]{
  width: 200px;
  height: 35px;
  border:0px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  -ms-border-radius: 5px;
  -o-border-radius: 5px;
  border-radius: 5px;
  padding-left: 5px;
 }
 .aa input[type="password"]{
  width: 200px;
  height: 35px;
  border:0px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  -ms-border-radius: 5px;
  -o-border-radius: 5px;
  border-radius: 5px;
  padding-left: 5px;
 }
 .aa input[type="submit"]{
  width: 200px;
  height: 35px;
  border:0px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  -ms-border-radius: 5px;
  -o-border-radius: 5px;
  border-radius: 5px;
  background-color: skyblue;
  font-weight: bolder;
 }
</style>
</head>
<body> 
<div class="nav">
	<ol>
  	<li><h1>ENVIRON CONSERVE</h1></li>
	</ol>
</div>
<div id="container">
	

	<?php
	include "init.php";

	if(isset($_POST['officeNumber'])&&isset($_POST['password'])){
		$officeNumber   = $_POST['officeNumber'];
		$password       = md5($_POST['password']);
	if(!empty($officeNumber)&&!empty($password)){
		$query = "SELECT * FROM admin WHERE officeNumber='$officeNumber' AND password='$password'";
		$run_query = mysqli_query($con,$query);
		if(mysqli_num_rows($run_query)==1){
		while($row = mysqli_fetch_assoc($run_query)){
			echo $row['officeNumber'];
			echo $row['admin_id'];
			$_SESSION['officeNumber']= $officeNumber;
			
			header('Location: dashBoard.php' );
		}
	}else{echo '<h4 style=color:red>User Not Found!</h4>';}
	}else{
		echo '<h4 style=color:red>Fill in all the fields!</h4>';
	}	
	}
	?>

	<div class="aa">
	<h2>Login</h2>	
		<form action="adminLoginH.php" method="POST">
			<div class="inputBox">
		   		<input type="text" placeholder="Office Number" name="officeNumber" value="<?php if(isset($_POST['officeNumber'])){echo $officeNumber;} ?>" required="">
		   	</div>

		   	<div class="inputBox">
		   		<input type="password" placeholder="Password" name="password" required="">
			</div>

		   	<input type="submit" value="Login">
		</form>	
	</div>	
	        
	<script src="js/jqeury.js"></script>
	<script src="js/bootstrap.js"></script>
</div>
</body>

</html>
