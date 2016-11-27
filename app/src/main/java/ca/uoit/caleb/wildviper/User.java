package ca.uoit.caleb.wildviper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by caleb on 2016-11-25.
 */

@IgnoreExtraProperties
public class User implements ProfileImageSetter {

    public String id;
    public String username;
    public String photoUrl;
    public double latitude;
    public double longitude;

    private final Float photoWidth = 100f;
    private GroundOverlay mOverlayHandle;

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
    public void dropOverlay(GoogleMap map) {
        //TODO Add click callback to user overlay to display user information

        String loggedInId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (loggedInId.equals(id)) {
            //Don't drop overlay for current user, just other users.
            return;
        }

        ProfileImageFetcher profileImageFetcher = new ProfileImageFetcher(this);

        GroundOverlayOptions userOverlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.default_user_icon))
                .anchor(0, 1)
                .position(getLatLng(), photoWidth);
        mOverlayHandle = map.addGroundOverlay(userOverlayOptions);
        if (photoUrl != null) {
            profileImageFetcher.execute(photoUrl);
        }
    }

    @Exclude
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Exclude
    @Override
    public void setProfileImage(Bitmap bitmap) {
        Bitmap circleBitmap = cropCircleBitmap(bitmap);
        mOverlayHandle.setImage(BitmapDescriptorFactory.fromBitmap(circleBitmap));
    }

    private Bitmap cropCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Exclude
    public void remove() {
        this.mOverlayHandle.remove();
    }
}
