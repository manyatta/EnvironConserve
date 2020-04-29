<?php
require "init.php";

$username 	= $_POST['username'];
$password 	= md5($_POST['password']);
$cpassword 	= md5($_POST['cpassword']);

if($password==$cpassword){

	$query 	= "SELECT * FROM user_info WHERE username='$username'";
	$run 	= mysqli_query($con,$query);

		if(mysqli_num_rows($run)==1){
			$update = "UPDATE user_info set password='$password' WHERE username='$username'";
			$run 	= mysqli_query($con,$update);
				if($run){
					echo "Password has been succesifully changed";
				}else{
					echo 'An error occured while Resetting';
				}
		}else{
			echo 'A user with that username does not exist';
		}


}else{
	echo 'The passwords do not match...!';
}

?>