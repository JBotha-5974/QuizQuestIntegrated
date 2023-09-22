package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import wrpv.assignment02.Contacts.EqualSpaceItem;

public class LeaderBoardActivity extends AppCompatActivity {
    private ArrayList<Player> playerList;
    private PlayerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
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
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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