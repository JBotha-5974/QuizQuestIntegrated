package wrrv.quizquest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AccessoriesFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_accessories, container, false);
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getItems();

        adapter = new ItemAdapter(items);

        rvAccessories = getView().findViewById(R.id.rvAccessories);
        rvAccessories.setLayoutManager(
                new GridLayoutManager(getContext(),2));

        rvAccessories.setAdapter(adapter);
    }

    public void getItems(){
        items = new ArrayList<>();

        Item i1 = new Item(1,"Blue Shoe", 10, "shoes", 0);
        Item i2 = new Item(2,"Blue Shoe", 10, "shoes", 1);
        Item i3 = new Item(3,"Blue Shoe", 10, "shoes", 2);

        items.add(i1);
        items.add(i2);
        items.add(i3);

    }
}