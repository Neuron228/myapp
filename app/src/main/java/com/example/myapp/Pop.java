package com.example.myapp;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pop extends Activity {

    SearchView searchView;
    ListView UsersListView;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    SearchUserRecyclerAdapter adapter;

    public ArrayList<ApplicationAccount> UsersList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);

         setContentView(R.layout.popwindow);
         UsersListView = findViewById(R.id.UsersList);

         database = FirebaseDatabase.getInstance();
         mDatabase = database.getReference("Workouts");

        Bundle arguments = getIntent().getExtras();
        int PositionOfWorkout = arguments.getInt("NamePosition");


        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ApplicationAccount account = new ApplicationAccount(ds.child("firstName").getValue(String.class), ds.child("lastName").getValue(String.class), ds.child("nickname").getValue(String.class), ds.child("UID").getValue(String.class));
                    if (!account.getUid().equals(fUser.getUid())) {

                        UsersList.add(account);
                    }
                    if ( getApplicationContext() != null) {
                        adapter = new SearchUserRecyclerAdapter(getApplicationContext(),R.layout.search_user_recycler_pop ,UsersList);
                        UsersListView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });



         searchView = findViewById(R.id.SerchView);
         searchView.clearFocus();
         searchView.setIconifiedByDefault(false);

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 filterList(newText);
                 return false;
             }
         });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;                                            

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        UsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationAccount PickedUser = UsersList.get(position);
                mDatabase.child(MainActivity.IDlist.get(PositionOfWorkout)).child("AnotherUsersOfWorkout").child(PickedUser.getUid()).setValue(PickedUser.getUid());
                onBackPressed();
            }
        });





    }
    private void filterList(String text){
        ArrayList<ApplicationAccount> UsersListFiltered = new ArrayList<>();
        for (ApplicationAccount item : UsersList){
            if(item.getNickname().toLowerCase().contains(text) || item.getLastName().toLowerCase().contains(text)||item.getFirstName().toLowerCase().contains(text)){
                UsersListFiltered.add(item);
            }
        }
        if (UsersListFiltered.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(UsersListFiltered);
        }

    }
}
