package ca.uoit.caleb.wildviper;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by caleb on 2016-11-24.
 */

public class UserOverlay implements ProfileImageSetter {
    private Uri mPhotoUrl;
    private String mUsername;
    private LatLng mLatLng;

    private GroundOverlay mOverlayHandle;
    private ProfileImageFetcher mProfileImageFetcher;

    public UserOverlay(Uri photoUrl, String username, LatLng latLng, GoogleMap map) {
        this.mPhotoUrl = photoUrl;
        this.mUsername = username;
        this.mLatLng = latLng;

        mProfileImageFetcher = new ProfileImageFetcher(this);

        dropOverlay(map);
    }

    private void dropOverlay(GoogleMap map) {
        GroundOverlayOptions userOverlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.default_user_icon))
                .anchor(0, 1)
                .position(mLatLng, 100f);
        mOverlayHandle = map.addGroundOverlay(userOverlayOptions);
        if (mPhotoUrl != null) {
            mProfileImageFetcher.execute(mPhotoUrl);
        }
    }

    @Override
    public void setProfileImage(Bitmap bitmap) {
        mOverlayHandle.setImage(BitmapDescriptorFactory.fromBitmap(bitmap));
    }
}
