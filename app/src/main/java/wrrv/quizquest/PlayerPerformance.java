package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class PlayerPerformance extends AppCompatActivity {
    private TextView questionsCorrectTxt;
    private TextView quizzesDoneTxt;
    private TextView pointsTxt;
    private TextView coinsTxt;
    private TextView performance;
    private ImageView imageView;
    private KonfettiView confetti;
    private Player player;
    private int quizzes;
    private int score;

    private Button btnContinue;
    private Boolean confettiPosted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_performance);
        Log.d("PlayerPerformance", "onCreate called"); // Add this line
        confettiPosted = false;
        Intent intent = getIntent();
        if (intent != null){
            player = (Player) intent.getSerializableExtra("player");
            try {
                Database.updateGamesPlayed(player.getUserName());
                quizzes = Database.getGamesPlayed(player.getUserName());
                score = intent.getIntExtra("score",0);
                Database.updateScoreAndCoins(player.getUserName(),score,score*2);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                imageView = findViewById(R.id.imageView);
                SpriteGenerator spriteGenerator = new SpriteGenerator(this, player.getUserName());
                imageView.setImageBitmap(spriteGenerator.generate());

                quizzesDoneTxt = findViewById(R.id.quizzesDoneTxt);
                quizzesDoneTxt.setText(getString(R.string.quiz_num_for_today,quizzes));

                performance = findViewById(R.id.performanceTxtView);
                questionsCorrectTxt = findViewById(R.id.questionsCorrectTxt);
                pointsTxt = findViewById(R.id.pointsTxt);
                coinsTxt = findViewById(R.id.coinsTxt);

                questionsCorrectTxt.setText(getString(R.string.questions_correct,score));
                pointsTxt.setText(getString(R.string.points,score));
                coinsTxt.setText(getString(R.string.coins,score*2));

                performance.setVisibility(View.INVISIBLE);
                questionsCorrectTxt.setVisibility(View.INVISIBLE);
                pointsTxt.setVisibility(View.INVISIBLE);
                coinsTxt.setVisibility(View.INVISIBLE);

                btnContinue = findViewById(R.id.performanceBtn);
                btnContinue.setEnabled(false);
                btnContinue.setVisibility(View.INVISIBLE);
                confetti = findViewById(R.id.confettiView);


                if (!confettiPosted) {
                    EmitterConfig ec = new Emitter(300, TimeUnit.MILLISECONDS).max(300);
                    confetti.start(
                            new PartyFactory(ec)
                                    .shapes(Shape.Circle.INSTANCE, Shape.Square.INSTANCE)
                                    .spread(360)
                                    .position(0.5, 0.25, 1, 1)
                                    .sizes(new Size(8, 50, 10))
                                    .timeToLive(1500)
                                    .fadeOutEnabled(true)
                                    .build()
                    );
                    Handler handler = new Handler();
                    int delayMillis = 1500;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            performance.setVisibility(View.VISIBLE);
                            questionsCorrectTxt.setVisibility(View.VISIBLE);
                            pointsTxt.setVisibility(View.VISIBLE);
                            coinsTxt.setVisibility(View.VISIBLE);
                            btnContinue.setEnabled(true);
                            btnContinue.setVisibility(View.VISIBLE);
                        }
                    }, delayMillis);
                    confettiPosted = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void returnMainMenu(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        if (player.getPlayerScore() % 20 > player.getPlayerLevel()){
            Intent goLevelUp = new Intent(this, LevelUp.class);
            goLevelUp.putExtra("player",player);
            startActivity(goLevelUp);
            finish();
        }
        else{
            startActivity(intent);
            finish();
        }
    }
}