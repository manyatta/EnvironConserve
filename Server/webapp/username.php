<?php
require "init.php";

$username = $_POST['username'];
$caption = $_POST['caption'];

//$username ="Manyatta";
//$photo_name = "1.jpg";

$update = "UPDATE reports set username='$username' WHERE caption='$caption'";
$run = mysqli_query($con,$update);
if($run){echo "Update Succesiful";}else{echo 'not updated';}



?>