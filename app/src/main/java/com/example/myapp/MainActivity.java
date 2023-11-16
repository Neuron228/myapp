package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //todo - intro screen (splash-screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo - change footer buttons to bottom  navigation
        ListView HomeList = findViewById(R.id.ListViewHome);
        ArrayList<Publication> publici = new ArrayList<>();

        HomeList.setItemsCanFocus(true);
        HomePageAdapter adapter = new HomePageAdapter(this,publici);
        HomeList.setAdapter(adapter);


        Button workoutButton = findViewById(R.id.workoutButton);
        Button SearchButton = findViewById(R.id.SearchButton);
        Button HomePage = findViewById(R.id.HomePage);
        Button AccountPage = findViewById(R.id.AccountPage);

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
        AccountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".AccountPageActivity");
                startActivity(intent);
            }
        });
    }
}