package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by caleb on 2016-11-24.
 */

public class MessageWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;
    private MessageListener mMessageListener;

    public MessageWindowAdapter(Context context, MessageListener messageListener) {
        mContext = context;
        mMessageListener = messageListener;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        if (mMessageListener.isMessageMarker(marker)) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View v = layoutInflater.inflate(R.layout.message, null);
            TextView userTxt = (TextView) v.findViewById(R.id.message_window_username);
            TextView messageTxt = (TextView) v.findViewById(R.id.message_window_message);
            String username = marker.getTitle();
            String message = marker.getSnippet();
            userTxt.setText(username);
            messageTxt.setText(message);
            return v;
        } else {
            return null;
        }

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
