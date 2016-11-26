package ca.uoit.caleb.wildviper;


import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

/**
 * Created by caleb on 2016-11-24.
 */

public class MessageListener implements ChildEventListener{

    private GoogleMap mMapReference;
    private HashMap<String, String> mMessageKeys;

    public MessageListener(GoogleMap map) {
        this.mMapReference = map;
        mMessageKeys = new HashMap<>();
    }

    public String getMessageKey(String markerId) {
        return mMessageKeys.get(markerId);
    }

    /**
     * New messaged saved on server
     * @param dataSnapshot
     * @param s
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Message message = dataSnapshot.getValue(Message.class);
        Marker marker = message.dropMarker(mMapReference);
        mMessageKeys.put(marker.getId(), dataSnapshot.getKey());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        mMessageKeys.values().remove(dataSnapshot.getKey());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        databaseError.toException().printStackTrace();
    }
}
