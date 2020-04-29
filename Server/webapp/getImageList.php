<?php
require "init.php";

$query = "SELECT * FROM nema_image";
$run = mysqli_query($con,$query);
if($run){
	while($row = mysqli_fetch_array($run)){
		$flag[]=$row;
		}
		print (json_encode($flag));
}
mysqli_close($con);
?>