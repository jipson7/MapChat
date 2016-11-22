package ca.uoit.caleb.wildviper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class WriteMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        setTitle(R.string.write_message_window_title);
    }

    public void done(View view) {

    }

    public void cancel(View view) {

    }
}
