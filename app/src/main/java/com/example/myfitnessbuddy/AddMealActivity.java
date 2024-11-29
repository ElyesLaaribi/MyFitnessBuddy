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

        // Retrieve the meal type passed from the previous activity (if any)
        String mealType = getIntent().getStringExtra("mealType");

        // Set the title dynamically based on meal type
        TextView title = findViewById(R.id.title);
        title.setText("Add " + (mealType != null ? mealType : "Meal"));

        // Get references to input fields and button
        EditText mealName = findViewById(R.id.mealName);
        EditText mealCalories = findViewById(R.id.mealCalories);
        Button saveMealButton = findViewById(R.id.saveMealButton);

        // Save button logic
        saveMealButton.setOnClickListener(v -> {
            String name = mealName.getText().toString();
            String calories = mealCalories.getText().toString();

            if (!name.isEmpty() && !calories.isEmpty()) {
                int calorieValue = Integer.parseInt(calories);

                // Save the meal calories to the database
                SQLiteHelper dbHelper = new SQLiteHelper(AddMealActivity.this);
                dbHelper.addFoodCalories(calorieValue);

                // Return result to MainActivity
                Intent intent = new Intent();
                intent.putExtra("mealName", name);
                intent.putExtra("mealCalories", calorieValue);
                setResult(RESULT_OK, intent);

                finish(); // Close the activity
            }
        });
    }
}
