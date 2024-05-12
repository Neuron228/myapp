package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    public static final String TAG = "SigninActivity";
    private ApplicationAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        TextView signUp = findViewById(R.id.SignUp);
        Button SignInButton = findViewById(R.id.signIn);
        EditText Email = findViewById(R.id.signInEmail);
        EditText PassWord = findViewById(R.id.signInPassword);

        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signUp.setText(content);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(".SignUpActivity");
                startActivity(i);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailText = Email.getText().toString();
                String PassWordText = PassWord.getText().toString();

                ApplicationAccount user = new ApplicationAccount(EmailText, PassWordText);
                signInUser(EmailText,PassWordText);

            }
        });

    }

    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        Intent profileIntent = new Intent(this, MainActivity.class);
        profileIntent.putExtra("email",currentUser.getEmail());
        startActivity(profileIntent);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent profileIntent = new Intent(this, MainActivity.class);
            profileIntent.putExtra("email",currentUser.getEmail());
            startActivity(profileIntent);
        }
    }
}
