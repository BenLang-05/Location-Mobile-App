package com.example.appdevcourseworkfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    private static final String DB_name = "locations";

    private static final int DB_version = 2;


    public  database(Context c){
        super(c, DB_name,null,DB_version);
    }
    @Override
    public void onCreate(SQLiteDatabase Db){

        updateMyDatabase(Db,0,DB_version);

    }
    @Override
    public void onUpgrade (SQLiteDatabase Db, int oldVersion, int newVersion){

        updateMyDatabase(Db,oldVersion,newVersion);

    }
    public static void insertLocation(SQLiteDatabase Db,
                                      String Location,
                                      String Country,
                                      String Latitude,
                                      String Longitude,
                                      float Rating,
                                      String Date){
        ContentValues values = new ContentValues();
        values.put("LOCATION", Location);
        values.put("COUNTRY",Country);
        values.put("LATITUDE",Latitude);
        values.put("LONGITUDE",Longitude);
        values.put("RATING",Rating);
        values.put("DATE",Date);
        Db.insert("PLACES", null, values);



    }

    private static void updateMyDatabase(SQLiteDatabase Db, int oldVersion, int newVersion){
        if(oldVersion < 1){

            Db.execSQL("CREATE TABLE PLACES ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "LOCATION TEXT, "
                    + "COUNTRY TEXT, "
                    + "LATITUDE TEXT, "
                    + "LONGITUDE TEXT, "
                    + "RATING REAL, "
                    + "DATE TEXT);");


        }
        if(oldVersion < 2){
            Db.execSQL("ALTER TABLE PLACES ADD COLUMN DESCRIPTION TEXT; ");
        }
    }

}

