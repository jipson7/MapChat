package ca.uoit.caleb.wildviper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by caleb on 2016-11-24.
 */

public class UserOverlay implements ProfileImageSetter {

    private final Float photoWidth = 100f;
    private String mPhotoUrl;
    private String mUsername;
    private LatLng mLatLng;

    private GroundOverlay mOverlayHandle;
    private ProfileImageFetcher mProfileImageFetcher;

    public UserOverlay(User user, GoogleMap map) {
        this.mPhotoUrl = user.photoUrl;
        this.mUsername = user.username;
        this.mLatLng = user.getLatLng();

        mProfileImageFetcher = new ProfileImageFetcher(this);

        dropOverlay(map);
    }

    private void dropOverlay(GoogleMap map) {
        //TODO Add click callback to user overlay to display user information
        GroundOverlayOptions userOverlayOptions = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.default_user_icon))
                .anchor(0, 1)
                .position(mLatLng, photoWidth);
        mOverlayHandle = map.addGroundOverlay(userOverlayOptions);
        if (mPhotoUrl != null) {
            mProfileImageFetcher.execute(mPhotoUrl);
        }
    }

    @Override
    public void setProfileImage(Bitmap bitmap) {
        Bitmap circleBitmap = cropCircleBitmap(bitmap);
        mOverlayHandle.setImage(BitmapDescriptorFactory.fromBitmap(circleBitmap));
    }

    public Bitmap cropCircleBitmap(Bitmap bitmap) {
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

    public void remove(){
        this.mOverlayHandle.remove();
    }
}
