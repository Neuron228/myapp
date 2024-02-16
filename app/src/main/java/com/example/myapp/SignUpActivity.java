package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static final String TAG = "SignUpActivity";
    private ApplicationAccount user;

    ArrayList<ApplicationAccount> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        EditText LastName = findViewById(R.id.signupLastName);
        EditText Email = findViewById(R.id.signupEmail);
        EditText Password = findViewById(R.id.signupPassword);
        EditText FirstName = findViewById(R.id.signupName);
        EditText NickName = findViewById(R.id.signupNickname);

        Button CreateAccount = findViewById(R.id.CreateAccount);

        database  = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();


        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NickNameText = NickName.getText().toString();
                String FirstNameText = FirstName.getText().toString();
                String LastNameText = LastName.getText().toString();
                String EmailText = Email.getText().toString();
                String PasswordText = Password.getText().toString();



                if(FirstNameText.equals("") || LastNameText.equals("") || EmailText.equals("") || PasswordText.equals("") || NickNameText.equals((""))){
                    Toast.makeText(getApplicationContext(),"One of the fields is not filled!",Toast.LENGTH_SHORT).show();
                }
                user = new ApplicationAccount(FirstNameText, LastNameText,EmailText,PasswordText,NickNameText);
                registerUser(EmailText,PasswordText);
            }
        });



    }
    public void registerUser(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        mDatabase.child(user.getNickname()).setValue(user);
        mDatabase.child(user.getNickname()).child("UID").setValue(FirebaseAuth.getInstance().getUid());
        mDatabase.child(user.getNickname()).child("NumOfWorkouts").setValue(0);
        user.setUid(FirebaseAuth.getInstance().getUid());
        Intent loginIntent = new Intent(this, SignInActivity.class);
        startActivity(loginIntent);
    }

}
