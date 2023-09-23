package wrrv.quizquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private Button startBtn;
    private TextView txtCoinsNum;
    private TextView txtHintsNum;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private int quizzesDone = 0;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new Player("JoshP", "Josh123", null, 10, 100, 12, 5, 1, 0);
        startBtn = findViewById(R.id.btnPlay);
        txtCoinsNum = findViewById(R.id.txtCoinsNum);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        txtCoinsNum.setText("" + player.getPlayerCoins());
        txtHintsNum = findViewById(R.id.txtHintsNum);
        txtHintsNum.setText("" + player.getPlayerHints());
        try {
            quizzesDone = Database.getGamesPlayed("JoshP");
            if (quizzesDone == 1) {
                checkBox1.setChecked(true);
            } else if (quizzesDone == 2) {
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
            } else if (quizzesDone >= 3) {
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startGame(View view) {
        if (quizzesDone <= 3) {
            Intent intent = new Intent(this, GameScreen.class);
            intent.putExtra("player",player);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(MainActivity.this, R.string.games_msg, Toast.LENGTH_LONG).show();
        }
    }
    public void showLeaderBoard(View view) {
        Intent intent = new Intent(this,LeaderBoardActivity.class);
        startActivity(intent);
    }
}