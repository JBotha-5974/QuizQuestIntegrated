package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LeaderBoardProfile extends AppCompatActivity {
    private TextView ldbProfileTxtName;
    private TextView ldbPointsTxt;
    private TextView ldbLevelTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_profile);
        ldbProfileTxtName = findViewById(R.id.ldbProfileTxtName);
        ldbPointsTxt = findViewById(R.id.ldbPointsTxt);
        ldbLevelTxt = findViewById(R.id.ldbLevelTxt);
        Intent intent = getIntent();
        if (intent != null){
            Player player = (Player)intent.getSerializableExtra("player");
            ldbProfileTxtName.setText(player.getUserName());
            ldbPointsTxt.setText(getString(R.string.ldb_points,player.getPlayerScore()));
            ldbLevelTxt.setText(getString(R.string.ldb_level,player.getPlayerLevel()));
        }
    }

    public void returnLeaderBoard(View view) {
        Intent intent = new Intent(this,LeaderBoardActivity.class);
        startActivity(intent);
        finish();
    }
}