package com.example.myfitnessbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TextView caloriesGoalTextView;
    private TextView CaloriesGoalTextView2;
    private TextView BaseGoalTextView;
    private TextView timeTextView;
    private SQLiteHelper dbHelper;
    private TabLayout tabLayout;
    private ImageButton myImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        caloriesGoalTextView = findViewById(R.id.caloriesGoalTextView);
        CaloriesGoalTextView2 = findViewById(R.id.caloriesBurnedTextView2);
        BaseGoalTextView = findViewById(R.id.BaseGoal);
        timeTextView = findViewById(R.id.time);  // Initialize the TextView for time
        dbHelper = new SQLiteHelper(this);

        // Load initial calorie goal and time data
        loadCalorieGoal();
        loadTotalCaloriesBurned();
        loadTotalTimeConsumed();  // Load total time consumed

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize tab layout and image button
        tabLayout = findViewById(R.id.tabLayout);
        myImageButton = findViewById(R.id.circular_button);
        Button resetButton = findViewById(R.id.resetButton);

        // Handle image button click to navigate to SettingsActivity
        myImageButton.setOnClickListener(v -> {
            Intent imageButtonIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(imageButtonIntent);
        });

        // Handle tab layout navigation
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                Intent intent = null;

                switch (selectedTabPosition) {
                    case 0:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, FoodActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, StepsActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, SleepActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, ExercisesActivity.class);
                        break;
                }

                if (intent != null) {
                    intent.putExtra("selected_tab_position", selectedTabPosition);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {
            }
        });

        resetButton.setOnClickListener(v -> {
            try {
                dbHelper.resetDatabase();
                Log.d("ResetButton", "Database reset successfully");
                loadCalorieGoal();
                loadTotalCaloriesBurned();
                loadTotalTimeConsumed();
                loadTotalFoodCalories();
            } catch (Exception e) {
                Log.e("ResetButton", "Error resetting database", e);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCalorieGoal();
        loadTotalCaloriesBurned();
        loadTotalTimeConsumed();
        loadTotalFoodCalories();
        // Reload total time consumed
    }

    private void loadCalorieGoal() {
        Cursor cursor = dbHelper.getCalorieData();

        if (cursor.moveToFirst()) {
            @SuppressLint("Range")
            int baseGoal = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_BASE_GOAL));
            int totalFoodCalories = dbHelper.getTotalFoodCalories();
            int totalCaloriesBurned = dbHelper.getTotalCaloriesBurned();
            int remainingCalories = baseGoal - totalFoodCalories + totalCaloriesBurned;

            // Update the TextView with remaining calories
            caloriesGoalTextView.setText(String.valueOf(remainingCalories));

            // Optionally, update other TextViews
            CaloriesGoalTextView2.setText(String.valueOf(totalCaloriesBurned));
            BaseGoalTextView.setText(baseGoal == 0 ? "Set your goal" : String.valueOf(baseGoal));

            // Update the progress bar, ensuring baseGoal is not zero
            ProgressBar progressBar = findViewById(R.id.progressCirc);
            int progress = 0;
            if (baseGoal != 0) {
                progress = (baseGoal - remainingCalories) * 100 / baseGoal;
            }
            progressBar.setProgress(Math.max(0, Math.min(progress, 100))); // Ensure progress stays within 0-100%
        } else {
            // Default values if no data exists
            caloriesGoalTextView.setText("0 Kcal");
            CaloriesGoalTextView2.setText("0 Kcal");
            BaseGoalTextView.setText("Set your goal");

            // Set progress bar to 0
            ProgressBar progressBar = findViewById(R.id.progressCirc);
            progressBar.setProgress(0);
        }

        cursor.close();
    }



    private void loadTotalCaloriesBurned() {
        int totalCaloriesBurned = dbHelper.getTotalCaloriesBurned();

        TextView caloriesBurnedTextView = findViewById(R.id.caloriesBurnedTextView);
        caloriesBurnedTextView.setText(String.valueOf(totalCaloriesBurned));

        TextView caloriesBurnedTextView2 = findViewById(R.id.caloriesBurnedTextView2);
        caloriesBurnedTextView2.setText(String.valueOf(totalCaloriesBurned));
    }

    @SuppressLint("SetTextI18n")
    private void loadTotalTimeConsumed() {
        int totalTimeConsumed = dbHelper.getTotalTimeConsumed();  // Get total time consumed

        timeTextView.setText(totalTimeConsumed + " mins");  // Set the time in the TextView
    }

    @SuppressLint("SetTextI18n")
    private void loadTotalFoodCalories() {
        int totalFoodCalories = dbHelper.getTotalFoodCalories();
        TextView foodCaloriesTextView = findViewById(R.id.foodCaloriesTextView); // Link this to your layout
        foodCaloriesTextView.setText(totalFoodCalories + " Kcal");
    }
}