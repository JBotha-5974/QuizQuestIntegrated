package wrrv.quizquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class AccessoriesFragment extends Fragment implements ItemAdapter.OnItemClickListener{

    ArrayList<Item> items;
    RecyclerView rvAccessories;
    ItemAdapter adapter;

    String activityName;

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

        activityName = getActivity().getClass().getSimpleName();

        getItems();

        adapter = new ItemAdapter(items, this, getContext());
        rvAccessories.setAdapter(adapter);

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

        Log.d("Buy item check", "Activity -> " + activityName);

        if(activityName.equals("Store_screen")){
            //if player
            Intent intent = new Intent(getContext(), Buy_Item.class);
            intent.putExtra("Item", item);
            startActivity(intent);
        }
        else if(activityName.equals("Admin_Store")){
            //if admin
            Intent intent = new Intent(getContext(),Update_Item.class);
            intent.putExtra("Item", item);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(), "Invalid activity: " + activityName, Toast.LENGTH_SHORT).show();
        }
    }

    public void getItems(){
        items = new ArrayList<>();

        try{

            items = Database.getItems(8);

            System.out.println("Got accessories");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}