package ca.uoit.caleb.wildviper;


import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by caleb on 2016-11-24.
 */

public class MessageListener implements ChildEventListener{
    private GoogleMap mMapReference;

    public MessageListener(GoogleMap map) {
        this.mMapReference = map;
    }

    /**
     * New messaged saved on server
     * @param dataSnapshot
     * @param s
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.i("Unique key", dataSnapshot.getKey());
        //TODO Use key to create hashmap with marker handles
        Message message = dataSnapshot.getValue(Message.class);
        message.dropMarker(mMapReference);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        //TODO use key from above to remove individual marker handles as they are removed
        //TODO do the same with user
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        databaseError.toException().printStackTrace();
    }
}
