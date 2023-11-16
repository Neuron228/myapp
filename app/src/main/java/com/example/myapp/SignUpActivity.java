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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

        Button workoutButton = findViewById(R.id.workoutButton);
        Button SearchButton = findViewById(R.id.SearchButton);
        Button HomePage = findViewById(R.id.HomePage);
        Button AccountPage = findViewById(R.id.AccountPage);

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
        Intent loginIntent = new Intent(this, SignInActivity.class);
        startActivity(loginIntent);
    }

}
