package zoran.vidovic.shoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewListActivity extends AppCompatActivity {


    DBHelper dbHelper;
    Lists listView;
    ListViewAdapter adapter;
    RadioButton Yes, No;
    Button OkBtn;
    Button SaveBtn;
    TextView textViewUsername;
    EditText listNameTextEdit;
    String listTitle;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        Yes = findViewById(R.id.radioYes);
        No = findViewById(R.id.radioNo);
        OkBtn = findViewById(R.id.ok);
        SaveBtn = findViewById(R.id.saveBtn);
        textViewUsername = findViewById(R.id.listTitle);
        listNameTextEdit = findViewById(R.id.listNameTextEdit);






        adapter = new ListViewAdapter(this);
        dbHelper = new DBHelper(this,DBHelper.DbName,null,1);




        ///Save button returns to Welcome activity
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = getIntent().getStringExtra("username");
                Bundle bundle = getIntent().getExtras();

                Intent intent = new Intent(NewListActivity.this, WelcomeActivity.class);
                bundle.putString("username",Username);
                intent.putExtras(bundle);
                if (Yes.isChecked()) {
                    listView = new Lists(textViewUsername.getText().toString(), "Yes");
                } else {
                    listView = new Lists(textViewUsername.getText().toString(), "No");
                }


                if (dbHelper.doesListExist(listTitle)) {
                    Toast.makeText(NewListActivity.this, "Saving failed!", Toast.LENGTH_SHORT).show();
                } else {
                   Toast.makeText(NewListActivity.this, "Saving successful!", Toast.LENGTH_SHORT).show();
                   dbHelper.insertList(listView, Username);
                   adapter.addItem(listView);
                   startActivity(intent);
                }
            }

        });
        /// Button to create a list title from input
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listTitle = listNameTextEdit.getText().toString();
                textViewUsername.setText(listTitle);

            }
        });
    }
}