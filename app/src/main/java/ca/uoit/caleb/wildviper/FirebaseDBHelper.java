package ca.uoit.caleb.wildviper;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FirebaseDBHelper  {

    private DatabaseReference mDBRef;
    private UserListener mUserListener;
    private MessageListener mMessageListener;

    FirebaseDBHelper() {
        this.mDBRef = FirebaseDatabase.getInstance().getReference();
    }

    public void setMessageListener(MessageListener messageListener) {
        mMessageListener = messageListener;
        getMessagesReference().addChildEventListener(messageListener);
    }

    public void deleteSingleMessage(String messageKey) {
        getMessagesReference().child(messageKey).removeValue();
    }

    public void deleteAllMessages(String userId) {
        Query userQuery = getMessagesReference().orderByChild("userid").equalTo(userId);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message: dataSnapshot.getChildren()) {
                    message.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    public void setUserListener(UserListener userListener) {
        mUserListener = userListener;
        getUsersReference().addChildEventListener(userListener);
    }

    public void saveMessage(Message message) {
        getMessagesReference().push().setValue(message);
    }

    public void saveUser(User user) {
        getUsersReference().child(user.id).setValue(user);
    }

    public void updateUserLocation(User user, Double latitude, Double longitude) {
        getUsersReference().child(user.id).child("latitude").setValue(latitude);
        getUsersReference().child(user.id).child("longitude").setValue(longitude);
    }

    public void deleteUser(String userId) {
        getUsersReference().child(userId).removeValue();
    }

    private DatabaseReference getMessagesReference() {
        return mDBRef.child("messages");
    }

    private DatabaseReference getUsersReference() {
        return mDBRef.child("users");
    }

    public void removeListeners() {
        getUsersReference().removeEventListener(mUserListener);
        getMessagesReference().removeEventListener(mMessageListener);
    }
}
