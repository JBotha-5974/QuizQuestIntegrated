package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.security.Provider;

public class Settings extends AppCompatActivity {

    Player player;

    SeekBar musicLevel;
    Button logOut;
    Button credits;
    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Service music = new MainActivityMusicManager();
        //music.onCreate();
        musicLevel = findViewById(R.id.musicLevel);
        logOut = findViewById(R.id.btnPlayerLogOut);
        credits = findViewById(R.id.btnCredits);
        leave = findViewById(R.id.btnLeaveSettings);
        /*@SuppressLint("WrongConstant") AudioManager manager = (AudioManager) getSystemService("MainActivityMusicManager");

        if (manager == null) {
            // Handle the case where the service is not available
        } else {
            int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            musicLevel.setMax(maxVolume);
            int currVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
            musicLevel.setProgress(currVolume);

            // Set up your SeekBar listener here as you did before
            musicLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                    ((MainActivityMusicManager) music).setVolume(AudioManager.STREAM_MUSIC);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Do Nothing
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Do Nothing
                }
            });
        }*/
    }
    public void openCredits(View view){
        Intent intent = new Intent(this,Credits.class);
        startActivity(intent);
    }
    public void leaveSettings(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void clickedLogOut(View view) {
        Intent intent = new Intent(this,LogIn_screen.class);
        startActivity(intent);
        finish();
    }
}