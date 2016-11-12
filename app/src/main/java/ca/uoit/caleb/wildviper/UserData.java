package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by caleb on 2016-11-12.
 */

public class UserData {

    //Preferences Name
    private static final String USER_PREFERENCES = "USER_PREFERENCES";

    //Context
    private static Context CONTEXT = null;

    /**
     * Keys used to store and retrieve user data
     */
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PHOTO_URL = "photo_url";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String FULL_NAME = "full_name";

    /**
     * Save Data to Shared preferences
     * @param acct
     * @param context
     */
    public static void saveData(GoogleSignInAccount acct, Context context) {
        CONTEXT = context;
        SharedPreferences.Editor editor
                = CONTEXT.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(ID, acct.getId());
        editor.putString(EMAIL, acct.getEmail());
        Uri url = acct.getPhotoUrl();
        if (url != null) {
            editor.putString(PHOTO_URL, url.toString());
        }
        editor.putString(FIRST_NAME, acct.getGivenName());
        editor.putString(LAST_NAME, acct.getFamilyName());
        editor.putString(FULL_NAME, acct.getDisplayName());
        editor.commit();
    }

    public static String getID() {
        return getPreferences().getString(ID, null);
    }

    public static String getEmail() {
        return getPreferences().getString(EMAIL, null);
    }

    public static String getPhotoUrl() {
        return getPreferences().getString(PHOTO_URL, null);
    }

    public static String getFirstName() {
        return getPreferences().getString(FIRST_NAME, null);
    }

    public static String getLastName() {
        return getPreferences().getString(LAST_NAME, null);
    }

    public static String getFullName() {
        return getPreferences().getString(FULL_NAME, null);
    }

    private static SharedPreferences getPreferences() {
        return CONTEXT.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
