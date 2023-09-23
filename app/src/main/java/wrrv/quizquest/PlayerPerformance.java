package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PlayerPerformance extends AppCompatActivity {
    private TextView questionsCorrectTxt;
    private TextView pointsTxt;
    private TextView coinsTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_performance);
        Intent intent = getIntent();
        if (intent != null){
            Player player = (Player) intent.getSerializableExtra("player");
            try {
                Database.updateGamesPlayed(player.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            int score = intent.getIntExtra("score",0);
            questionsCorrectTxt = findViewById(R.id.questionsCorrectTxt);
            pointsTxt = findViewById(R.id.pointsTxt);
            coinsTxt = findViewById(R.id.coinsTxt);
            questionsCorrectTxt.setText(getString(R.string.questions_correct,score));
            pointsTxt.setText(getString(R.string.points,score));
            coinsTxt.setText(getString(R.string.coins,score*2));
        }
    }

    public void returnMainMenu(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}