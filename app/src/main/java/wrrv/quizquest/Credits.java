package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    TextView team;
    TextView manager;
    TextView spriteCredits;

    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        team = findViewById(R.id.txtTeam);
        manager = findViewById(R.id.txtManager);
        spriteCredits = findViewById(R.id.txtCredits);
        leave = findViewById(R.id.btnLeaveCredits);

        new ReadFileAsyncTask(this, spriteCredits, R.raw.credits).execute();
    }

    public void leaveCredits(View view){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }


}