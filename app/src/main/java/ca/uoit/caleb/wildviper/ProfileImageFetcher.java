package ca.uoit.caleb.wildviper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by caleb on 2016-11-24.
 */

public class ProfileImageFetcher extends AsyncTask {

    private ProfileImageSetter mProfileImageSetter;

    private Exception e;
    private Bitmap mCachedBitmap;

    public ProfileImageFetcher(ProfileImageSetter profileImageSetter) {
        mProfileImageSetter = profileImageSetter;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (mCachedBitmap != null) {
            return mCachedBitmap;
        }
        Bitmap profilePhoto = null;
        String photoUrlString = (String) objects[0];
        try {
            URL photoUrl = new URL(photoUrlString);
            HttpURLConnection connection = (HttpURLConnection) photoUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            profilePhoto = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            this.e = e;
        } catch (IOException e) {
            this.e = e;
        }

        return profilePhoto;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (e != null) {
            e.printStackTrace();
            return;
        }
        if (o != null) {
            Bitmap profilePhoto = (Bitmap) o;
            mCachedBitmap = profilePhoto;
            mProfileImageSetter.setProfileImage(profilePhoto);
        }
    }

}
