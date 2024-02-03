package com.example.myapp;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePublicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePublicationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    String Nickname;
    String text = "";
    int NumOfPublications;


    public CreatePublicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePublicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePublicationFragment newInstance(String param1, String param2) {
        CreatePublicationFragment fragment = new CreatePublicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_publication, container, false);

        Button postButton = view.findViewById(R.id.postButton);
        EditText TextPublication = view.findViewById(R.id.TextPublication);

        Nickname = MainActivity.Nickname;
        NumOfPublications = MainActivity.NumOfPublications;

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");

        HashMap<String,Object> newNum = new HashMap<>();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = TextPublication.getText().toString();
                mDatabase.child(Nickname).child("publications").child("publication" + NumOfPublications).setValue(text);
                newNum.put("numOfPublications",NumOfPublications+1);
                mDatabase.child(Nickname).updateChildren(newNum);
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new ProfileFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}