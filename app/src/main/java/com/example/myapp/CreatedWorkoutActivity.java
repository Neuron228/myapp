package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CreatedWorkoutActivity extends AppCompatActivity {

    public static FirebaseUser user ;

    public static ArrayList<Workout> ALLWorkout = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_workout);

        Bundle arguments = getIntent().getExtras();
        TextView nameWorkout = findViewById(R.id.nameWorkout);
        int Position = arguments.getInt("NamePosition");
        ListView WorkoutList = findViewById(R.id.WorkoutList2);
        FloatingActionButton EditButton = findViewById(R.id.change);
        ImageButton BackButton = findViewById(R.id.BackButton4);
        ImageButton ShareButton = findViewById(R.id.share);

        String WorkoutName = MainActivity.list.get(Position);


        nameWorkout.setText(WorkoutName);

        ALLWorkout = MainActivity.ALLUserWorkouts.get(Position);

        WorkoutList.setItemsCanFocus(true);
        WorkoutAdapter2 adapter2 = new WorkoutAdapter2(this,ALLWorkout);
        WorkoutList.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".WorkoutMakerActivity");
                intent.putExtra("view","change");
                intent.putExtra("Name",WorkoutName);
                startActivity(intent);

            }
        });
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(CreatedWorkoutActivity.this,Pop.class);
                intent2.putExtra("NamePosition",Position);
                intent2.putExtra("WorkoutName",WorkoutName);
                startActivity(intent2);
            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
            });

    }
}