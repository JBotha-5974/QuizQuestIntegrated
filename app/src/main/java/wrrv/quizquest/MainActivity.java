package wrrv.quizquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startBtn = findViewById(R.id.btnPlay);
        txtCoinsNum = findViewById(R.id.txtCoinsNum);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        txtCoinsNum.setText("" + player.getPlayerCoins());
        txtHintsNum = findViewById(R.id.txtHintsNum);
        txtHintsNum.setText("" + player.getPlayerHints());
        try {
            quizzesDone = Database.getGamesPlayed(player.getUserName());
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
        if (quizzesDone < 3) {
            Intent intent = new Intent(this, GameScreen.class);
            intent.putExtra("player",player);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, R.string.games_msg, Toast.LENGTH_LONG).show();
        }
    }
    public void showLeaderBoard(View view) {
        Intent intent = new Intent(this,LeaderBoardActivity.class);
        startActivity(intent);
    }

    public void openProfile(View view){
        Intent intent = new Intent(this,Profile_screen.class);
        startActivity(intent);
    }

    public void openStore(View view){
        Intent intent = new Intent(this,Store_screen.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }
}