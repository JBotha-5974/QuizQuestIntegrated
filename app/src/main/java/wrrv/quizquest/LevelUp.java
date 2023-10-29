package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LevelUp extends AppCompatActivity {
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        Intent intent = getIntent();

        if (intent != null){
            player = (Player) intent.getSerializableExtra("player");
            try {
                int iLevel = player.getPlayerLevel();
                iLevel++;
                Database.updatePlayerLevel(player.getUserName(),iLevel);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}