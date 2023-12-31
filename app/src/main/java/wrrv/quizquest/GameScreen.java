package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameScreen extends AppCompatActivity {
    private TextView txtQuestion;
    private Button btnHint;
    private ImageButton skipArrow;
    private TextView txtTimer;
    private ProgressBar progress;
    private CountDownTimer countdownTimer;
    private ArrayList<Question> questions;
    private ImageView tickImg;
    private ImageView crossImg;
    private Button[] btns;
    private int curIndex = 0;
    private int correct = 0;
    private Player player;
    private long timeTaken = 0;
    private int gameID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        txtQuestion = findViewById(R.id.txtQuestion);
        Button answerBtn1 = findViewById(R.id.answerBtn1);
        Button answerBtn2 = findViewById(R.id.answerBtn2);
        Button answerBtn3 = findViewById(R.id.answerBtn3);
        Button answerBtn4 = findViewById(R.id.answerBtn4);
        btns = new Button[]{answerBtn1, answerBtn2, answerBtn3, answerBtn4};
        btnHint = findViewById(R.id.btnHint);
        progress = findViewById(R.id.progressBar);
        questions = new ArrayList<>();
        btnHint = findViewById(R.id.btnHint);
        txtTimer = findViewById(R.id.txtTimer);
        tickImg = findViewById(R.id.tickImg);
        crossImg = findViewById(R.id.crossImg);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
            gameID = Database.getLatestGameID();
            gameID++;
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormat);
            Game game = new Game(gameID,player.getUserName(),formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        countdownTimer = new CountDownTimer(21000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timeTaken = 21000 - secondsRemaining;
                txtTimer.setText("00:" + secondsRemaining);
            }
            @Override
            public void onFinish() {
                try {
                    mapQuestions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        skipArrow = findViewById(R.id.skipArrow);
        skipArrow.setOnClickListener(view -> {
            try {
                mapQuestions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            obtainQuestions();
            mapQuestions();
            if (Database.getHints(player.getUserName()) == 0){
                btnHint.setVisibility(View.INVISIBLE);
            }
            progress.setMax(questions.size()+1);
            btnHint.setOnClickListener(view -> {
                try {
                    if (Database.getHints(player.getUserName()) > 0) {
                        Database.updateHints(player.getUserName(),-1);
                        List<Button> shuffleButtons = Arrays.asList(answerBtn1, answerBtn2, answerBtn3, answerBtn4);
                        Collections.shuffle(shuffleButtons);
                        btnHint.setEnabled(false);
                        btnHint.setVisibility(View.INVISIBLE);
                        for (Button b : shuffleButtons) {
                            if (!(b.getText().equals(questions.get(curIndex - 1).getCorrectAnswer()))) {
                                b.setVisibility(View.INVISIBLE);
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void mapQuestions() throws Exception {
        if (curIndex < questions.size()){
            GameDetails gameDetails = new GameDetails(gameID,questions.get(curIndex).getQuestionID(),correct,timeTaken);
            resetBtns();
            Question temp = questions.get(curIndex);
            txtQuestion.setText(temp.getQuestionText());
            List<String> options = Arrays.asList(temp.getCorrectAnswer(),temp.getIncorrectAnswer1(),temp.getIncorrectAnswer2(),temp.getIncorrectAnswer3());
            Collections.shuffle(options);
            for (int i = 0; i < btns.length;i++){
                btns[i].setText(options.get(i));
            }
            curIndex++;
            progress.setProgress(curIndex);
            countdownTimer.start();
        }
        else{
            Intent intent = new Intent(this,PlayerPerformance.class);
            intent.putExtra("score",correct);
            intent.putExtra("player",player);
            startActivity(intent);
            finish();
        }
    }
    private void obtainQuestions() throws Exception {
        try {
            questions = Database.LoadQuestions();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkAnswer(View view) throws Exception {
        skipArrow.setEnabled(false);
        countdownTimer.cancel();
        Button cur = (Button) view;
        int[] coordinate = new int[2];
        cur.getLocationOnScreen(coordinate);
        if (cur.getText().equals(questions.get(curIndex-1).getCorrectAnswer())){
            cur.setBackgroundResource(R.drawable.custom_correct_border);
            tickImg.setX(coordinate[0] + cur.getWidth() + 20);
            tickImg.setY(coordinate[1] - cur.getHeight()/3);
            tickImg.setVisibility(View.VISIBLE);
            correct++;
        }else{
            cur.setBackgroundResource(R.drawable.custom_incorrect_border);
            crossImg.setX(coordinate[0] + cur.getWidth() + 20);
            crossImg.setY(coordinate[1] - cur.getHeight()/3);
            crossImg.setVisibility(View.VISIBLE);
            for (Button btn : btns) {
                if (btn.getText().equals(questions.get(curIndex-1).getCorrectAnswer())) {
                    btn.setBackgroundResource(R.drawable.custom_correct_border);
                    coordinate = new int[2];
                    btn.getLocationOnScreen(coordinate);
                    tickImg.setX(coordinate[0] + btn.getWidth() + 20);
                    tickImg.setY(coordinate[1] - btn.getHeight()/3);
                    tickImg.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mapQuestions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
    private void resetBtns() throws Exception {
        for (Button btn: btns){
            btn.setBackgroundResource(R.color.white);
            btn.setVisibility(View.VISIBLE);
            btn.setText("");
        }
        skipArrow.setEnabled(true);
        if (Database.getHints(player.getUserName()) > 0){
            btnHint.setEnabled(true);
            btnHint.setVisibility(View.VISIBLE);
        }
        crossImg.setVisibility(View.INVISIBLE);
        tickImg.setVisibility(View.INVISIBLE);
        skipArrow.setVisibility(View.VISIBLE);
    }
}