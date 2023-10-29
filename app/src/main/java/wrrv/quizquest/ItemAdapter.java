package wrrv.quizquest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.itemViewHolder> {

    private ArrayList<Item> items;
    private OnItemClickListener clickListener;

    Context context;

    public ItemAdapter(ArrayList<Item> items, OnItemClickListener clickListener, Context context) {
        this.items = items;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_view, parent, false);

        itemViewHolder ivh = new itemViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Item i = items.get(position);
        holder.setItem(i, context);
        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(i));
    }

    @Override
    public int getItemCount() {

        if(items!= null) return items.size();
        else return 0;
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        public Item item;
        public TextView desc;
        public ImageView image;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.txtItemDesc);
            image = itemView.findViewById(R.id.imgStoreItem);
        }

        public itemViewHolder(@NonNull View itemView, Item item) {
            super(itemView);
            this.item = item;
        }

        public void setItem(Item i,Context context) {
            this.item = i;

            desc.setText(i.getItemName());

            image.setImageBitmap(i.getItemImage(context,"gray"));

        }
    }

    public Item get(int position) {
        return items.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
}
