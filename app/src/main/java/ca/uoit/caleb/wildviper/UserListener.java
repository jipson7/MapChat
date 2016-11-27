package ca.uoit.caleb.wildviper;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

/**
 * Created by caleb on 2016-11-26.
 */

public class UserListener implements ChildEventListener {

    private GoogleMap mMapReference;

    private HashMap<String, User> mUsers;

    public UserListener(GoogleMap mapReference) {
        this.mMapReference = mapReference;
        mUsers = new HashMap<>();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        User user = dataSnapshot.getValue(User.class);
        user.dropOverlay(mMapReference);
        mUsers.put(user.id, user);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String userId = dataSnapshot.getValue(User.class).id;
        User user = mUsers.get(userId);
        user.remove();
        mUsers.remove(userId);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
