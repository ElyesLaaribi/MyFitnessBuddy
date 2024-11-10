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

public class FoodActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get data
        tabLayout = findViewById(R.id.tabLayout);
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
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {
            }
        });


    }
}