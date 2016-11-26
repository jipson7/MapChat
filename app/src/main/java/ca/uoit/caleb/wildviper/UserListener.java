package ca.uoit.caleb.wildviper;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by caleb on 2016-11-26.
 */

public class UserListener implements ChildEventListener {

    private GoogleMap mMapReference;

    public UserListener(GoogleMap mapReference) {
        this.mMapReference = mapReference;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
