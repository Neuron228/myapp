package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WorkoutMakerActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    DatabaseReference mDatabaseW;

    String Nickname;

    String PreviousWorkoutName;
    ArrayList<Workout>  workout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_maker);

        EditText EditWorkoutName = findViewById(R.id.WorkoutName);
        Button button3 = findViewById(R.id.Button3);
        Button AddButton = findViewById(R.id.AddButton2);
        ListView WorkoutList = findViewById(R.id.WorkoutList);
        ImageButton BackButton = findViewById(R.id.BackButton3);
        Button AddButtonTime = findViewById(R.id.AddButtonTime);

        Bundle arguments = getIntent().getExtras();
        String Target = arguments.get("view").toString();

        System.out.println(Target);


        database = FirebaseDatabase.getInstance();

        mDatabase = database.getReference("User");
        mDatabaseW = database.getReference("Workouts");

        WorkoutList.setItemsCanFocus(true);
        WorkoutAdapter adapter = new WorkoutAdapter(this);
        WorkoutList.setAdapter(adapter);

        if(Target.equals("change")) {
            PreviousWorkoutName = arguments.get("Name").toString();
            workout = CreatedWorkoutActivity.ALLWorkout;
            EditWorkoutName.setText(PreviousWorkoutName);
            for (int i = 0;i<workout.size();i++){
                if(workout.get(i).getItemViewType() == 1){
                    WorkoutAdapter.exercises.add(new ListItem(1,workout.get(i).getName(),workout.get(i).getRepetitions(),workout.get(i).getSets(),workout.get(i).getResttime()));
                }else if (workout.get(i).getItemViewType() == 2){
                    WorkoutAdapter.exercises.add(new ListItem(2,workout.get(i).getName(),workout.get(i).getResttime(),workout.get(i).getTimeType()));
                }
            }
        }
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutAdapter.exercises.clear();
                onBackPressed();
            }
        });


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
                String name = EditWorkoutName.getText().toString();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("Name",name);
                childUpdates.put("UIDofCreator",MainActivity.user.getUid());
                System.out.println(Target.equals("set"));
                if(Target.equals("set")) {
                    mDatabaseW.child(Long.toString(10000000 + MainActivity.NumForID)).updateChildren(childUpdates);

                    int num = 0;
                    while (num < WorkoutAdapter.exercises.size()) {
                        ListItem exercise = WorkoutAdapter.exercises.get(num);
                        switch (exercise.getItemViewType()) {
                            case 1:
                                Workout workout = new Workout(exercise.nameExercise, exercise.NumReps, exercise.NumSets, exercise.restime, 1);
                                workoutList.add(workout);
                                break;
                            case 2:
                                Workout workout1 = new Workout(exercise.nameExercise, exercise.Time, exercise.timeType, 2);
                                workoutList.add(workout1);
                                break;
                        }
                        num++;
                    }
                    mDatabaseW.child(Long.toString(10000000 + MainActivity.NumForID)).child(name).setValue(workoutList);
                    mDatabaseW.child(Long.toString(10000000 + MainActivity.NumForID)).child("IDOfWorkout").setValue(Long.toString(10000000 + MainActivity.NumForID));

                    mDatabase.child(MainActivity.Nickname).child("NumOfWorkouts").setValue(MainActivity.NumOfWorkouts+ 1);
                    mDatabaseW.child("NumForID").setValue(MainActivity.NumForID + 1);

                    WorkoutAdapter.exercises.clear();

                } else if (Target.equals("change")) {
                    mDatabaseW.child(workout.get(0).getId()).updateChildren(childUpdates);

                    int num = 0;
                    while (num < WorkoutAdapter.exercises.size()) {
                        ListItem exercise = WorkoutAdapter.exercises.get(num);
                        switch (exercise.getItemViewType()) {
                            case 1:
                                Workout workout = new Workout(exercise.nameExercise, exercise.NumReps, exercise.NumSets, exercise.restime, 1);
                                workoutList.add(workout);
                                break;
                            case 2:
                                Workout workout1 = new Workout(exercise.nameExercise, exercise.Time, exercise.timeType, 2);
                                workoutList.add(workout1);
                                break;
                        }
                        num++;
                    }
                    mDatabaseW.child(workout.get(0).getId()).child(PreviousWorkoutName).removeValue();
                    mDatabaseW.child(workout.get(0).getId()).child(name).setValue(workoutList);
                    mDatabaseW.child(workout.get(0).getId()).child("IDOfWorkout").setValue((workout.get(0).getId()));


                    WorkoutAdapter.exercises.clear();
                }
                Intent i = new Intent(WorkoutMakerActivity.this,MainActivity.class);
                i.putExtra("frgToLoad", "FRAGMENT_WORKOUT");
                startActivity(i);

            }
        });
        }


    }