package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Profile_screen extends AppCompatActivity {

    Button submissions;
    ImageButton leave;
    TextView username;

    ImageView sprite;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        submissions = findViewById(R.id.btnSubmissions);
        leave = findViewById(R.id.btnLeaveProfile);
        username = findViewById(R.id.lblUsername);
        sprite = findViewById(R.id.imgTest);

        SpriteGenerator sg = new SpriteGenerator(this, savedUsername);

        sprite.setImageBitmap(sg.generate());

        username.setText("Hello " + player.getUserName() + "!");

        submissions.setOnClickListener(view ->{
            openSubmissions(view);
        });

        leave.setOnClickListener(view ->{
            leaveClick(view);
        });
    }

    public void openSubmissions(View view){
        Intent intent = new Intent(this,Submissions_screen.class);
        startActivity(intent);
    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnCustomizeSpriteClick(View view) {
        Intent intent = new Intent(this,Customize.class);
        startActivity(intent);
    }

    public void btnStatsticsClick(View view) {
        Intent intent = new Intent(this,Stats.class);
        startActivity(intent);
    }
}