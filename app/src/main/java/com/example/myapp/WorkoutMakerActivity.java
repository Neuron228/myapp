package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class WorkoutMakerActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    DatabaseReference mDatabaseW;

    String Nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_maker);

        EditText editText = findViewById(R.id.editText);
        Button button3 = findViewById(R.id.Button3);
        Button AddButton = findViewById(R.id.AddButton2);
        ListView WorkoutList = findViewById(R.id.WorkoutList);
        Button BackButton = findViewById(R.id.BackButton3);
        Button AddButtonTime = findViewById(R.id.AddButtonTime);


        Nickname = MainActivity.Nickname;

        database = FirebaseDatabase.getInstance();

        mDatabase = database.getReference("User");
        mDatabaseW = database.getReference("Workouts");

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                Intent returnIntent = new Intent();
                mDatabaseW.child("Workout" + (10000000+MainActivity.NumOfWorkoutsALl)).child("UIDofCreator").setValue(MainActivity.user.getUid());
                mDatabaseW.child("Workout" + (10000000+MainActivity.NumOfWorkoutsALl)).child("Name").setValue(name);
                mDatabaseW.child("Workout" + (10000000+MainActivity.NumOfWorkoutsALl)).child(name).setValue(workoutList);

                mDatabase.child(MainActivity.Nickname).child("NumOfWorkouts").setValue(MainActivity.NumOfWorkouts+1);

                returnIntent.putExtra("name", name);
                setResult(Activity.RESULT_OK,returnIntent);
                WorkoutAdapter.exercises.clear();

                finish();

            }
        });


    }
}