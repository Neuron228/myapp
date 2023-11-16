package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    public static List<String> list = new ArrayList<String>();
    public static ArrayList<Workout> workout = new ArrayList<Workout>();
    public static ArrayList<ArrayList<Workout>> AllWorkoutsList = new ArrayList<ArrayList<Workout>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            //todo -change button to floatingactionButton
            //todo -
            //todo - instead of redirecting to a new activity open a dilog
            workout = (ArrayList<Workout>)arguments.getSerializable("Workout");
            AllWorkoutsList.add(workout);
        }
        Button AddButton = findViewById(R.id.AddButton1);
        ListView ListView = findViewById(R.id.ListView);
        Button BackButton = findViewById(R.id.BackButton2);

        Button workoutButton = findViewById(R.id.workoutButton);
        Button SearchButton = findViewById(R.id.SearchButton);
        Button HomePage = findViewById(R.id.HomePage);
        Button AccountPage = findViewById(R.id.AccountPage);

        AccountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".AccountPageActivity");
                startActivity(intent);
            }
        });
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });
        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                }
            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(".MainActivity3");
                startActivity(intent1);
            }
        });

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(".MainActivity4");
                intent2.putExtra("NamePosition",position);
                startActivity(intent2);

            }
        });

        List<String> items = initData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

        ListView.setAdapter(adapter);

    }


    public List<String> initData(){
            Bundle arguments = getIntent().getExtras();
         if(arguments!=null) {
             String name = arguments.getString("name");
             list.add(name);
         }
        return list;
    }
}
