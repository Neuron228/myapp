package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Pop extends Activity {

    SearchView searchView;
    ListView UsersListView;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    SearchUserRecyclerAdapter adapter;
    String WorkoutName;

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
        WorkoutName = arguments.getString("WorkoutName");


        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ApplicationAccount account = new ApplicationAccount(ds.child("firstName").getValue(String.class), ds.child("lastName").getValue(String.class),ds.child("email").getValue(String.class) ,ds.child("nickname").getValue(String.class), ds.child("UID").getValue(String.class),ds.child("pictureUri").getValue(String.class),ds.child("FCMToken").getValue(String.class));
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
                sendNotification(PickedUser);
                onBackPressed();
            }
        });





    }

    private void sendNotification(ApplicationAccount PickedUser){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","GymGrid");
            notificationObj.put("body",MainActivity.Username + " sent you workout: "+WorkoutName);

            JSONObject dataObj = new JSONObject();
            dataObj.put("userId",user.getUid());

            jsonObject.put("notification",notificationObj);
            jsonObject.put("data",dataObj);
            jsonObject.put("to",PickedUser.getFCMToken());

            callApi(jsonObject);
        }catch (Exception e){

        }
    }
    void callApi(JSONObject jsonObject){
        MediaType JSON = MediaType.get("application/json");
        OkHttpClient client = new OkHttpClient();
        String url = "https://fcm.googleapis.com/fcm/send";
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization","Bearer AAAA2zOFFzo:APA91bFKcg23WPYwETdON5yQY3se3O_BPh69ZU5lViqymHhbUytYuRJ-u7qvGe2mHHMefPfc_pgUbhiHCyYejZap8k2wPoaqhrGFuWiAY9S_HGT2TAeWT94Xc20GTbg2jLXaK7twQBuK")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

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
