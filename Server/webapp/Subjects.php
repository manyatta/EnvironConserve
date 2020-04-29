<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";



// Create connection
$conn = new mysqli($host,$user,$pass,$db);

if ($conn->connect_error) {
	
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT county as subjects, count(1) as number FROM nema_image GROUP BY county";

$result = $conn->query($sql);

if ($result->num_rows >0) {
   
   
    while($row[] = $result->fetch_assoc()) {
		
		$tem = $row;
		
	   $json = json_encode($tem);
	  
	   
    }
	
} else {
    echo "No Results Found.";
}
 echo $json;
$conn->close();
?>