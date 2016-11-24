package ca.uoit.caleb.wildviper;

import android.net.Uri;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by caleb on 2016-11-24.
 */

public class UserOverlay {
    private Uri mPhotoUrl;
    private String mUsername;
    private LatLng mLatLng;

    private GroundOverlay mOverlayHandle;

    public UserOverlay(Uri photoUrl, String username, LatLng latLng, GoogleMap map) {
        this.mPhotoUrl = photoUrl;
        this.mUsername = username;
        this.mLatLng = latLng;

        dropOverlay(map);
    }

    private void dropOverlay(GoogleMap map) {

    }
}
