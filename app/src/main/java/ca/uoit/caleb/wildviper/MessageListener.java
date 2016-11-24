package ca.uoit.caleb.wildviper;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by caleb on 2016-11-24.
 */

public class MessageListener implements ValueEventListener {
    private GoogleMap mMapReference;
    private Context mContext;

    public MessageListener(GoogleMap map, Context context) {
        this.mMapReference = map;
        this.mContext = context;
    }

    /**
     * Listen for changes in Messages table
     * @param dataSnapshot
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mMapReference.clear();
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            Message message = postSnapshot.getValue(Message.class);
            message.dropMarker(mMapReference, mContext);
        }

    }

    /**
     * Listen for errors on the Firebase DB connection
     * @param databaseError
     */
    @Override
    public void onCancelled(DatabaseError databaseError) {
        databaseError.toException().printStackTrace();
    }
}
