package ca.uoit.caleb.wildviper;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by caleb on 2016-11-27.
 */

public class NotificationPlayer {
    private MediaPlayer mMessageAddedNotification;
    private MediaPlayer mMessageRemovedNotificaiton;
    private MediaPlayer mUserAddedNotification;
    private MediaPlayer mUserRemovedNotification;

    public NotificationPlayer(Context context) {
        this.mMessageAddedNotification = MediaPlayer.create(context, R.raw.message_added);
        this.mMessageRemovedNotificaiton = MediaPlayer.create(context, R.raw.message_removed);
        this.mUserAddedNotification = MediaPlayer.create(context, R.raw.user_added);
        this.mUserRemovedNotification = MediaPlayer.create(context, R.raw.user_removed);
    }

    public void messageAdded() {
        if (!mMessageAddedNotification.isPlaying()) {
            mMessageAddedNotification.start();
        }
    }

    public void messageRemoved() {
        if (!mMessageRemovedNotificaiton.isPlaying()) {
            mMessageRemovedNotificaiton.start();
        }
    }

    public void userAdded() {
        if (!mUserAddedNotification.isPlaying()) {
            mUserAddedNotification.start();
        }
    }

    public void userRemoved() {
        if (!mUserRemovedNotification.isPlaying()) {
            mUserRemovedNotification.start();
        }
    }
}
