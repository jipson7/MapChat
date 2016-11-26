package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WriteMessageActivity extends Activity {

    private FirebaseDBHelper mFirebaseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        setTitle(R.string.write_message_window_title);
        mFirebaseDBHelper = new FirebaseDBHelper();
    }

    public void done(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText messageView = (EditText) findViewById(R.id.write_message_txt);
        String messageText = messageView.getText().toString();
        Intent intent = getIntent();

        Uri photoUrl = user.getPhotoUrl();

        Message message = new Message(
                user.getUid(),
                user.getDisplayName(),
                (photoUrl != null) ? photoUrl.toString() : null,
                messageText,
                intent.getDoubleExtra("latitude", 0),
                intent.getDoubleExtra("longitude", 0));

        mFirebaseDBHelper.saveMessage(message);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
