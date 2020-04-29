<?php
require "init.php";

$activity = $_POST['activity'];
$userName = $_POST['userName'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

$query = "INSERT INTO kfm_map VALUES('','$activity','$userName','$latitude','$longitude')";
$run = mysqli_query($con,$query);
if($run){
	echo "Success";
}else{
	echo 'failed';
}
?>