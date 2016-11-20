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
 * Created by caleb on 2016-11-19.
 */

public class MessageMarker {

    private String mMessage;
    private LatLng mLatLng;
    private String mUsername;
    private Marker mMarkerHandle;

    public MessageMarker(Context context, GoogleMap map, LatLng latLng, String message, FirebaseUser user) {
        this.mMessage = message;
        this.mUsername = user.getDisplayName();
        this.mLatLng = latLng;
        this.mMarkerHandle = map.addMarker(new MarkerOptions().position(latLng).title(message));
        this.mMarkerHandle.showInfoWindow();
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null) {
            fetchUserPhoto(user.getPhotoUrl(), context);
        }
    }

    private void fetchUserPhoto(Uri photoUrl, Context context) {
        Picasso.with(context)
                .load(photoUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mMarkerHandle.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    public void remove(){
        this.mMarkerHandle.remove();
    }

}
