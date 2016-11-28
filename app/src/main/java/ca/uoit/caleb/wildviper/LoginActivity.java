package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;


public class LoginActivity extends Activity {

    private static final int RC_SIGN_IN = 1234;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {
            //TODO Uncomment before submitting
            //launchMainActivity();
        }
    }

    public void launchSignInIntent(View view) {
        Intent i = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                )).setIsSmartLockEnabled(false)
                .build();
        startActivityForResult(i, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: {
                String username = mAuth.getCurrentUser().getDisplayName();
                String message = username + " signed in Successfully";
                showToast(message);
                launchMainActivity();

                break;
            }
            case RESULT_CANCELED: {
                showToast(getString(R.string.toast_sign_in_cancelled));
                break;
            }
            case ResultCodes.RESULT_NO_NETWORK: {
                showToast(getString(R.string.toast_no_internet));
                break;
            }
            default: {
                showToast(getString(R.string.toast_unexpected_error));
                break;
            }
        }
    }

    private void launchMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
