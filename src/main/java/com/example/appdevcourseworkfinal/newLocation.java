package com.example.appdevcourseworkfinal;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class newLocation extends AppCompatActivity {


    EditText editText_place, editText_country, editText_latitude, editText_longitude;

    boolean newPlace;
    private ImageButton pickDate;
    private TextView selectedDate;
    RatingBar rBar;
    long item_index;
    private SQLiteDatabase DB;
    public int ID = 2;

    

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlocation_details);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText_place = (EditText)findViewById(R.id.editText_place);
        editText_country = (EditText)findViewById(R.id.editText_country);
        editText_latitude = (EditText)findViewById(R.id.editText_latitude);
        editText_longitude = (EditText)findViewById(R.id.editText_longitude);



        pickDate = findViewById(R.id.button_date);
        selectedDate = findViewById(R.id.textView_selectDate);
        pickDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(

                        newLocation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        rBar = (RatingBar) findViewById(R.id.ratingBar_rating);
        LayerDrawable stars=(LayerDrawable) rBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        item_index = intent.getLongExtra("item_index", -1);

        if(item_index == -1){
            newPlace = true;
        } else{

            newPlace = false;
        }


    }
    public void Call(View v){
        TextView text = (TextView) findViewById(R.id.rating_result);
        String r = String.valueOf(rBar.getRating()*2);

        text.setText("You rated : " + r + "/10");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.place_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (item.getItemId() == R.id.save_place) {

            if (newPlace) {

                if(editText_place.getText().toString().isEmpty()
                        || editText_country.getText().toString().isEmpty()
                        || editText_latitude.getText().toString().isEmpty()
                        || editText_longitude.getText().toString().isEmpty()
                        || rBar.getRating() == 0
                        || selectedDate.getText().toString().isEmpty()){
                    Toast.makeText(newLocation.this, "Please fill in all details!", Toast.LENGTH_SHORT).show();

                }
                else {

                    /////////////////////////////////////////////////////////////////////////////////////////////////////////

                    SQLiteOpenHelper Database = new database(this);
                    try{
                        DB = Database.getReadableDatabase();
                        database.insertLocation(DB,
                                editText_place.getText().toString(),
                                editText_country.getText().toString(),
                                editText_latitude.getText().toString(),
                                editText_longitude.getText().toString(),
                                Float.parseFloat(String.valueOf(rBar.getRating()*2)),
                                selectedDate.getText().toString());



                    }  catch(SQLException e){
                        Toast toast = Toast.makeText(this, "Location Added", Toast.LENGTH_SHORT);
                        toast.show();

                    }

                    finish();
                    DB.close();

                }
            }else{

                finish();

            }

            return true;

        }else{
            return super.onOptionsItemSelected(item);

        }
    }
}





