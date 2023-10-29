package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class QuizQuestSettings extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private AudioManager soundManager;
    private SeekBar volumeControl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_quest_settings);
        //mediaPlayer = MediaPlayer.create(this, //Values)  This is the Song Choice, Needs some Work as song not chosen/ may use different overarching system to play aus=dio
        volumeControl = findViewById(R.id.cntrlSoundBar);
        soundManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int iMaxVol = soundManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int iCurVol = soundManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setMax(iMaxVol);
        volumeControl.setProgress(iCurVol);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                soundManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }
            //These two are not relevant
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void btnLogOutClick(View view) {
        Intent intent = new Intent(this, LogIn_screen.class);
        startActivity(intent);
    }

    public void btnSettingsBackClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}