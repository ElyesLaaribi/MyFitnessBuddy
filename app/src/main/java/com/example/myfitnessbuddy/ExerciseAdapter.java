package com.example.myfitnessbuddy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private final List<Exercise> exerciseList;
    private final OnExerciseClickListener clickListener;

    // Constructor
    public ExerciseAdapter(List<Exercise> exerciseList, OnExerciseClickListener clickListener) {
        this.exerciseList = exerciseList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercice, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getName());

        // Set click listener for the button
        holder.actionButton.setOnClickListener(v -> clickListener.onExerciseClick(exercise.getName()));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        Button actionButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            actionButton = itemView.findViewById(R.id.actionButton);
        }
    }

    // Listener interface for button clicks
    public interface OnExerciseClickListener {
        void onExerciseClick(String exerciseName);
    }
}