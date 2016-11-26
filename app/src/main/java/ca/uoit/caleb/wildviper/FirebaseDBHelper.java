package ca.uoit.caleb.wildviper;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseDBHelper  {

    private DatabaseReference mDBRef;

    FirebaseDBHelper() {
        this.mDBRef = FirebaseDatabase.getInstance().getReference();
    }

    public void setMessageListener(MessageListener messageListener) {
        getMessagesReference().addChildEventListener(messageListener);
    }

    public void deleteSingleMessage(String messageKey) {
        getMessagesReference().child(messageKey).removeValue();
    }

    public void setUserListener(UserListener userListener) {
        getUsersReference().addChildEventListener(userListener);
    }

    public void saveMessage(Message message) {
        getMessagesReference().push().setValue(message);
    }

    public void saveUser(User user) {
        getUsersReference().child(user.id).setValue(user);
    }

    public void deleteUser(User user) {
        getUsersReference().child(user.id).removeValue();
    }

    private DatabaseReference getMessagesReference() {
        return mDBRef.child("messages");
    }

    private DatabaseReference getUsersReference() {
        return mDBRef.child("users");
    }
}
