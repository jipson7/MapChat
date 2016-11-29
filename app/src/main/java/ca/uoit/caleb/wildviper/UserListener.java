package ca.uoit.caleb.wildviper;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
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

    private NotificationPlayer mNotificationPlayer;

    public UserListener(GoogleMap mapReference, Context context) {
        this.mMapReference = mapReference;
        mUsers = new HashMap<>();
        mNotificationPlayer = new NotificationPlayer(context);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String loggedInId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        User user = dataSnapshot.getValue(User.class);

        if (user.id.equals(loggedInId)) {
            return;
        }

        user.dropMarker(mMapReference);
        mUsers.put(user.id, user);
        mNotificationPlayer.userAdded();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        String loggedInId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        User updatedUser = dataSnapshot.getValue(User.class);

        if (updatedUser.id.equals(loggedInId)) {
            return;
        }

        Double latitude = updatedUser.latitude;
        Double longitude = updatedUser.longitude;
        User user = mUsers.get(updatedUser.id);
        user.latitude = latitude;
        user.longitude = longitude;
        user.moveMarker(latitude, longitude);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String loggedInId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        String userId = dataSnapshot.getValue(User.class).id;

        if (userId.equals(loggedInId)) {
            return;
        }

        User user = mUsers.get(userId);
        user.remove();
        mUsers.remove(userId);
        mNotificationPlayer.userRemoved();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
