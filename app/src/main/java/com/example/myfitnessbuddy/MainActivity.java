package com.example.myfitnessbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
    private TextView BaseGoalTextView;
    private SQLiteHelper dbHelper;
    private TabLayout tabLayout;
    private ImageButton myImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        caloriesGoalTextView = findViewById(R.id.caloriesGoalTextView);
        BaseGoalTextView = findViewById(R.id.BaseGoal);

        // Initialize the database helper
        dbHelper = new SQLiteHelper(this);

        // Load calorie goal and update the UI
        loadCalorieGoal();

        // Set up edge-to-edge window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        myImageButton = findViewById(R.id.circular_button);

        // Handle ImageButton click to open SettingsActivity
        myImageButton.setOnClickListener(v -> {
            Intent imageButtonIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(imageButtonIntent);
        });

        // Handle tab selection to switch between activities
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
    }

    /**
     * Method to load the calorie goal from the SQLite database and update the TextView.
     */
    private void loadCalorieGoal() {
        Cursor cursor = dbHelper.getCalorieData();

        if (cursor.moveToFirst()) {
            @SuppressLint("Range")
            int baseGoal = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_BASE_GOAL));
            caloriesGoalTextView.setText(String.valueOf(baseGoal));
            BaseGoalTextView.setText(String.valueOf(baseGoal));
        } else {
            caloriesGoalTextView.setText("Not Set"); // Default text if no data is found
        }

        cursor.close();
    }
}
