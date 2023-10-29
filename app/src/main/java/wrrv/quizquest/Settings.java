package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

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

        musicLevel = findViewById(R.id.musicLevel);
        logOut = findViewById(R.id.btnPlayerLogOut);
        credits = findViewById(R.id.btnCredits);
        leave = findViewById(R.id.btnLeaveSettings);


    }
    public void openCredits(View view){
        Intent intent = new Intent(this,Credits.class);
        startActivity(intent);
    }

    public void leaveSettings(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}