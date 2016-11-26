package ca.uoit.caleb.wildviper;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by caleb on 2016-11-25.
 */

@IgnoreExtraProperties
public class User {

    public String id;
    public String username;
    public String photoUrl;
    public double latitude;
    public double longitude;

    public User() {}

    public User(FirebaseUser user, LatLng latLng) {
        this.id = user.getUid();
        this.username = user.getDisplayName();
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        Uri photoUri = user.getPhotoUrl();
        if (photoUri != null) {
            this.photoUrl = photoUri.toString();
        } else {
            this.photoUrl = null;
        }
    }

    @Exclude
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
