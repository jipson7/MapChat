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

    public void saveUser(User user) {
        mDBRef.child("users").child(user.id).setValue(user);
    }

    public void deleteUser(User user) {
        mDBRef.child("users").child(user.id).removeValue();
    }
}
