package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by caleb on 2016-11-23.
 */

public class Message {
    private String mMessage;
    private String mPhotoUrl;
    private String mUsername;
    private double latitude;
    private double longitude;


    private Marker mMarkerHandle;

    public Message(String username, String photoUrl, String message, Double latitude, Double longitude) {
        this.mMessage = message;
        this.mPhotoUrl = photoUrl;
        this.mUsername = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void dropMarker(GoogleMap map) {
        String message = mUsername + ": " + mMessage;
        this.mMarkerHandle = map.addMarker(new MarkerOptions().position(getLatLng()).title(message));
        this.mMarkerHandle.showInfoWindow();
    }

    public void displayPhoto(Context context) {
        if (mPhotoUrl == null || mMarkerHandle == null) {
            return;
        }
        Picasso.with(context)
                .load(mPhotoUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    }
                    public void onBitmapFailed(Drawable errorDrawable) {}
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
    }

    private LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
