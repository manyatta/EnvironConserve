<?php

require 'init.php';


	$lquery ="SELECT * FROM nema_map WHERE report_id = 1";

	$result =mysqli_query($con,$lquery); //executes query

	if (mysqli_num_rows($result)){

		while($row = mysqli_fetch_array($result)) {

			$data[] = $row;

		}

		print json_encode($data);

	}else{

		echo "All reports resolved";
	}
?>
