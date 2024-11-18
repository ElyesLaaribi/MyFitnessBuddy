package com.example.myfitnessbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        dbHelper = new SQLiteHelper(this); // Initialize SQLiteHelper

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add exercises with calories burned and time consumed (5 minutes for each)
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press", 100, 5));  // 5 minutes
        exerciseList.add(new Exercise("Deadlift", 100, 5));     // 5 minutes
        exerciseList.add(new Exercise("Squats", 100, 5));       // 5 minutes
        exerciseList.add(new Exercise("Pull Ups", 100, 5));     // 5 minutes
        exerciseList.add(new Exercise("Push Ups", 100, 5));     // 5 minutes
        exerciseList.add(new Exercise("Lunges", 100, 5));       // 5 minutes
        exerciseList.add(new Exercise("Bicep Curl", 100, 5));   // 5 minutes
        exerciseList.add(new Exercise("Tricep Dips", 100, 5));  // 5 minutes
        exerciseList.add(new Exercise("Plank", 100, 5));        // 5 minutes
        exerciseList.add(new Exercise("Burpees", 100, 5));      // 5 minutes

        // Set up adapter
        ExerciseAdapter adapter = new ExerciseAdapter(exerciseList, exerciseName -> {
            for (Exercise exercise : exerciseList) {
                if (exercise.getName().equals(exerciseName)) {
                    // Save calories burned and time consumed to SQLite
                    dbHelper.insertExercise(exercise.getName(), exercise.getCaloriesBurned(), exercise.getTimeConsumed());
                    Toast.makeText(this, String.format("Calories burned: %d, Time consumed: %d mins", exercise.getCaloriesBurned(), exercise.getTimeConsumed()), Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });

        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        int selectedTabPosition = getIntent().getIntExtra("selected_tab_position", 0);
        tabLayout.getTabAt(selectedTabPosition).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                Intent intent = null;
                switch (selectedTabPosition) {
                    case 0:
                        intent = new Intent(ExercisesActivity.this, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ExercisesActivity.this, FoodActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ExercisesActivity.this, StepsActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ExercisesActivity.this, SleepActivity.class);
                        break;
                    case 4:
                        intent = new Intent(ExercisesActivity.this, ExercisesActivity.class);
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
}
