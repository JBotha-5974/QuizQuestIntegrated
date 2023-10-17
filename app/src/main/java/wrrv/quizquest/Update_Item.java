package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Update_Item extends AppCompatActivity {

    Item item;

    EditText description;
    ImageView image;
    EditText price;

    ImageButton leave;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        description = findViewById(R.id.txtUpdateDesc);
        image = findViewById(R.id.imgUpdateItem);
        price = findViewById(R.id.txtUpdatePrice);

        update = findViewById(R.id.btnUpdate);
        leave = findViewById(R.id.btnLeaveUpdate);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        description.setText(item.getItemName());
        image.setImageResource(item.getItemImage());
        price.setText(String.valueOf(item.getItemPrice()));
    }

    public void updateItem(View view){

        String desc = description.getText().toString();

        String strPrice = price.getText().toString();
        int intPrice = 0;

        try {
            intPrice = Integer.parseInt(strPrice);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
        }

        item.setUpdate(item.getItemID(),desc, intPrice);

        Toast.makeText(this, "The item has been updated", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,Store_screen.class);
        startActivity(intent);
    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Admin_Store.class);
        startActivity(intent);
    }
}