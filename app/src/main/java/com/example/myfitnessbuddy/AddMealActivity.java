package com.example.myfitnessbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AddMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        // Retrieve the meal type passed from the FoodActivity
        String mealType = getIntent().getStringExtra("mealType");

        // Set the title dynamically based on meal type
        TextView title = findViewById(R.id.title);
        title.setText("Add " + mealType);

        // Get references to input fields and button
        EditText mealName = findViewById(R.id.mealName);
        EditText mealCalories = findViewById(R.id.mealCalories);
        Button saveMealButton = findViewById(R.id.saveMealButton);

        // Save button logic (you can extend this as needed to save data)
        saveMealButton.setOnClickListener(v -> {
            String name = mealName.getText().toString();
            String calories = mealCalories.getText().toString();

            // You can save this data to a database or pass it back to FoodActivity
            // For now, just log or toast the input
            if (!name.isEmpty() && !calories.isEmpty()) {
                // Example of handling the input
                Intent intent = new Intent();
                intent.putExtra("mealName", name);
                intent.putExtra("mealCalories", calories);
                setResult(RESULT_OK, intent);
                finish(); // Close the activity and return to FoodActivity
            }
        });
    }
}