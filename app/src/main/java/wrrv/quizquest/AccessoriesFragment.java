package wrrv.quizquest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AccessoriesFragment extends Fragment implements ItemAdapter.OnItemClickListener{

    ArrayList<Item> items;
    RecyclerView rvAccessories;
    ItemAdapter adapter;

    public AccessoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accessories, container, false);

        rvAccessories = view.findViewById(R.id.rvAccessories);
        rvAccessories.setLayoutManager(
                new GridLayoutManager(getContext(),2));

        getItems();

        adapter = new ItemAdapter(items, this);
        rvAccessories.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemClick(Item item) {
        // Handle item click here
        // Start the SecondActivity when an item is clicked
        Intent intent = new Intent(getContext(),View_Item.class);
        startActivity(intent);
    }

    public void getItems(){
        items = new ArrayList<>();

        Item i1 = new Item(1,"Blue Shoe", 10, "shoes", 0);
        Item i2 = new Item(2,"Black Shoe", 10, "shoes", 1);
        Item i3 = new Item(3,"Green Shoe", 10, "shoes", 2);
        Item i4 = new Item(3,"Gray Shoe", 10, "shoes", 3);
        Item i5 = new Item(3,"Brown Shoe", 10, "shoes", 4);
        Item i6 = new Item(3,"Dark Gray Shoe", 10, "shoes", 5);
        Item i7 = new Item(3,"Dark Green Shoe", 10, "shoes", 6);

        items.add(i1);
        items.add(i2);
        items.add(i3);
        items.add(i4);
        items.add(i5);
        items.add(i6);
        items.add(i7);

    }
}