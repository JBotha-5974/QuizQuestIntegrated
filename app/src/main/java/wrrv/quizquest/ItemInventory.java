package wrrv.quizquest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemInventory extends Fragment implements ItemAdapter.OnItemClickListener {

    ArrayList<Item> items;
    RecyclerView rvInventory;
    ItemAdapter adapter;

    public ItemInventory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_inventory, container, false);

        rvInventory = view.findViewById(R.id.rvInventory);
        rvInventory.setLayoutManager(
                new GridLayoutManager(getContext(),2));

        getItems();

        adapter = new ItemAdapter(items, this, getContext());
        rvInventory.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemClick(Item item) {
        // Handle item click here
        // Start the SecondActivity when an item is clicked

        Intent intent = new Intent(getContext(),Update_Item.class);
        intent.putExtra("Item", item);
        startActivity(intent);
    }

    public void getItems(){
        items = new ArrayList<>();

        try{
            items = Database.getItems("shoes");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}