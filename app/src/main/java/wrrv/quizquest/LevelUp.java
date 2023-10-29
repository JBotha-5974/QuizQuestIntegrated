package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LevelUp extends AppCompatActivity {
    private Player player;
    private int iLevel;
    TextView tbxDisplay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        tbxDisplay = findViewById(R.id.tbxLevelDisplayLevel);
        Intent intent = getIntent();
        if (intent != null){
            player = (Player) intent.getSerializableExtra("player");
            try {
                iLevel = player.getPlayerLevel();
                iLevel++;
                Database.updatePlayerLevel(player.getUserName(),iLevel);

            } catch (Exception e) {
                tbxDisplay.setText("");
                e.printStackTrace();
            }
        }
        tbxDisplay.setText("You are now Level" + iLevel);
    }

    public void btnLevelUpContinueClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}