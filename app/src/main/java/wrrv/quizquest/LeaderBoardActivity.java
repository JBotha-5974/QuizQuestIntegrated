package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaderBoardActivity extends AppCompatActivity {
    private ArrayList<Player> playerList;
    private PlayerAdapter adapter;
    private TextView timeLefttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        timeLefttxt = findViewById(R.id.timeLefttxt);
        calculateAndDisplayTimeDifference();
        try {
            playerList = Database.getPlayers();
            RecyclerView lstPlayers = findViewById(R.id.leaderBoardRecycler);
            adapter = new PlayerAdapter(playerList);
            RecyclerView.LayoutManager layoutManager;
            layoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
            lstPlayers.addItemDecoration(new EqualSpaceItem(20));
            lstPlayers.setLayoutManager(layoutManager);
            lstPlayers.setAdapter(adapter);
            adapter.setOnClickListener(view -> {
                PlayerAdapter.PlayerViewHolder viewHolder =
                        (PlayerAdapter.PlayerViewHolder) lstPlayers.findContainingViewHolder(view);
                Player player = viewHolder.player;
                Intent intent = new Intent(this,LeaderBoardProfile.class);
                intent.putExtra("player",player);
                startActivity(intent);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void calculateAndDisplayTimeDifference() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date targetDate = calendar.getTime();
        long currentTimeMillis = System.currentTimeMillis();
        long targetTimeMillis = targetDate.getTime();
        if (targetTimeMillis <= currentTimeMillis) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            targetDate = calendar.getTime();
            targetTimeMillis = targetDate.getTime();
        }
        long timeDifferenceMillis = targetTimeMillis - currentTimeMillis;
        long days = timeDifferenceMillis / (1000 * 60 * 60 * 24);
        long hours = (timeDifferenceMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        String timeLeft = String.format(Locale.getDefault(), "%d days %d hours left", days, hours);
        timeLefttxt.setText(timeLeft);
    }

    public void returnBtn(View view) {
        finish();
    }

    public void displayHelp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_leaderboard);
        builder.setMessage(R.string.help_msg);
        builder.setNegativeButton("OK",((dialogInterface, i) -> dialogInterface.dismiss()));
        builder.show();
    }
}