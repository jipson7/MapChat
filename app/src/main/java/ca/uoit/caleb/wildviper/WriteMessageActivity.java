package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WriteMessageActivity extends Activity {

    private MessageDBHelper mMessageDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        setTitle(R.string.write_message_window_title);
        mMessageDBHelper = new MessageDBHelper();
    }

    public void done(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText messageView = (EditText) findViewById(R.id.write_message_txt);
        String messageText = messageView.getText().toString();
        Intent intent = getIntent();

        Message message = new Message(
                user.getDisplayName(),
                user.getPhotoUrl().toString(),
                messageText,
                intent.getDoubleExtra("latitude", 0),
                intent.getDoubleExtra("longitude", 0));

        mMessageDBHelper.saveMessage(message);
    }

    public void cancel(View view) {
        finish();
    }
}
