package ca.uoit.caleb.wildviper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class LoginActivity extends FragmentActivity {

    private static final int RC_SIGN_IN = 1234;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        mUser = auth.getCurrentUser();

        if (auth.getCurrentUser() != null) {
            launchMainActivity();
        }
    }

    public void launchSignInIntent(View view) {
        Intent i = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                .setIsSmartLockEnabled(false)
                .build();
        startActivityForResult(i, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: {
                launchMainActivity();
                break;
            }
            case RESULT_CANCELED: {
                //showSnackbar(R.string.sign_in_cancelled);
                break;
            }
            case ResultCodes.RESULT_NO_NETWORK: {
                //showSnackbar(R.string.no_internet_connection);
                break;
            }
            default: {
                //showSnackbar(R.string.unexpected_error);
                break;
            }
        }
    }

    private void launchMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
