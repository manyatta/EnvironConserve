function loadMap() {
	var pune = {lat: 18.5204, lng: 73.8567};
    map = new google.maps.Map(document.getElementById('map'), {
      zoom: 12,
      center: pune
    });
}