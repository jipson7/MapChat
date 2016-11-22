package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WriteMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        setTitle(R.string.write_message_window_title);
    }

    public void done(View view) {
        EditText messageView = (EditText) findViewById(R.id.write_message_txt);
        String message = messageView.getText().toString();
    }

    public void cancel(View view) {

    }
}
