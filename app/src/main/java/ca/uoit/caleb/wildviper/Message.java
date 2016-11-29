package ca.uoit.caleb.wildviper;


import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by caleb on 2016-11-23.
 */

@IgnoreExtraProperties
public class Message implements ProfileImageSetter {
    public String userid;
    public String username;
    public String photoUrl;
    public String message;
    public double latitude;
    public double longitude;

    private Marker mMarkerHandle;

    public Message() {}

    public Message(String userid, String username, String photoUrl, String message, Double latitude, Double longitude) {
        this.userid = userid;
        this.message = message;
        this.photoUrl = photoUrl;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public void dropMarker(GoogleMap map) {
        MarkerOptions options = new MarkerOptions().position(getLatLng()).title(username).snippet(message);
        mMarkerHandle = map.addMarker(options);
        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.default_message_icon));
        if (photoUrl != null) {
            ProfileImageFetcher mProfileImageFetcher = new ProfileImageFetcher(this);
            mProfileImageFetcher.execute(photoUrl);
        } else {
            mMarkerHandle.showInfoWindow();
        }
    }

    @Exclude
    public Marker getMarker() {
        return mMarkerHandle;
    }

    private LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Exclude
    @Override
    public void setProfileImage(Bitmap bitmap) {
        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        mMarkerHandle.showInfoWindow();
    }

    @Exclude
    public void remove(){
        this.mMarkerHandle.remove();
    }
}
