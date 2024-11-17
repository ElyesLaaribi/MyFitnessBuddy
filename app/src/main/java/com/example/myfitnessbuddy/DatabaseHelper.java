package com.example.myfitnessbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "fitnessbuddy.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_NAME = "settings";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_CALORIES_GOAL = "calories_goal";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_NAME + " TEXT, "
                + COLUMN_HEIGHT + " TEXT, "
                + COLUMN_WEIGHT + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_DOB + " TEXT, "
                + COLUMN_CALORIES_GOAL + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrade table if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create the table again
        onCreate(db);
    }

    // Method to save settings into the database
    public boolean saveSettings(String name, String height, String weight, String gender, String dob, String caloriesGoal) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert values into ContentValues
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_CALORIES_GOAL, caloriesGoal);

        // Insert the data into the table and return the result
        long result = db.insert(TABLE_NAME, null, values);
        db.close();  // Close the database connection
        return result != -1;  // Return true if insert was successful
    }

    // Method to retrieve settings from the database
    public Cursor getSettings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";  // Retrieve only the first record
        return db.rawQuery(query, null);
    }
}
