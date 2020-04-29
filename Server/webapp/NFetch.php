<?php

$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);

$query="SELECT photo_name,activity,county,latitude,longitude,photo_url FROM nema_image";
$run =mysqli_query($con,$query);

if($run)
{
	while ($row=mysqli_fetch_array($run)) {
		$flag[]=$row;
		}
		print(json_encode($flag));
}
mysqli_close($con);
?>
