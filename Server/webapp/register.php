<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);

$username = $_POST['username'];
$password = md5($_POST['password']);

$qr="SELECT username from user_info WHERE username='$username'";
$run=mysqli_query($con,$qr);
if(mysqli_num_rows($run)>0){
echo 'User Name Already Exists';
}else{
	$query = "INSERT INTO user_info VALUES('','$username','$password')";
$run = mysqli_query($con,$query);
echo "Registered Succesifully";
}
?>