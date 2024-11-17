package com.example.myfitnessbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class ExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercises);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press"));
        exerciseList.add(new Exercise("Deadlift"));
        exerciseList.add(new Exercise("Squats"));
        exerciseList.add(new Exercise("Pull Ups"));
        exerciseList.add(new Exercise("Push Ups"));
        exerciseList.add(new Exercise("Lunges"));
        exerciseList.add(new Exercise("Bicep Curl"));
        exerciseList.add(new Exercise("Tricep Dips"));
        exerciseList.add(new Exercise("Plank"));
        exerciseList.add(new Exercise("Burpees"));
        // Set up adapter
        ExerciseAdapter adapter = new ExerciseAdapter(exerciseList, exerciseName ->
                Toast.makeText(this, "You clicked: " + exerciseName, Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get data
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        int selectedTabPosition = getIntent().getIntExtra("selected_tab_position", 0);
        tabLayout.getTabAt(selectedTabPosition).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                // Pass the selected tab position to the next activity
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
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {
            }
        });
    }
}