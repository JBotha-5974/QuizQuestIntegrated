package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Profile_screen extends AppCompatActivity {

    Button submissions;
    ImageButton leave;
    TextView username;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //temporary just to get to submissions
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
    }

    public void btnCustomizeSpriteClick(View view) {
        Intent intent = new Intent(this,CustomizeSprite.class);
        startActivity(intent);
    }
}