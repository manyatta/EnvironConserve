  
<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);


	$officeNumber   = $_POST['officeNumber'];
	$password       = $_POST['password'];

	$query = "SELECT * FROM admin WHERE officeNumber='$officeNumber' AND password='$password'";
	$run_query = mysqli_query($con,$query);
	if(mysqli_num_rows($run_query)==1){
	echo 'Login Succesiful';
}else{echo '<h4 style=color:red>User Not Found!</h4>';}
	

?>

	
                
