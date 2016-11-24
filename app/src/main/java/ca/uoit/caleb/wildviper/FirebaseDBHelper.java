package ca.uoit.caleb.wildviper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseDBHelper  {

    private DatabaseReference mMessageDBRef;

    FirebaseDBHelper() {
        this.mMessageDBRef = FirebaseDatabase.getInstance().getReference().child("messages");
    }


    public void saveMessage(Message message) {
        mMessageDBRef.push().setValue(message);
    }
}
