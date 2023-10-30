package wrrv.quizquest;

import android.app.*;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MainActivityMusicManager extends Service {
    private final IBinder binder = new LocalBinder();
    private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    public class LocalBinder extends Binder {
        MainActivityMusicManager getService() {
            // Return this instance of LocalService so clients can call public methods.
            return MainActivityMusicManager.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.play);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        player.start();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TODO

    }
    public void setVolume(int iVal)
    {
        player.setVolume(iVal,iVal);
    }
    public IBinder onUnBind(Intent arg0) {
        return binder;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {

        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}