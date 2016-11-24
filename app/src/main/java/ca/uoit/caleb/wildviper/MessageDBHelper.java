package ca.uoit.caleb.wildviper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by caleb on 2016-11-23.
 */

public class MessageDBHelper {

    private DatabaseReference mMessageDBRef;

    MessageDBHelper() {
        this.mMessageDBRef = FirebaseDatabase.getInstance().getReference().child("messages");
    }

    public void saveMessage(Message message) {
        mMessageDBRef.push().setValue(message);
    }
}
