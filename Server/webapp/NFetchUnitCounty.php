<?php

$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";


//"laikipia";//

$con = mysqli_connect($host,$user,$pass,$db);
$county = "laikipia";//$_POST['county'];

$query="SELECT photo_name,activity,county,latitude,longitude,photo_url FROM nema_image WHERE county = '$county'";
$run =mysqli_query($con,$query);

if($run)
{
	while ($row=mysqli_fetch_array($run)) {
		//echo $county = $row['county'];
		$flag[]=$row;
		}
		print(json_encode($flag));
}
mysqli_close($con);

?>	 

