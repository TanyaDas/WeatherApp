package com.tanya.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Information
    private static final String DB_NAME = "weather_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER_ADMIN = "user_admin";
    private static final String TABLE_USER_LIST = "user_list_details";
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_FIRSTNAME = "first_name";
    private static final String KEY_LASTNAME = "last_name";
    private static final String KEY_EMAIL = "email_id";
    private static final String CREATE_TABLE_ADMINS = "CREATE TABLE " + TABLE_USER_ADMIN + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_EMAIL + " TEXT," +
            KEY_PASSWORD + " TEXT" +
            ")";
    private static final String CREATE_TABLE_USER_LIST = "CREATE TABLE " + TABLE_USER_LIST + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_FIRSTNAME + " TEXT," +
            KEY_LASTNAME + " TEXT," +
            KEY_EMAIL + " TEXT" + ")";
    private static final String READ_ALL_USERS = "SELECT * FROM " + TABLE_USER_LIST;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public float addNewAdminUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(KEY_EMAIL, email);
        contentValue.put(KEY_PASSWORD, password);
        try {
            float result = db.insert(TABLE_USER_ADMIN, null, contentValue);
            return result;
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error inserting data: " + e.getMessage());
            return -1;
        } finally {
            //   db.close();
        }
    }

    public float addUserForWeather(String email, String firstname, String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(KEY_EMAIL, email);
        contentValue.put(KEY_FIRSTNAME, firstname);
        contentValue.put(KEY_LASTNAME, lastname);
        try {
            float result = db.insert(TABLE_USER_LIST, null, contentValue);
            return result;
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error inserting data: " + e.getMessage());
            return -1;
        } finally {
            //   db.close();
        }
    }

    public ArrayList<UserModal> readAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(READ_ALL_USERS, null);
        ArrayList<UserModal> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int firstNameIndex = cursor.getColumnIndex(KEY_FIRSTNAME);
            int lastNameIndex = cursor.getColumnIndex(KEY_LASTNAME);
            int emailIndex = cursor.getColumnIndex(KEY_EMAIL);
            do {
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String email = cursor.getString(emailIndex);
                arrayList.add(new UserModal(email, firstName, lastName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        return arrayList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADMINS);
        db.execSQL(CREATE_TABLE_USER_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_ADMINS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + CREATE_TABLE_USER_LIST + "'");
        onCreate(db);
    }
}
