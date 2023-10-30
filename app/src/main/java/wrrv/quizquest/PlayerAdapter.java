package wrrv.quizquest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>{
    private View.OnClickListener onClickListener;
    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_player,parent,false);
        return new PlayerViewHolder(view, parent.getContext());
    }
    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.setPlayer(player, position);
        holder.itemView.setOnClickListener(onClickListener);
    }
    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    @Override
    public int getItemCount() {
        return players.size();
    }
    public static class PlayerViewHolder extends RecyclerView.ViewHolder{
        public Context context;
        public Player player;
        public ImageView recyclerSprite;
        public TextView userName;
        public TextView score;
        public TextView pos;
        public PlayerViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            recyclerSprite = itemView.findViewById(R.id.recycleSprite);
            userName = itemView.findViewById(R.id.recycleUserName);
            score = itemView.findViewById(R.id.recycleScore);
            pos = itemView.findViewById(R.id.recyclerPos);
        }
        public void setPlayer(Player player, int position){
            this.player = player;
            SpriteGenerator spriteGenerator = new SpriteGenerator(context, player.getUserName());
            recyclerSprite.setImageBitmap(spriteGenerator.generate());
            userName.setText(player.getUserName());
            score.setText(String.valueOf(player.getPlayerScore()) + " points");
            pos.setText(String.valueOf(position+1));
        }
    }
    private final List<Player> players;
    public PlayerAdapter(List<Player> players){this.players = players;
        Collections.sort(players,new PlayerComparator());
    }
}
