var map;

function handleViewChange(viewName) {
    if (viewName == 'map') {
        if (map == null) {
            initializeMap();
        }
    }
}

function setupMap(mapZoom, showOverviewControl) {
    var coord = document.getElementById("map:mapForm:coordstr").value;
    console.log(coord);

//    $('#gmap').gmap3({
//        map: {
//            options: {
//                maxZoom: mapZoom
//            }
//        },
//        marker: {
//            address: coord,
//            options: {
//                icon: new google.maps.MarkerImage(
//                        new google.maps.Size(32, 37, "px", "px")
//                        )
//            }
//        }
//    },
//    "autofit");
    var re = /\s*,\s*/,
            mapCoord = coord.split(re);
    if (!mapCoord)
        return;
    var mapLatlng = new google.maps.LatLng(mapCoord[0], mapCoord[1]);
    var myOptions = {
        zoom: mapZoom,
        center: mapLatlng,
        overviewMapControl: showOverviewControl,
        zoomControl: true,
        zoomControlOptions: {
            style: google.maps.ZoomControlStyle.DEFAULT,
            position: google.maps.ControlPosition.LEFT_TOP

        },
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map:mapForm:gmap"),
            myOptions);
    var marker = new google.maps.Marker({
        position: mapLatlng,
        map: map,
        title: "Это здесь!"
    });
    marker.setMap(map);
    map.setCenter(mapLatlng);
    enableHighAccuracy: true;
    google.maps.event.trigger(map, 'resize');
}

function initializeMap() {
    setupMap(15, true);
}

function resetMap() {
    map = null;
}

function refreshMap() {
    if (map != null) {
        google.maps.event.trigger(map, 'resize');
        //$('map:mapForm:gmap').gmap3('refresh');
    }
}

function refreshPage() {
  $.mobile.changePage(
    window.location.href,
    {
      allowSamePageTransition : true,
      transition              : 'none',
      showLoadMsg             : false,
      reloadPage              : true
    }
  );
}



