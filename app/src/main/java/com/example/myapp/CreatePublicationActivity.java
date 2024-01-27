package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreatePublicationActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    String Nickname;
    String text = "";
    int NumOfPublications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_publication);

        Intent intent = getIntent();
        Nickname = intent.getStringExtra("nick");
        NumOfPublications = intent.getIntExtra("num",R.id.CreatePublication);

        Button postButton = findViewById(R.id.postButton);
        EditText TextPublication = findViewById(R.id.TextPublication);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");

        HashMap<String,Object> newNum = new HashMap<>();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = TextPublication.getText().toString();
                mDatabase.child(Nickname).child("publications").child("publication" + NumOfPublications).setValue(text);
                newNum.put("numOfPublications",NumOfPublications+1);
                mDatabase.child(Nickname).updateChildren(newNum);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
