package com.example.myfitnessbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import android.widget.Button;

public class FoodActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view first
        setContentView(R.layout.activity_food);

        // Find buttons in the layout
        Button addBreakfastButton = findViewById(R.id.addBreakfastButton);
        Button addLunchButton = findViewById(R.id.addLunchButton);
        Button addDinnerButton = findViewById(R.id.addDinnerButton);

        // Enable edge-to-edge functionality
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the TabLayout and handle tab selection
        tabLayout = findViewById(R.id.tabLayout);
        int selectedTabPosition = getIntent().getIntExtra("selected_tab_position", 0);

        // Safely select the tab if it exists
        TabLayout.Tab tab = tabLayout.getTabAt(selectedTabPosition);
        if (tab != null) {
            tab.select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                Intent intent = null;

                // Handle navigation based on the selected tab
                switch (selectedTabPosition) {
                    case 0:
                        intent = new Intent(FoodActivity.this, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(FoodActivity.this, FoodActivity.class);
                        break;
                    case 2:
                        intent = new Intent(FoodActivity.this, StepsActivity.class);
                        break;
                    case 3:
                        intent = new Intent(FoodActivity.this, SleepActivity.class);
                        break;
                    case 4:
                        intent = new Intent(FoodActivity.this, ExercisesActivity.class);
                        break;
                }

                if (intent != null) {
                    intent.putExtra("selected_tab_position", selectedTabPosition);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
                // No action needed here
            }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {
                // No action needed here
            }
        });

        // Set click listeners for the meal buttons
        addBreakfastButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodActivity.this, AddMealActivity.class);
            intent.putExtra("mealType", "Breakfast");
            startActivity(intent);
        });

        addLunchButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodActivity.this, AddMealActivity.class);
            intent.putExtra("mealType", "Lunch");
            startActivity(intent);
        });

        addDinnerButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodActivity.this, AddMealActivity.class);
            intent.putExtra("mealType", "Dinner");
            startActivity(intent);
        });
    }
}
