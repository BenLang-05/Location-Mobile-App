package com.example.appdevcourseworkfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase DB;
    private Cursor cursor;
    ListView locationsList;

    String orderSTR = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        locationsList = findViewById(R.id.list_of_places);


        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> locationsList,
                                    View itemView,
                                    int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this,
                        recordActivity.class);
                intent.putExtra(recordActivity.extraLocation, (int) id);
                startActivity(intent);

            }
        };
        locationsList.setOnItemClickListener(itemClickListener);
    }
    public void applyOrder(View view){
        Spinner order = (Spinner)findViewById(R.id.order);
        String outcome = String.valueOf(order.getSelectedItem());

        switch(outcome){

            case "Location A-Z":{
                orderSTR = "";
                orderSTR = "LOCATION ASC";
                break;
            }
            case "Location Z-A":{
                orderSTR = "";
                orderSTR = "LOCATION DESC";
                break;
            }
            case "Rating High-Low":{
                orderSTR = "";
                orderSTR = "RATING DESC";
                break;
            }
            case "Rating Low-High":{
                orderSTR = "";
                orderSTR = "RATING ASC";
                break;
            }
            case "Country":{
                orderSTR = "";
                orderSTR = "COUNTRY ASC";
                break;
            }
            default:{
                orderSTR = "";
                orderSTR = "LOCATION ASC";

            }

        }
        refreshList();
    }


    @Override
    public void onStart() {


        super.onStart();
        refreshList();
    }

    public void refreshList() {


        SQLiteOpenHelper Database = new database(this);
        try {
            DB = Database.getReadableDatabase();

            cursor = DB.query("PLACES",
                    new String[]{"_id", "LOCATION"},
                    null, null, null, null, orderSTR);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"LOCATION"},
                    new int[]{android.R.id.text1},
                    0);
            locationsList.setAdapter(listAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Try again ", Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        DB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addNew) {
            startActivity(new Intent(this, newLocation.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}
