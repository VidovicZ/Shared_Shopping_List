package zoran.vidovic.shoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowListActivity extends AppCompatActivity {

    private String ListTitle;
    private Button AddButton;
    private EditText Input;
    private TextView Title;
    private List<Items> Items;
    private ZadatakAdapter adapter;

    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        adapter = new ZadatakAdapter(this);
        dbHelper = new DBHelper(this, DBHelper.DbName,null,1);

        Input = findViewById(R.id.listNameId);
        Title = findViewById(R.id.ListTitleShow);
        ListView List= findViewById(R.id.ShoppingListId);
        AddButton = findViewById(R.id.AddButtonId);


        List.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

       // Show list name
        if (bundle != null) {
            ListTitle = getIntent().getStringExtra("title");
            Title.setText(ListTitle);
        }
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Input.getText().toString();
                if (title.trim().equals(""))
                {
                    Toast.makeText(ShowListActivity.this, "Please Enter a Item", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Items it = new Items(title,false,null);
                    String ID = UUID.randomUUID().toString();

                    Toast.makeText(ShowListActivity.this, "Item added succesfully", Toast.LENGTH_SHORT).show();
                    dbHelper.insertItem(it,ID, ListTitle);
                    adapter.addItem(new Items(Input.getText().toString(),false,ID));
                }
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();

        Items[] items = dbHelper.readItems(ListTitle);
        adapter.update(items);


    }
}