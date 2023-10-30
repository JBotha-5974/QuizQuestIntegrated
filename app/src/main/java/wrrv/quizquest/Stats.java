package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Stats extends AppCompatActivity {
    private TextView statProfileTxtName;
    private TextView statPointsTxt;
    private TextView statLevelTxt;
    private TextView statCoinsTxt;
    private TextView statGamesPlayedTxt;
    private ImageView statImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statProfileTxtName = findViewById(R.id.statProfileTxtName);
        statPointsTxt = findViewById(R.id.statPointsTxt);
        statLevelTxt = findViewById(R.id.statLevelTxt);
        statImageView = findViewById(R.id.imgProfileSprite);
        statCoinsTxt = findViewById(R.id.statCoinsTxt);
        statGamesPlayedTxt = findViewById(R.id.statGamesPlayedTxt);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        Player player = Database.getPlayer(savedUsername,savedPassword);
            SpriteGenerator spriteGenerator = new SpriteGenerator(this, player.getUserName());
            Bitmap image = spriteGenerator.generate();
            statImageView.setImageBitmap(image);
            statProfileTxtName.setText(player.getUserName());
            statPointsTxt.setText(getString(R.string.ldb_points,player.getPlayerScore()));
            statLevelTxt.setText(getString(R.string.ldb_level,player.getPlayerLevel()));
            statCoinsTxt.setText(getString(R.string.coins,player.getPlayerCoins()));
            statGamesPlayedTxt.setText(getString(R.string.games_played,player.getGamesPlayed()));
        }

    public void btnProfileReturnClick(View view) {
        Intent intent = new Intent(this, Profile_screen.class);
        startActivity(intent);
        finish();
    }
}