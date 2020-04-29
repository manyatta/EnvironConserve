<?php
header("Content-type: bitmap; charset=utf-8");
if(isset($_POST["encoded_string"])){
	$encoded_string = $_POST["encoded_string"];
	$name 	= $_POST["name"];

	$decoded_string = base64_decode($encoded_string);

	$path 			= 'images/'.$name;

	$file 			= fopen($path, 'wb');

	$is_written 	= fwrite($file, $decoded_string);

	fclose($file);


	if($is_written>0){
		$connection = mysqli_connect('localhost','root','','environment');
		$query 		= "INSERT INTO reports values('','$name','$path')";
		$result 	= mysqli_query($connection,$query);

		if($result){
			echo 'success';
		}else{
			echo 'Failed';
		}
		mysqli_close($connection);
	}
}else{echo 'not set';}
?>