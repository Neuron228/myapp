package com.example.myapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    //todo - intro screen (splash-screen)
    ActivityMainBinding binding;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    static String email;
    static String Nickname;
    static String name;
    static int NumOfPublications;
    public static ArrayList<String> publications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Account:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.Home:

                    break;
                case R.id.Search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.Workout:
                    replaceFragment(new WorkoutFragment());
                    break;

            }

            return true;
        });
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        //бубен уебище блять

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(MainActivity.email)) {
                        int n =0;
                        name = ds.child("firstName").getValue(String.class) +" "+ ds.child("lastName").getValue(String.class);
                        NumOfPublications = ds.child("numOfPublications").getValue(Integer.class);
                        publications.clear();
                        while (n < NumOfPublications && publications.size() != NumOfPublications+1) {
                            if(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class) != null) {

                                publications.add(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class));
                                n++;
                            }
                        }
                        Nickname = ds.child("nickname").getValue(String.class);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ListView HomeList = findViewById(R.id.ListViewHome);
        ArrayList<Publication> publici = new ArrayList<>();

        HomeList.setItemsCanFocus(true);

        //люблю тебя Левочка  :-)
        HomePageAdapter adapter = new HomePageAdapter(this,publici);
        HomeList.setAdapter(adapter);



    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}