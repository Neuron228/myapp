package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    public static final String TAG = "SignUpActivity";
    private ApplicationAccount user;

    private static final int PICK = 1;

    private Uri mImageUri;

    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<ApplicationAccount> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ImageButton PicturePicker = findViewById(R.id.PicturePicker);
        ImageButton backbutton = findViewById(R.id.backbutton);
        EditText LastName = findViewById(R.id.signupLastName);
        EditText Email = findViewById(R.id.signupEmail);
        EditText Password = findViewById(R.id.signupPassword);
        EditText FirstName = findViewById(R.id.signupName);
        EditText NickName = findViewById(R.id.signupNickname);

        Button CreateAccount = findViewById(R.id.CreateAccount);

        database  = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            mImageUri = data.getData();
                            PicturePicker.setImageURI(mImageUri);
                        }else{
                            Toast.makeText(SignUpActivity.this,"No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );
        PicturePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);;
            }
        });
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
                if (mImageUri != null){
                    user = new ApplicationAccount(FirstNameText, LastNameText,EmailText,PasswordText,NickNameText,mImageUri.toString());
                    registerUser(EmailText,PasswordText);
                }else {
                    Toast.makeText(SignUpActivity.this,"Please select image",Toast.LENGTH_SHORT).show();
                }

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
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "."+ getFileExtension(mImageUri));
        imageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mDatabase.child(user.getNickname()).setValue(user);
                        mDatabase.child(user.getNickname()).child("UID").setValue(FirebaseAuth.getInstance().getUid());
                        mDatabase.child(user.getNickname()).child("pictureUri").setValue(mImageUri.toString());
                        mDatabase.child(user.getNickname()).child("NumOfWorkouts").setValue(0);
                        user.setUid(FirebaseAuth.getInstance().getUid());
                        Intent loginIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(loginIntent);
                    }
                });
            }
        });

    }


    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
