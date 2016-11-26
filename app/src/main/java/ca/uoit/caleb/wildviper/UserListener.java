package ca.uoit.caleb.wildviper;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

/**
 * Created by caleb on 2016-11-26.
 */

public class UserListener implements ChildEventListener {

    private GoogleMap mMapReference;

    private HashMap<String, UserOverlay> mOverlayHandles;

    public UserListener(GoogleMap mapReference) {
        this.mMapReference = mapReference;
        mOverlayHandles = new HashMap<>();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        User user = dataSnapshot.getValue(User.class);
        UserOverlay userOverlay = new UserOverlay(user, mMapReference);
        mOverlayHandles.put(user.id, userOverlay);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        UserOverlay userOverlay = mOverlayHandles.get(user.id);
        userOverlay.remove();
        mOverlayHandles.remove(user.id);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
