package com.example.myapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends FragmentActivity {
    //todo - intro screen (splash-screen)
    ActivityMainBinding binding;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    DatabaseReference mDatabaseW;
    static String email;
    static String Nickname;
    static String Username;
    public static FirebaseUser user ;
    static int NumForID;
    static int NumOfPublications;
    static ArrayList<String> items = new ArrayList<>();

    static int NumOfWorkouts;

    static long NumOfWorkoutsALl;
    public static ArrayList<String> publications = new ArrayList<String>();
    public static ArrayList<String> list = new ArrayList<String>();

    public static ArrayList<String> IDlist = new ArrayList<String>();


    public static ArrayList<ArrayList<Workout>> ALLUserWorkouts = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String intentFragment = getIntent().getExtras().getString("frgToLoad");
        if(intentFragment != null) {

            switch (intentFragment) {
                case "FRAGMENT_WORKOUT":
                    replaceFragment(new WorkoutFragment());
                    break;
                case "FRAGMENT_SEARCH":
                    replaceFragment(new SearchFragment());
                    break;
                case "FRAGMENT_PROFILE":
                    replaceFragment(new ProfileFragment());
                    break;
            }
        }




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
        mDatabaseW = database.getReference("Workouts");
        user = FirebaseAuth.getInstance().getCurrentUser();



        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(MainActivity.email)) {
                        int n =0;
                        Username = ds.child("firstName").getValue(String.class) +" "+ ds.child("lastName").getValue(String.class);
                        NumOfPublications = ds.child("numOfPublications").getValue(Integer.class);
                        NumOfWorkouts = ds.child("NumOfWorkouts").getValue(Integer.class);
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
                Collections.reverse(publications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseW.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NumForID = Math.toIntExact(snapshot.child("NumForID").getValue(Long.class));
                NumOfWorkoutsALl = snapshot.getChildrenCount()-1;
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ArrayList<Workout> ALLWorkout = new ArrayList<>();
                    try {
                        ds.child("UIDofCreator").getValue(String.class).equals(null);
                    }catch (NullPointerException a){
                        continue;
                    }

                    if (ds.child("UIDofCreator").getValue(String.class).equals(user.getUid()) ||( ds.child("AnotherUsersOfWorkout").child(user.getUid()).getValue(String.class) != null && ds.child("AnotherUsersOfWorkout").child(user.getUid()).getValue(String.class).equals(user.getUid()))) {
                        String name = ds.child("Name").getValue(String.class);
                        String id = ds.child("IDOfWorkout").getValue(String.class);
                        IDlist.add(id);

                        list.add(name);
                        int num = (int)ds.child(name).getChildrenCount();


                        for (int i = 0;i<num;i++){

                            switch (ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class)) {
                                case 1:
                                    Workout workout = new Workout(ds.child("IDOfWorkout").getValue(String.class),ds.child(name).child(Integer.toString(i)).child("name").getValue(String.class),ds.child(name).child(Integer.toString(i)).child("repetitions").getValue(Integer.class),ds.child(name).child(Integer.toString(i)).child("sets").getValue(Integer.class),ds.child(name).child(Integer.toString(i)).child("resttime").getValue(Integer.class),ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class));
                                    ALLWorkout.add(workout);
                                    break;

                                case 2:
                                    Workout workout1 = new Workout(ds.child("IDOfWorkout").getValue(String.class),ds.child(name).child(Integer.toString(i)).child("name").getValue(String.class),ds.child(name).child(Integer.toString(i)).child("resttime").getValue(Integer.class),ds.child(name).child(Integer.toString(i)).child("timeType").getValue(String.class),ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class));
                                    ALLWorkout.add(workout1);
                                    break;
                            }
                        }

                        ALLUserWorkouts.add(ALLWorkout);
                    }
                }

                Collections.reverse(ALLUserWorkouts);
                Collections.reverse(list);
                Collections.reverse(IDlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}