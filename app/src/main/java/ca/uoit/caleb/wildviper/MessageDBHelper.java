package ca.uoit.caleb.wildviper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by caleb on 2016-11-23.
 */

public class MessageDBHelper {

    private DatabaseReference mDatabase;

    MessageDBHelper() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void saveMessage(Message message) {
        mDatabase.child("messages").setValue(message);
    }
}
