package com.example.quiz_four;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apartments.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_APARTMENTS = "apartments";
    
    private static final String COLUMN_ID = "apartment_id";
    private static final String COLUMN_UNIT_NUMBER = "unit_number";
    private static final String COLUMN_SQUARE_FOOTAGE = "square_footage";
    private static final String COLUMN_RENT_AMOUNT = "rent_amount";
    private static final String COLUMN_IS_AIRBNB = "is_airbnb";
    private static final String COLUMN_ALLOWS_PETS = "allows_pets";
    private static final String COLUMN_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_APARTMENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UNIT_NUMBER + " TEXT NOT NULL, " +
                COLUMN_SQUARE_FOOTAGE + " REAL, " +
                COLUMN_RENT_AMOUNT + " REAL, " +
                COLUMN_IS_AIRBNB + " INTEGER, " +
                COLUMN_ALLOWS_PETS + " INTEGER, " +
                COLUMN_LOCATION + " TEXT" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APARTMENTS);
        onCreate(db);
    }

    // CREATE - Add new apartment
    public long addApartment(Apartment apartment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_UNIT_NUMBER, apartment.getUnitNumber());
        values.put(COLUMN_SQUARE_FOOTAGE, apartment.getSquareFootage());
        values.put(COLUMN_RENT_AMOUNT, apartment.getRentAmount());
        values.put(COLUMN_IS_AIRBNB, apartment.getIsAirBnb() ? 1 : 0);
        values.put(COLUMN_ALLOWS_PETS, apartment.getAllowsPets() ? 1 : 0);
        values.put(COLUMN_LOCATION, apartment.getLocation());
        
        long id = db.insert(TABLE_APARTMENTS, null, values);
        db.close();
        return id;
    }

    // READ - Get all apartments
    public List<Apartment> getAllApartments() {
        List<Apartment> apartmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APARTMENTS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Apartment apartment = new Apartment();
                apartment.setApartmentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                apartment.setUnitNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT_NUMBER)));
                apartment.setSquareFootage(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SQUARE_FOOTAGE)));
                apartment.setRentAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RENT_AMOUNT)));
                apartment.setIsAirBnb(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_AIRBNB)) == 1);
                apartment.setAllowsPets(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALLOWS_PETS)) == 1);
                apartment.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
                
                apartmentList.add(apartment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return apartmentList;
    }

    // READ - Get apartment by ID
    public Apartment getApartmentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APARTMENTS, null, COLUMN_ID + "=?", 
                new String[]{String.valueOf(id)}, null, null, null);
        
        Apartment apartment = null;
        if (cursor.moveToFirst()) {
            apartment = new Apartment();
            apartment.setApartmentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            apartment.setUnitNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT_NUMBER)));
            apartment.setSquareFootage(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SQUARE_FOOTAGE)));
            apartment.setRentAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RENT_AMOUNT)));
            apartment.setIsAirBnb(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_AIRBNB)) == 1);
            apartment.setAllowsPets(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALLOWS_PETS)) == 1);
            apartment.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
        }
        cursor.close();
        db.close();
        return apartment;
    }

    // UPDATE - Update apartment
    public int updateApartment(Apartment apartment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_UNIT_NUMBER, apartment.getUnitNumber());
        values.put(COLUMN_SQUARE_FOOTAGE, apartment.getSquareFootage());
        values.put(COLUMN_RENT_AMOUNT, apartment.getRentAmount());
        values.put(COLUMN_IS_AIRBNB, apartment.getIsAirBnb() ? 1 : 0);
        values.put(COLUMN_ALLOWS_PETS, apartment.getAllowsPets() ? 1 : 0);
        values.put(COLUMN_LOCATION, apartment.getLocation());
        
        int rowsAffected = db.update(TABLE_APARTMENTS, values, COLUMN_ID + "=?", 
                new String[]{String.valueOf(apartment.getApartmentId())});
        db.close();
        return rowsAffected;
    }

    // DELETE - Delete apartment
    public int deleteApartment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_APARTMENTS, COLUMN_ID + "=?", 
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
}
