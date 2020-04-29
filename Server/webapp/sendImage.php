<?php
 
require_once 'dbConnection.php';
 
global $connection;
$upload_path = 'uploads/'; //this is our upload folder
$server_ip = "localhost";//Getting the server ip
$upload_url = 'http://'.$server_ip.'/webapp/'.$upload_path; //upload url
 
//response array
$response = array();
 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    
    //checking the required parameters from the request
    if(isset($_POST['activity']) and isset($_FILES['image']['name']))
    {
        $s = substr(str_shuffle(str_repeat("01123456789abcdefghijklmnopqrstuvwxyz", 5)),0,5);
        $activity = $_POST['activity'];
        $county = $_POST['county'];
        $latitude = $_POST['latitude'];
        $longitude = $_POST['longitude'];
        $fileinfo = pathinfo($_FILES['image']['name']);//getting file info from the request
        $extension = $fileinfo['extension']; //getting the file extension
        $file_url = $upload_url . getFileName() . '.' . $extension; //file url to store in the database
        $file_path = $upload_path . getFileName() . '.'. $extension; //file path to upload in the server
        $img_name = getFileName() . '.'. $extension; //file name to store in the database
        
        $query = "SELECT * FROM nema_image WHERE activity='$activity'";
        $run = mysqli_query($connection,$query);
        if(mysqli_num_rows($run)<1){
        try{
            move_uploaded_file($_FILES['image']['tmp_name'],$file_path); //saving the file to the uploads folder;
           
            //adding the path and name to database
            $sql = "INSERT INTO nema_image(photo_name, photo_url, activity, county, latitude, longitude) ";
            $sql .= "VALUES ('{$img_name}', '{$file_url}', '{$activity}', '{$county}','{$latitude}','{$longitude}');";
             
            if(mysqli_query($connection,$sql)){
                //filling response array with values
                $response['error'] = false;
                $response['photo_name'] = $img_name;
                $response['photo_url'] = $file_url;
                $response['activity'] = $activity;
                $response['county'] = $county;
                $response['latitude'] = $longitude;
                $response['longitude'] = $longitude;

            }
            //if some error occurred
        }catch(Exception $e){
            $response['error']=true;
            $response['message']=$e->getMessage();
        }
    }else{ echo 'image already uploaded';}
        //displaying the response
        echo json_encode($response);
 
        //closing the connection
        mysqli_close($connection);
    }else{
        $response['error'] = true;
        $response['message']='Please choose a file';
    }
}
 
/*
We are generating the file name
so this method will return a file name for the image to be uploaded
*/
function getFileName(){
    global $connection;
     
    $sql = "SELECT max(report_id) as report_id FROM nema_image";
    $result = mysqli_fetch_array(mysqli_query($connection, $sql));
 
    if($result['report_id']== null)
        return 1;
    else
        return ++$result['report_id'];
     
    mysqli_close($connection);
}
?>