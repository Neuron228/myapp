package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CreatedWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_workout);

        Bundle arguments = getIntent().getExtras();
        TextView nameWorkout = findViewById(R.id.nameWorkout);
        int Position = arguments.getInt("NamePosition");
        ListView WorkoutList = findViewById(R.id.WorkoutList2);
        Button BackButton = findViewById(R.id.BackButton4);
        Button StartButton = findViewById(R.id.start);

        nameWorkout.setText(WorkoutFragment.list.get(Position));
        ArrayList<Workout> workout = WorkoutFragment.AllWorkoutsList.get(Position);
        WorkoutList.setItemsCanFocus(true);
        WorkoutAdapter2 adapter2 = new WorkoutAdapter2(this,workout);
        WorkoutList.setAdapter(adapter2);


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
            });

    }
}