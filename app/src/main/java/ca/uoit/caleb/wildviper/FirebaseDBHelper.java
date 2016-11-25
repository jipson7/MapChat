package ca.uoit.caleb.wildviper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseDBHelper  {

    private DatabaseReference mDBRef;

    FirebaseDBHelper() {
        this.mDBRef = FirebaseDatabase.getInstance().getReference();
    }


    public void saveMessage(Message message) {
        mDBRef.child("messages").push().setValue(message);
    }
}
