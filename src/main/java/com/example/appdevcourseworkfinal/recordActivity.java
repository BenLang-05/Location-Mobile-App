package com.example.appdevcourseworkfinal;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class recordActivity extends AppCompatActivity {

    public static final String extraLocation = "locationID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_record);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int locationID =(Integer)getIntent().getExtras().get(extraLocation);


        SQLiteOpenHelper Database = new database(this);
        try{
            SQLiteDatabase Db = Database.getReadableDatabase();
            Cursor cursor = Db.query("PLACES",
                    new String[] {"_id","LOCATION", "COUNTRY", "LATITUDE", "LONGITUDE", "RATING", "DATE"},
                    "_id = ?",
                    new String[] {Integer.toString(locationID)},
                    null, null, null);

            if (cursor.moveToFirst()){
                String locationText = cursor.getString(1);
                String countryText = cursor.getString(2);
                String latitudeText = cursor.getString(3);
                String longitudeText = cursor.getString(4);
                float ratingText = cursor.getFloat(5);
                String dateText = cursor.getString(6);

                TextView location = (TextView)findViewById(R.id.textView_getlocation);
                location.setText(locationText);

                TextView country = (TextView)findViewById(R.id.textView_getcountry);
                country.setText(countryText);

                TextView latitude = (TextView)findViewById(R.id.textView_getlatitude);
                latitude.setText(latitudeText);

                TextView longitude = (TextView)findViewById(R.id.textView_getlongitude);
                longitude.setText(longitudeText);

                TextView rating = (TextView)findViewById(R.id.textView_getrating);
                rating.setText((new Float(ratingText).toString()));

                TextView date = (TextView)findViewById(R.id.textView_getdate);
                date.setText(dateText);
            }
            cursor.close();
            Db.close();

        } catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }





    }

}
