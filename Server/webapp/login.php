<?php

$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);


$username = $_POST['username'];
$password = md5($_POST['password']);

$query = "SELECT * FROM user_info WHERE username='$username' AND password='$password'";

$run = mysqli_query($con,$query);

if(mysqli_num_rows($run)>0){
    echo 'Login Successfull';
}else{
	echo 'Incorrect Username or Password';
}

?>