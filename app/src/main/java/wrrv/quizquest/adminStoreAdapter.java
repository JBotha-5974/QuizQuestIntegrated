package wrrv.quizquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adminStoreAdapter extends RecyclerView.Adapter <adminStoreAdapter.itemViewHolder> {

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_store_view, parent, false);

        itemViewHolder ivh = new itemViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Item i = items.get(position);
        holder.setItem(i);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        public Item item;
        public TextView desc;
        public ImageView image;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.imgStoreItem);
            image = itemView.findViewById(R.id.txtItemDesc);
        }

        public itemViewHolder(@NonNull View itemView, Item item) {
            super(itemView);
            this.item = item;
        }

        public void setItem(Item i) {
            this.item = i;

            desc.setText(i.getItemName());

//            image.setImageResource(i.getItemImage());

        }
    }

    private final List<Item> items;

    public adminStoreAdapter(List<Item> items) {
        this.items = items;
    }

    public Item get(int position) {
        return items.get(position);
    }
}
