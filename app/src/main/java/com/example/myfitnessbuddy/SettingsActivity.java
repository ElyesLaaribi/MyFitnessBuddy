package com.example.myfitnessbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextCaloriesGoal;
    private Button btnValidate;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextCaloriesGoal = findViewById(R.id.editTextCaloriesGoal);
        btnValidate = findViewById(R.id.btnValidate);

        dbHelper = new SQLiteHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnValidate.setOnClickListener(v -> {
            String calorieGoalInput = editTextCaloriesGoal.getText().toString();

            if (!calorieGoalInput.isEmpty()) {
                int calorieGoal = Integer.parseInt(calorieGoalInput);

                // Save calorie goal to database
                dbHelper.insertOrUpdateCalorieGoal(calorieGoal);

                // Pass the updated calorie goal back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_calorie_goal", calorieGoal);
                setResult(RESULT_OK, resultIntent);

                Toast.makeText(this, "Calorie goal saved!", Toast.LENGTH_SHORT).show();
                finish(); // Close the settings screen
            } else {
                Toast.makeText(this, "Please enter a valid calorie goal.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
