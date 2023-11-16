package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    String email;
    String Nickname;
    int NumOfPublications;
    public static ArrayList<String> publications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");

        Button workoutButton = findViewById(R.id.workoutButton);
        Button SearchButton = findViewById(R.id.SearchButton);
        Button HomePage = findViewById(R.id.HomePage);
        Button AccountPage = findViewById(R.id.AccountPage);
        ListView PublicationsList = findViewById(R.id.YourPublications);
        Button CreatePublication = findViewById(R.id.CreatePublication);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView DisplayName = findViewById(R.id.DisplayName);




        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                        int n =0;
                        DisplayName.setText(ds.child("firstName").getValue(String.class) +" "+ ds.child("lastName").getValue(String.class));
                        NumOfPublications = ds.child("numOfPublications").getValue(Integer.class);
                        while (n < NumOfPublications) {
                            publications.add(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class));
                            n++;
                        }
                        Nickname = ds.child("nickname").getValue(String.class);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AdapterOfPublications adapter1 = new AdapterOfPublications(this, publications, Nickname);
        PublicationsList.setAdapter(adapter1);



        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publications.clear();
                adapter1.notifyDataSetChanged();
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publications.clear();
                adapter1.notifyDataSetChanged();
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });
        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publications.clear();
                adapter1.notifyDataSetChanged();
                Intent intent = new Intent(".MainActivity2");
                startActivity(intent);
            }
        });
        AccountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publications.clear();
                adapter1.notifyDataSetChanged();
                Intent intent = new Intent(".AccountPageActivity");
                startActivity(intent);
            }
        });

        CreatePublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".CreatePublicationActivity");
                publications.clear();
                adapter1.notifyDataSetChanged();
                intent.putExtra("nick",Nickname);
                intent.putExtra("num", NumOfPublications);
                startActivity(intent);
            }
        });


    }
}
