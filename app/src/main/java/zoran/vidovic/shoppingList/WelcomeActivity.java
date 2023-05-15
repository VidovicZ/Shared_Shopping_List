package zoran.vidovic.shoppingList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ListView;


public class WelcomeActivity extends AppCompatActivity {


    AlertDialog.Builder builder;
    String ListTitle;
    Button MyListsBtn;
    Button NewListBtn;
    ListViewAdapter adapter;
    String Username;

    private TextView textViewUsername;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MyListsBtn = findViewById(R.id.MyLists);
        NewListBtn = findViewById(R.id.NewList);
        textViewUsername = findViewById(R.id.userTextView);


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Username = getIntent().getStringExtra("username");
            textViewUsername.setText(Username);
        }


        // SEE MY LIST
        MyListsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lists[] lists = dbHelper.readUserLists(textViewUsername.getText().toString());
                adapter.update(lists);
            }
        });


        adapter = new ListViewAdapter(this);
        builder = new AlertDialog.Builder(this);
        dbHelper = new DBHelper(this,DBHelper.DbName,null,1);

        //alert Dialog (create a new list)
        NewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewListActivity.class);
                builder.setTitle("New List Dialog")
                        .setMessage("Are you sure you want to create a new list")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bundle.putString("username",Username);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        ListView list = findViewById(R.id.list_id);
        list.setAdapter(adapter);

        //Enter the submenu for list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent myIntent = new Intent(getApplicationContext(), ShowListActivity.class);
                 Bundle bundle = new Bundle();
                 Lists lv = (Lists) adapter.getItem(i);
                 ListTitle = lv.getName();
                 bundle.putString("title",ListTitle);
                 myIntent.putExtras(bundle);
                 startActivity(myIntent);
            }
        });

        //Remove item from list
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Lists list = (Lists)adapter.getItem(position);
                    dbHelper.deleteList(list.getName());
                    adapter.removeItem((Lists) adapter.getItem(position));

                return true;
            }
        });

    }
    public void onBackPressed(){
        Intent myIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(myIntent);

    }
    @Override
    protected void onResume() {
        super.onResume();
        Lists[] lists = dbHelper.readAllLists(textViewUsername.getText().toString());
        adapter.update(lists);
    }
}