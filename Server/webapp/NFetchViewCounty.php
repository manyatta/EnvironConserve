<?php

$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);

$qr = "SELECT county,count(1) as number FROM nema_image GROUP BY county";
$run = mysqli_query($con,$qr);
if($run){
	while($row = mysqli_fetch_assoc($run)){
		$flag[]=$row;
	}
	print(json_encode($flag));
}else{
	echo 'no';
}

?>	 