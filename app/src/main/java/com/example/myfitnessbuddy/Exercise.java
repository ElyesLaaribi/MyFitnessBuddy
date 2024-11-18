package com.example.myfitnessbuddy;

public class Exercise {
    private String name;
    private int caloriesBurned;
    private int timeConsumed;

    // Constructor to initialize name, calories burned, and time consumed
    public Exercise(String name, int caloriesBurned, int timeConsumed) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
        this.timeConsumed = timeConsumed;
    }

    // Getter and Setter for Exercise name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter for calories burned
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    // Getter for time consumed
    public int getTimeConsumed() {
        return timeConsumed;
    }

    // Setter for time consumed
    public void setTimeConsumed(int timeConsumed) {
        this.timeConsumed = timeConsumed;
    }
}
