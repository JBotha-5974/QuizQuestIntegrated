package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerPerformance extends AppCompatActivity {
    private TextView questionsCorrectTxt;
    private TextView quizzesDoneTxt;
    private TextView pointsTxt;
    private TextView coinsTxt;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_performance);
        Intent intent = getIntent();
        if (intent != null){
            Player player = (Player) intent.getSerializableExtra("player");
            try {
                Database.updateGamesPlayed(player.getUserName());
                int quizzes = Database.getGamesPlayed(player.getUserName());
                int score = intent.getIntExtra("score",0);
                Database.updateScoreAndCoins(player.getUserName(),score,score*2);
                imageView = findViewById(R.id.imageView);
                GenerateSprite generateSprite = new GenerateSprite(this,player.getPlayerSprite());
                imageView.setBackground(generateSprite.getImage());
                questionsCorrectTxt = findViewById(R.id.questionsCorrectTxt);
                quizzesDoneTxt = findViewById(R.id.quizzesDoneTxt);
                pointsTxt = findViewById(R.id.pointsTxt);
                coinsTxt = findViewById(R.id.coinsTxt);
                questionsCorrectTxt.setText(getString(R.string.questions_correct,score));
                quizzesDoneTxt.setText(getString(R.string.quiz_num_for_today,quizzes));
                pointsTxt.setText(getString(R.string.points,score));
                coinsTxt.setText(getString(R.string.coins,score*2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void returnMainMenu(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}