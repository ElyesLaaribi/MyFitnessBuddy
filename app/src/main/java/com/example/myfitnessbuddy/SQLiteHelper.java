package com.example.myfitnessbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitnessBuddy.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Columns
    public static final String TABLE_CALORIES = "calorie_data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BASE_GOAL = "base_goal";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_EXERCISE = "exercise";

    // SQL to create the table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_CALORIES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BASE_GOAL + " INTEGER, " +
            COLUMN_FOOD + " INTEGER, " +
            COLUMN_EXERCISE + " INTEGER);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALORIES);
        onCreate(db);
    }

    // Insert or update calorie goal
    public void insertOrUpdateCalorieGoal(int baseGoal) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if data exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CALORIES, null);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BASE_GOAL, baseGoal);
            db.update(TABLE_CALORIES, values, COLUMN_ID + " = ?", new String[]{"1"});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BASE_GOAL, baseGoal);
            values.put(COLUMN_FOOD, 0); // Default food value
            values.put(COLUMN_EXERCISE, 0); // Default exercise value
            db.insert(TABLE_CALORIES, null, values);
        }
        cursor.close();
    }

    // Retrieve calorie data
    public Cursor getCalorieData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CALORIES, null);
    }
}
