<?php
/*Connect to the MySQL database that is holding the data will handle in here, replace the relevent data with your data
 with database name username and password*/
$host = "localhost";
$user = "root";
$pass = "";
$db   ="environment";

$con = mysqli_connect($host,$user,$pass,$db);

 //Initialize your first couple variables
 $encodedString = ""; //This is the string that will hold all your location data
 $x = 0; //This is a trigger to keep the string tidy
 
 //Now we do a simple query to the database
 $result = mysqli_query($con,"SELECT * FROM `nema_image`");

 while($row = mysqli_fetch_assoc($result)){
 
 //Multiple rows are returned {
 //This is to keep an empty first or last line from forming, when the string is split
 if ( $x == 0 ){
     $separator = "";
 } else{
     //Each row in the database is separated in the string by four *'s
     $separator = "****";
 }
 
 //Saving to the String, each variable is separated by three &amp;amp;'s
 //this is for the shows the details in the map
   $encodedString = $encodedString.$separator.
"
<p class='content'><b>Lat:</b> ".$row['latitude'].
"
<b>Long:</b> ".$row['longitude'].
"
<b>Name: </b>".$row[3].
"
<b>Address: </b>".$row[4].
"
<b>Division: </b>".$row[5].
"
 
&&&".$row[1]."&&&".$row[2];
$x = $x + 1;
 }
 ?>

 <html>
<head>
<script type='text/javascript' src='js/jquery-1.6.2.min.js'></script>
    <script type='text/javascript' src='js/jquery-ui.min.js'></script>
<style>
 
BODY {font-family : Verdana,Arial,Helvetica,sans-serif; color: #000000; font-size : 13px ; }
 
#map { width:100%; height: 100%; z-index: 0; }
</style>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY" /></script>
 
    <script type='text/javascript'>
  
       <!--java script code comes here-->

       jQuery(document).ready( function($){
 
    //Initialize the Google Maps
    var geocoder;
    var map;
    var markersArray = [];
    var infos = [];
 
    geocoder = new google.maps.Geocoder();
    var myOptions = {
        zoom: 9,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    //Load the Map into the map div
    var map = new google.maps.Map(document.getElementById("map"), myOptions);
    map = new google.maps.Map(document.getElementById("map"), myOptions);
 
    //Initialize a variable that the auto-size the map to whatever you are plotting
    var bounds = new google.maps.LatLngBounds();
    //Initialize the encoded string
    var encodedString;
    //Initialize the array that will hold the contents of the split string
    var stringArray = [];
    //Get the value of the encoded string from the hidden input
    encodedString = document.getElementById("encodedString").value;
    //Split the encoded string into an array the separates each location
    stringArray = encodedString.split("****");
 
    var x;
    for (x = 0; x &amp;lt; stringArray.length; x = x + 1){
         var addressDetails = [];
         var marker;
         //Separate each field
        addressDetails = stringArray[x].split("&&&");
       //Load the lat, long data
      var lat = new google.maps.LatLng(addressDetails[1], addressDetails[2]);
      //Create a new marker and info window
       marker = new google.maps.Marker({
           map: map,
           position: lat,
           //Content is what will show up in the info window
           content: addressDetails[0]
       });
//Pushing the markers into an array so that it's easier to manage them
markersArray.push(marker);
google.maps.event.addListener( marker, 'click', function () {
     closeInfos();
     var info = new google.maps.InfoWindow({content: this.content});
     //On click the map will load the info window
     info.open(map,this);
     infos[0]=info;
});
   //Extends the boundaries of the map to include this new location
   bounds.extend(lat);
}
//Takes all the lat, longs in the bounds variable and autosizes the map
map.fitBounds(bounds);
 
//Manages the info windows
function closeInfos(){
     if(infos.length > 0){
         infos[0].set("marker",null);
         infos[0].close();
         infos.length = 0;
     }
}
 
});
    </script>
 
</head>
<body>
<div id='input'>
 
<!--php code will come here-->
 
<input type="hidden" id="encodedString" name="encodedString" value="<?php echo $encodedString; ?>" />
</div>
<div id="map"></div>
</body>