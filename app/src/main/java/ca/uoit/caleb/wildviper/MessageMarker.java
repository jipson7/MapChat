package ca.uoit.caleb.wildviper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by caleb on 2016-11-19.
 */

public class MessageMarker {

    private String mMessage;
    private LatLng mLatLng;
    private String mUsername;
    private Marker mMarkerHandle;

    public MessageMarker(GoogleMap map, LatLng latLng, String username, String message) {
        this.mMessage = message;
        this.mUsername = username;
        this.mLatLng = latLng;
        this.mMarkerHandle = map.addMarker(new MarkerOptions().position(latLng).title(message));
        this.mMarkerHandle.showInfoWindow();
    }

    public void deleteMarker(){
        this.mMarkerHandle.remove();
    }

}
