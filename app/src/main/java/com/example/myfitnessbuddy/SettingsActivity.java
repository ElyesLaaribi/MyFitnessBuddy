package com.example.myfitnessbuddy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextName, editTextHeight, editTextWeight, editTextDOB, editTextCaloriesGoal;
    private Spinner spinnerGender;
    private Button btnValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        editTextName = findViewById(R.id.editTextName);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextCaloriesGoal = findViewById(R.id.editTextCaloriesGoal);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnValidate = findViewById(R.id.btnValidate);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);


        btnValidate.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String height = editTextHeight.getText().toString();
            String weight = editTextWeight.getText().toString();
            String gender = spinnerGender.getSelectedItem().toString();
            String dob = editTextDOB.getText().toString();
            String caloriesGoal = editTextCaloriesGoal.getText().toString();


            System.out.println("Name: " + name);
            System.out.println("Gender: " + gender);
        });
    }
}
