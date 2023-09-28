package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile_screen extends AppCompatActivity {

    Button submissions;
    Button leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //temporary just to get to submissions

        submissions = findViewById(R.id.btnSubmissions);

        submissions.setOnClickListener(view ->{
            openSubmissions(view);
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