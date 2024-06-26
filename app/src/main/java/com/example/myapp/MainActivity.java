package com.example.myapp;


import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.example.myapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends FragmentActivity {
    //todo - intro screen (splash-screen)
    ActivityMainBinding binding;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    DatabaseReference mDatabaseW;


    static String email;
    static String Nickname;
    private Fragment fragment = null;
    public static String Username;
    public static FirebaseUser user ;
    static int NumForID;
    static int NumOfPublications;

    static String profilePicUri;
    static ArrayList<String> items = new ArrayList<>();
    public static Uri ProfileUriPic;

    static int NumOfWorkouts;

    static long NumOfWorkoutsALl;
    ProgressBar progressBar;
    public static ArrayList<String> publications = new ArrayList<String>();
    public static ArrayList<String> list = new ArrayList<String>();

    public static ArrayList<String> IDlist = new ArrayList<String>();

    private StorageReference storageReference;
    public static ArrayList<ArrayList<Workout>> ALLUserWorkouts = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, 1);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");
        mDatabaseW = database.getReference("Workouts");
        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        System.out.println(user.getIdToken(true));

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        // Declare the launcher at the top of your Activity/Fragment:

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(MainActivity.email)) {
                        int n = 0;
                        Nickname = ds.child("nickname").getValue(String.class);
                        Username = ds.child("firstName").getValue(String.class) + " " + ds.child("lastName").getValue(String.class);
                        NumOfPublications = ds.child("numOfPublications").getValue(Integer.class);
                        NumOfWorkouts = ds.child("NumOfWorkouts").getValue(Integer.class);
                        profilePicUri = ds.child("pictureUri").getValue(String.class);
                        storageReference = storage.getReferenceFromUrl(MainActivity.profilePicUri);
                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                String token = task.getResult();
                                if(!ds.child("FCMToken").exists() || !ds.child("FCMToken").getValue(String.class).equals(token)){
                                    mDatabase.child(Nickname).child("FCMToken").setValue(token);
                                }
                            }

                        });
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ProfileUriPic = uri;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        System.out.println(profilePicUri);
                        publications.clear();
                        while (n < NumOfPublications && publications.size() != NumOfPublications + 1) {
                            if (ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class) != null) {
                                System.out.println("4");
                                publications.add(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class));
                                n++;
                            }
                        }


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
                NumOfWorkoutsALl = snapshot.getChildrenCount() - 1;
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ArrayList<Workout> ALLWorkout = new ArrayList<>();
                    try {
                        ds.child("UIDofCreator").getValue(String.class).equals(null);
                    } catch (NullPointerException a) {
                        continue;
                    }

                    if (ds.child("UIDofCreator").getValue(String.class).equals(user.getUid()) || (ds.child("AnotherUsersOfWorkout").child(user.getUid()).getValue(String.class) != null && ds.child("AnotherUsersOfWorkout").child(user.getUid()).getValue(String.class).equals(user.getUid()))) {
                        String name = ds.child("Name").getValue(String.class);
                        String id = ds.child("IDOfWorkout").getValue(String.class);
                        IDlist.add(id);

                        list.add(name);
                        int num = (int) ds.child(name).getChildrenCount();


                        for (int i = 0; i < num; i++) {

                            switch (ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class)) {
                                case 1:
                                    Workout workout = new Workout(ds.child("IDOfWorkout").getValue(String.class), ds.child(name).child(Integer.toString(i)).child("name").getValue(String.class), ds.child(name).child(Integer.toString(i)).child("repetitions").getValue(Integer.class), ds.child(name).child(Integer.toString(i)).child("sets").getValue(Integer.class), ds.child(name).child(Integer.toString(i)).child("resttime").getValue(Integer.class), ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class));
                                    ALLWorkout.add(workout);
                                    break;

                                case 2:
                                    Workout workout1 = new Workout(ds.child("IDOfWorkout").getValue(String.class), ds.child(name).child(Integer.toString(i)).child("name").getValue(String.class), ds.child(name).child(Integer.toString(i)).child("resttime").getValue(Integer.class), ds.child(name).child(Integer.toString(i)).child("timeType").getValue(String.class), ds.child(name).child(Integer.toString(i)).child("itemViewType").getValue(Integer.class));
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

        fragment = new WorkoutFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();



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
                case R.id.Search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.Workout:
                    replaceFragment(new WorkoutFragment());
                    break;

            }

            return true;
        });
    }



    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}