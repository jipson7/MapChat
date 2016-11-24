package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by caleb on 2016-11-24.
 */

public class MessageListener implements ChildEventListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap mMapReference;
    private Context mContext;

    public MessageListener(GoogleMap map, Context context) {
        this.mMapReference = map;
        this.mContext = context;
    }


    /**
     * Map Long Click to Add new Message
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent i = new Intent(mContext, WriteMessageActivity.class);
        i.putExtra("latitude", latLng.latitude);
        i.putExtra("longitude", latLng.longitude);
        mContext.startActivity(i);
    }

    /**
     * New messaged saved on server
     * @param dataSnapshot
     * @param s
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Message message = dataSnapshot.getValue(Message.class);
        message.dropMarker(mMapReference, mContext);
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
        databaseError.toException().printStackTrace();
    }
}
