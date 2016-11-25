package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by caleb on 2016-11-23.
 */

@IgnoreExtraProperties
public class Message {
    public String username;
    public String photoUrl;
    public String message;
    public double latitude;
    public double longitude;


    private Marker mMarkerHandle;

    public Message() {}

    public Message(String username, String photoUrl, String message, Double latitude, Double longitude) {
        this.message = message;
        this.photoUrl = photoUrl;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Exclude
    public void dropMarker(GoogleMap map, Context context) {
        MarkerOptions options = new MarkerOptions().position(getLatLng()).title(username).snippet(message);
        this.mMarkerHandle = map.addMarker(options);
        this.mMarkerHandle.showInfoWindow();
        if (photoUrl != null) {
            displayPhoto(context);
        }
    }

    private void displayPhoto(Context context) {
        Picasso.with(context)
                .load(photoUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    }
                    public void onBitmapFailed(Drawable errorDrawable) {
                        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.default_message_icon));
                    }
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.default_message_icon));
                    }
                });
    }

    private LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
