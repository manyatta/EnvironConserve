<?php
session_start();
if(isset($_SESSION['officeNumber'])){
	session_destroy();
	header('Location: adminLoginH.php' );
}
?>