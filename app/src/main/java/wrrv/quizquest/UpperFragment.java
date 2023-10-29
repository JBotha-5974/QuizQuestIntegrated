package wrrv.quizquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class UpperFragment extends Fragment implements ItemAdapter.OnItemClickListener{

    ArrayList<Item> items;
    RecyclerView rvUpper;
    ItemAdapter adapter;

    Player player;
    String gender;

    public UpperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        try {
            gender = Database.getGender(savedUsername);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upper, container, false);

        rvUpper = view.findViewById(R.id.rvUpper);
        rvUpper.setLayoutManager(
                new GridLayoutManager(getContext(),2));

        getItems();

        adapter = new ItemAdapter(items, this, getContext());
        rvUpper.setAdapter(adapter);

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

        String activityName = getActivity().getClass().getSimpleName();

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
            ArrayList<Item> shirts = Database.getItems(6, gender);
            ArrayList<Item> jackets = Database.getItems(7, gender);

            items.addAll(shirts);
            items.addAll(jackets);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}