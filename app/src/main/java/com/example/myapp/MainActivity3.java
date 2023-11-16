package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = findViewById(R.id.editText);
        Button button3 = findViewById(R.id.Button3);
        Button AddButton = findViewById(R.id.AddButton2);
        ListView WorkoutList = findViewById(R.id.WorkoutList);
        Button BackButton = findViewById(R.id.BackButton3);
        Button AddButtonTime = findViewById(R.id.AddButtonTime);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                    startActivity(intent);
                    WorkoutAdapter.exercises.clear();
                    finish();
                }catch(Exception e){
                }
            }
        });
        WorkoutList.setItemsCanFocus(true);
        WorkoutAdapter adapter = new WorkoutAdapter(this);
        WorkoutList.setAdapter(adapter);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutAdapter.exercises.add(new ListItem(1));
                adapter.notifyDataSetChanged();
            }
            });
        AddButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutAdapter.exercises.add(new ListItem(2));
                adapter.notifyDataSetChanged();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Workout> workoutList= new ArrayList<Workout>();
                String name = editText.getText().toString();
                int num=0;
                while(num < WorkoutAdapter.exercises.size()){
                    ListItem exercise = WorkoutAdapter.exercises.get(num);
                    switch (exercise.getItemViewType()) {
                        case 1:
                            Workout workout = new Workout(exercise.nameExercise, exercise.NumReps, exercise.NumSets, exercise.restime,1);
                            workoutList.add(workout);
                            break;
                        case 2:
                            Workout workout1 = new Workout(exercise.nameExercise, exercise.RestTime, exercise.timeType,2);
                            workoutList.add(workout1);
                            break;
                    }
                    num++;
                }
                Intent intent2 = new Intent(".MainActivity2");
                intent2.putExtra("Workout",workoutList);
                intent2.putExtra("name", name);
                startActivity(intent2);
                WorkoutAdapter.exercises.clear();
            }
        });


    }
}