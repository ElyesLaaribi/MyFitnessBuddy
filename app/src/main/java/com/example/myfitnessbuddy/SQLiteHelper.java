package com.example.myfitnessbuddy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitnessBuddy.db";
    private static final int DATABASE_VERSION = 3; // Incremented to 3 for adding the timeConsumed column

    // Table and Columns
    public static final String TABLE_CALORIES = "calorie_data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BASE_GOAL = "base_goal";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_EXERCISE = "exercise";

    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "id";
    public static final String COLUMN_EXERCISE_NAME = "name";
    public static final String COLUMN_CALORIES_BURNED = "calories_burned";
    public static final String COLUMN_TIME_CONSUMED = "time_consumed";  // New column

    // SQL to create tables
    private static final String CREATE_CALORIE_TABLE = "CREATE TABLE " + TABLE_CALORIES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BASE_GOAL + " INTEGER, " +
            COLUMN_FOOD + " INTEGER, " +
            COLUMN_EXERCISE + " INTEGER);";

    private static final String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + " (" +
            COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EXERCISE_NAME + " TEXT, " +
            COLUMN_CALORIES_BURNED + " INTEGER, " +
            COLUMN_TIME_CONSUMED + " INTEGER);";  // Modified to include timeConsumed

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(CREATE_CALORIE_TABLE);
        db.execSQL(CREATE_EXERCISES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic: Drop and recreate both tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

    // Insert or update calorie goal
    public void insertOrUpdateCalorieGoal(int calorieGoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BASE_GOAL, calorieGoal);  // Insert or update calorie goal

        // Check if base goal already exists
        int rowsAffected = db.update(TABLE_CALORIES, values, null, null);
        if (rowsAffected == 0) {
            // If no rows were updated, insert the value
            db.insert(TABLE_CALORIES, null, values);
        }
        db.close();
    }


    // Insert exercise with calories burned and time consumed
    public void insertExercise(String name, int caloriesBurned, int timeConsumed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, name);
        values.put(COLUMN_CALORIES_BURNED, caloriesBurned);
        values.put(COLUMN_TIME_CONSUMED, timeConsumed);  // Insert time consumed
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    // Add food calories
    public void addFoodCalories(int calories) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update the food column in the calorie_data table
        db.execSQL("UPDATE " + TABLE_CALORIES + " SET " + COLUMN_FOOD + " = " +
                COLUMN_FOOD + " + " + calories);
        db.close();
    }

    // Retrieve calorie data
    public Cursor getCalorieData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CALORIES, null);
    }

    // Get total calories burned
    @SuppressLint("Range")
    public int getTotalCaloriesBurned() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_CALORIES_BURNED + ") AS total FROM " + TABLE_EXERCISES, null);
        int totalCalories = 0;
        if (cursor.moveToFirst()) {
            totalCalories = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();
        return totalCalories;
    }

    // Get total time consumed
    @SuppressLint("Range")
    public int getTotalTimeConsumed() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TIME_CONSUMED + ") AS total FROM " + TABLE_EXERCISES, null);
        int totalTime = 0;
        if (cursor.moveToFirst()) {
            totalTime = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();
        return totalTime;
    }

    // Optional: Clear all exercises (useful for debugging or resetting data)
    public void clearExercises() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXERCISES);
        db.close();
    }

    // Add the method to get total food calories
    @SuppressLint("Range")
    public int getTotalFoodCalories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_FOOD + ") AS total FROM " + TABLE_CALORIES, null);
        int totalCalories = 0;
        if (cursor.moveToFirst()) {
            totalCalories = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();
        return totalCalories;
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Clear both tables
            db.execSQL("DELETE FROM " + TABLE_CALORIES);
            db.execSQL("DELETE FROM " + TABLE_EXERCISES);

            // Remove the default calorie data
            ContentValues values = new ContentValues();
            values.put(COLUMN_BASE_GOAL, 0); // Set to 0 initially to require user input
            values.put(COLUMN_FOOD, 0);      // Default food calories
            values.put(COLUMN_EXERCISE, 0);  // Default exercise calories
            db.insert(TABLE_CALORIES, null, values);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to debug
        } finally {
            db.close(); // Always close the database
        }
    }




}
